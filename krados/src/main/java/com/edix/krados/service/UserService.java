package com.edix.krados.service;

import com.edix.krados.model.Role;
import com.edix.krados.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);

    void addRoleToUser(String username, String rolName);
    User getUser(String username);
    List<User>getUsers();

}
