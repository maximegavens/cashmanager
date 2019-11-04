package com.cashmanager.repository;

import com.cashmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingAdminRepository extends JpaRepository<User, Long> {   //CrudRepository
}