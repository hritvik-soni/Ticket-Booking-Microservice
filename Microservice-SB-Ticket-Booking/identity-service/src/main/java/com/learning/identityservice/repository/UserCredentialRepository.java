package com.learning.identityservice.repository;

import com.learning.identityservice.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepository  extends JpaRepository<UserCredential,Integer> {
    Optional<UserCredential> findByName(String username);

    Optional<UserCredential> findByEmail(String username);

//    void delete(Optional<UserCredential> user);
}
