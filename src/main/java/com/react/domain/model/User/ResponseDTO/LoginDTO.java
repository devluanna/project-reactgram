package com.react.domain.model.User.ResponseDTO;

public record LoginDTO( String login, String password) {
    public String getLogin() {return login;}
    public String getPassword() {return password;}

}