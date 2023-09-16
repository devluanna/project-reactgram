package com.react.domain.model.User;

public enum UserRole {
    USERGRAM("usergram"),
    NOTUSER("notuser");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }

}