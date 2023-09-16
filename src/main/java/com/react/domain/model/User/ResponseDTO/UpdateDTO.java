package com.react.domain.model.User.ResponseDTO;

import org.springframework.web.multipart.MultipartFile;

public record UpdateDTO(String login, String name, String bio ) {
    public String getLogin() {
        return login;
    }
    public String getName() {
        return name;
    }
    public String getBio() {
        return bio;
    }


}