package com.ronnachate.inventory.user.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_statuses")
public class UserStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 100)
    private String name;

    // jpa only
    protected UserStatus() {
    }

    public UserStatus(long id) {
        this.id = id;
    }

    public UserStatus(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
