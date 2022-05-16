package com.edix.krados.controller;

import com.edix.krados.form.RoleToUserForm;
import com.edix.krados.model.Client;
import com.edix.krados.model.Role;
import com.edix.krados.model.User;
import com.edix.krados.repository.ClientRepository;
import com.edix.krados.repository.UserRepository;
import com.edix.krados.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;

    // Devuelve todos los usuarios
    @GetMapping("/users")
    public ResponseEntity<List<User>>getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }
    // Añade un nuevo usuario
    @PostMapping("/users/save")
    public ResponseEntity<User>saveUser(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
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
    public ResponseEntity<Client>getClient(@RequestParam (name = "userName") String userName){
        User user = userRepository.findByUsername(userName);
        return new ResponseEntity(user.getClient(), HttpStatus.OK);
    }
}
