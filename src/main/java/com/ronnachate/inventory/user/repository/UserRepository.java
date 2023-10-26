package com.ronnachate.inventory.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ronnachate.inventory.user.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
}
