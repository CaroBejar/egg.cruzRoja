package com.example.demo.repositories;

import com.example.demo.entities.Organization;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User findByEmail(@Param("email")String email);

    @Query("SELECT u FROM User u WHERE u.alta = 1")
    public List<User> listByAltas(); //listarPorAltas
}
