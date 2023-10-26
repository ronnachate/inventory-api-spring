package com.ronnachate.inventory.user.repository;

import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ronnachate.inventory.user.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, UUID> {
}
