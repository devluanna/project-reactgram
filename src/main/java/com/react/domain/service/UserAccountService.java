package com.react.domain.service;


import com.react.domain.model.User.UserAccount;

import org.springframework.stereotype.Service;

import java.util.List;


public interface UserAccountService {
    UserAccount findById(Long id);
    UserAccount create(UserAccount newUser);
    UserAccount update(UserAccount authenticatedAccount);
    UserAccount updatePass(UserAccount authenticatedUserPass);
    List<UserAccount> getAllUsers();
    UserAccount getUserById(Long id);
}