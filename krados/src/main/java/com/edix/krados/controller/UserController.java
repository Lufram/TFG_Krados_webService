package com.edix.krados.controller;

import com.edix.krados.form.ClientForm;
import com.edix.krados.form.PasswordForm;
import com.edix.krados.form.RegisterForm;
import com.edix.krados.form.RoleToUserForm;
import com.edix.krados.model.*;
import com.edix.krados.repository.CartRepository;
import com.edix.krados.repository.ClientRepository;
import com.edix.krados.repository.UserRepository;
import com.edix.krados.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<List<User>>getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }
    // Añade un nuevo usuario
    @PostMapping("/users/save")
    public ResponseEntity<User>saveUser(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("krados/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }
    // Recoge los datos del formulario de registro y crea un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<User>registerUser(@RequestBody RegisterForm registerForm){
        try {
            User userExist = userRepository.findByUsername(registerForm.getUsername());
            if(userExist == null){
                User user = new User();
                user.setName(registerForm.getName() + " " + registerForm.getLastname());
                user.setUsername(registerForm.getUsername());
                user.setPassword(registerForm.getPassword());
                user.setRoles(new ArrayList<>());
                userService.saveUser(user);
//                userService.addRoleToUser(registerForm.getUsername(), "ROLE_USER");
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
                return new ResponseEntity<User>(user,HttpStatus.CREATED);
            }else{
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
        }
    }
    // Cambiar la contraseña de un usuario
    @PutMapping("/updatePassword")
    public ResponseEntity<String>updatePassword(@RequestBody PasswordForm passwordForm){
        User user = userRepository.findByUsername(passwordForm.getUsername());
        String oldPassword = passwordEncoder.encode(passwordForm.getOldPassword());
        if(user.getPassword().equals(oldPassword)){
            user.setPassword(passwordForm.getNewPassword());
            userService.saveUser(user);
            return new ResponseEntity("Cambio de contraseña correcto", HttpStatus.CREATED);
        }
        return new ResponseEntity("Error", HttpStatus.BAD_REQUEST);
    }
    // Añade un nuevo rol
    @PostMapping("/role/save")
    public ResponseEntity<Role>saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }
    // Asigna un rol a un usuario
    @PostMapping("/role/addtouser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getUsername(), form.getRolename());
        return ResponseEntity.ok().build();
    }
    // devuelve la información personal y el carrito que correspode con el id de usuario
    @GetMapping("/client")
    public ResponseEntity<ClientForm>getClient(@RequestParam (name = "userName") String userName){
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
}
