package com.react.domain.service.ServiceImp;

import java.util.List;
import java.util.NoSuchElementException;


import com.react.domain.model.User.UserAccount;
import com.react.domain.repository.UserAccountRepository;
import com.react.domain.service.UserAccountService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserAccount findById(Long id) {
        return userAccountRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<UserAccount> getAllUsers() {
        return userAccountRepository.findAll();
    }

    @Override
    public UserAccount getUserById(Long id) {
        return userAccountRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public UserAccount create(UserAccount newUser) {

        // Check Nickname
        if (userAccountRepository.findByLogin(newUser.getLogin()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (newUser.getLogin() == null || newUser.getLogin().isEmpty()) {
            throw new IllegalArgumentException("Login is required.");
        }

        if (newUser.getLogin().length() < 3) {
            throw new IllegalArgumentException("Your username must be at least 3 characters long.");
        }


        // Check Name
        if (newUser.getName() == null || newUser.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required.");
        }

        // Check Email
        if (userAccountRepository.findByEmail(newUser.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists.");
        }

        if (newUser.getEmail() == null || newUser.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required.");}



        return userAccountRepository.save(newUser);
    }


    @Override
    public UserAccount update(UserAccount updatedUserAccount) {

        if (updatedUserAccount.getLogin().length() < 3) {
            throw new IllegalArgumentException("Your username must be at least 3 characters long.");
        }


        UserAccount existingLogin = userAccountRepository.findById(updatedUserAccount.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!existingLogin.getLogin().equals(updatedUserAccount.getLogin())) {
            if (userAccountRepository.existsByLogin(updatedUserAccount.getLogin())) {
                throw new IllegalArgumentException("Username already exists");
            }
        }


        if (userAccountRepository.existsByName(updatedUserAccount.getName())) {
            UserAccount existingAccount = userAccountRepository.findByName(updatedUserAccount.getName());
            if (!existingAccount.getName().equals(updatedUserAccount.getName())) {
                existingAccount.setName(updatedUserAccount.getName());
            }}


        return userAccountRepository.save(updatedUserAccount);
    }


    public UserAccount updatePass(UserAccount updatedPassAccount ) {

        String newPassword = updatedPassAccount.getPassword();
        String newConfirmPassword = updatedPassAccount.getConfirmPassword();

        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }

        if (!newPassword.equals(newConfirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        String encryptedNewPassword = new BCryptPasswordEncoder().encode(newPassword);
        updatedPassAccount.setPassword(encryptedNewPassword);

        String encryptedNewConfirmPassword = new BCryptPasswordEncoder().encode(newConfirmPassword);
        updatedPassAccount.setConfirmPassword(encryptedNewConfirmPassword);


        return userAccountRepository.save(updatedPassAccount);
    }

}