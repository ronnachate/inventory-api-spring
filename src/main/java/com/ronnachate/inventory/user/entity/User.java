package com.ronnachate.inventory.user.entity;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", length = 50)
    private String title;

    @Column( name= "name", length= 100 )
    private String name;

    @Column( name= "lastname", length= 100 )
    private String lastname;

    @Column( name= "username", length= 50, unique=true)
    private String username;

    @CreationTimestamp
    @Column( name= "createdAt", nullable = true )
    private Date createdAt;

    @UpdateTimestamp
    @Column( name= "updatedAt", nullable= true )
    private Date updatedAt;

    public User(String title,String name, String lastname, String username) {
        this.title = title;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
    }
    
    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
