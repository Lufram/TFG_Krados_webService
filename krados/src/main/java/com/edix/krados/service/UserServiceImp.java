package com.edix.krados.service;

import com.edix.krados.model.Role;
import com.edix.krados.model.User;
import com.edix.krados.repository.RoleRepository;
import com.edix.krados.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  RoleRepository roleRepository;

    @Override
    public User saveUser(User user) {
        log.info("Guardando nuevo usuario {} en la base de datos", user.getName());
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Guardando nuevo rol {} en la base de datos", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String rolName) {
        log.info("Añadiendo rol {} al usuario {}", rolName, username );
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(rolName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("Mostrando usuario {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Mostrando todos los usuarios ");
        return userRepository.findAll();
    }
}
