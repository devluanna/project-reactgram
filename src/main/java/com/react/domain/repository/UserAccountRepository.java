package com.react.domain.repository;


import com.react.domain.model.User.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByLogin(String login);
    boolean existsByLogin(String username);
    boolean existsByName(String name);
    UserAccount findByName(String name);
    UserAccount findByEmail(String email);

}