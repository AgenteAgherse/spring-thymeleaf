package com.db2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.db2.Model.User;


public interface UserRepository extends JpaRepository<User, Long>{
    
}
