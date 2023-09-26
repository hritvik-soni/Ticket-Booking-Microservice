package com.learning.userdetails.repository;

import com.learning.userdetails.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepo extends JpaRepository<Users,Integer> {
//     Users findByUserEmail(String email);
    Users findByUserEmail(String email);

}
