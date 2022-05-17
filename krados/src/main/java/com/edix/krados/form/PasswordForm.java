package com.edix.krados.form;

import lombok.Data;
import org.springframework.data.repository.query.Param;

@Data
public class PasswordForm {
    private String username;
    private String oldPassword;
    private String newPassword;
}
