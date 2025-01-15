package com.translineindia.vms.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminLoginDTO {

    @NotBlank(message = "Admin ID cannot be blank")
    @Size(max = 50, message = "Admin ID must not exceed 50 characters")
    private String adminId;

    @NotBlank(message = "Company Code cannot be blank")
    @Size(max = 30, message = "Company Code must not exceed 30 characters")
    private String cmpCd;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Size(max = 50, message = "Email must not exceed 50 characters")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 100, message = "Password must not exceed 100 characters")
    private String password;
}
