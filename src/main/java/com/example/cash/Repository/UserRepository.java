package com.example.cash.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cash.Models.User;

public interface UserRepository extends JpaRepository<User, Integer> {}
