package com.ronnachate.inventory.user.repository;

import com.ronnachate.inventory.user.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {}
