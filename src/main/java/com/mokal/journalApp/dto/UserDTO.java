package com.mokal.journalApp.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotEmpty(message = "User name cannot be empty")
    @Schema(description = "User name of the user", example = "mokal2002")
    private String userName;
    private String email;
    private boolean sentimentAnalysis;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}