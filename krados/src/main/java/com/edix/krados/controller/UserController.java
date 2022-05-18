package com.edix.krados.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.edix.krados.form.*;
import com.edix.krados.model.Role;
import com.edix.krados.model.User;

import com.edix.krados.model.*;
import com.edix.krados.repository.CartRepository;
import com.edix.krados.repository.ClientRepository;
import com.edix.krados.repository.UserRepository;

import com.edix.krados.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/krados")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // Devuelve todos los usuarios
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    // Añade un nuevo usuario
    @PostMapping("/users/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("krados/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    // Recoge los datos del formulario de registro y crea un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterForm registerForm) {
        try {
            User userExist = userRepository.findByUsername(registerForm.getUsername());
            if (userExist == null) {
                User user = new User();
                user.setName(registerForm.getName() + " " + registerForm.getLastname());
                user.setUsername(registerForm.getUsername());
                user.setPassword(registerForm.getPassword());
                user.setRoles(new ArrayList<>());
                userService.saveUser(user);
                userService.addRoleToUser(registerForm.getUsername(), "ROLE_USER");
                Cart cart = new Cart();
                cartRepository.save(cart);
                Address address = new Address(
                        registerForm.getRoadName(),
                        registerForm.getCity(),
                        registerForm.getState(),
                        registerForm.getRoadNum(),
                        registerForm.getPostalCode());
                Client client = new Client();
                client.setName(registerForm.getName());
                client.setSurname(registerForm.getLastname());
                client.setAddress(address);
                client.setUser(user);
                client.setCart(cart);
                clientRepository.save(client);
                cart.setClient(client);
                cartRepository.save(cart);
                return new ResponseEntity<User>(user, HttpStatus.CREATED);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
        }
    }

    // Cambiar la contraseña de un usuario
    @PutMapping("/updatePassword")
    public ResponseEntity<UserForm> updatePassword(@RequestBody PasswordForm passwordForm) {
        User user = userRepository.findByUsername(passwordForm.getUsername());
        if (passwordEncoder.matches(passwordForm.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordForm.getNewPassword());
            userService.saveUser(user);
            UserForm userForm = new UserForm();
            userForm.setId(user.getId());
            userForm.setName(user.getName());
            userForm.setUsername(user.getUsername());
            return new ResponseEntity<UserForm>(userForm, HttpStatus.CREATED);
        }
        return new ResponseEntity("Error", HttpStatus.BAD_REQUEST);
    }

    // Añade un nuevo rol
    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    // Asigna un rol a un usuario
    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRolename());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 20 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    // devuelve la información personal y el carrito que correspode con el id de usuario
    @GetMapping("/client")
    public ResponseEntity<ClientForm> getClient(@RequestParam(name = "userName") String userName) {
        User user = userRepository.findByUsername(userName);
        Client client = user.getClient();
        ClientForm clientForm = new ClientForm();

        clientForm.setId(client.getId());
        clientForm.setName(client.getName());
        clientForm.setSurname(client.getSurname());
        clientForm.setAddress(client.getAddress());
        clientForm.setCartId(client.getCart().getId());

        return new ResponseEntity(clientForm, HttpStatus.OK);
    }
    
    // Modifica los datos del cliente
    @PutMapping("/client/update")
    public ResponseEntity<Client> updateClient(@RequestBody ClientForm clientForm) {
        Client c = clientRepository.findById(clientForm.getId()).get();
        c.setName(clientForm.getName());
        c.setSurname(clientForm.getSurname());
        Address a = c.getAddress();
        a.setRoadName(clientForm.getAddress().getRoadName());
        a.setNumber(clientForm.getAddress().getNumber());
        a.setCityName(clientForm.getAddress().getCityName());
        a.setExtraInfo(clientForm.getAddress().getExtraInfo());
        a.setPostalCode(clientForm.getAddress().getPostalCode());
        clientRepository.save(c);

        return new ResponseEntity(c , HttpStatus.OK);
    }
}
