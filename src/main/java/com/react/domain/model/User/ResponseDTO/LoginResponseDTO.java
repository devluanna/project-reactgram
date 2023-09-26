package com.react.domain.model.User.ResponseDTO;

public class LoginResponseDTO {
    private String token;
    private Long id;

    public LoginResponseDTO(String token, Long id) {
        this.token = token;
        this.id = id;
    }

    // getters e setters
}