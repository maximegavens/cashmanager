package com.cashmanager.repository;

import com.cashmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<User, Long> {

    Boolean existsUserByEmail(String email);
    User findUserByEmail(String email);
}
