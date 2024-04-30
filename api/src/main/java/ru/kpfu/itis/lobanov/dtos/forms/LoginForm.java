package ru.kpfu.itis.lobanov.dtos.forms;

import lombok.Data;

@Data
public class LoginForm {
    private String email;
    private String password;
}
