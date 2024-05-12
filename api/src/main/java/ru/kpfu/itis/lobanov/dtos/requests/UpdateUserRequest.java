package ru.kpfu.itis.lobanov.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    private String id;
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;
}
