package com.react.domain.model.User.ResponseDTO;


import com.react.domain.model.User.Dashboard;
import com.react.domain.model.User.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record RegisterDTO(String login, String name, String email, String password, UserRole role, Dashboard dashboard) {
    public String getLogin() {
        return login;
    }
    public String getName() {return name;}
    public String getEmail() {return email;}

    @NotBlank(message = "Is required!")
    @Size(min = 8, message = "Your password must contain at least 8 characters")
    public String getPassword() {return password;}
    public Dashboard getDashboard() {return dashboard;}

}