package com.connectx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "username" , nullable = false , unique = true , length = 255)
    private String username;

    @Column(name = "pass_hash" , nullable = false)
    private String passwordHash;

    @Column(name = "email" , nullable = false , unique = true , length = 255)
    private String email;

    @Column(name = "display_name" , nullable = false)
    private String displayName;

    @Column(name = "created_at" , nullable = false)
    private String createdAt;

    public UserEntity() {

    }

    @PrePersist
    public void onCreate(){
        if(createdAt == null){
            createdAt = LocalDateTime.now().toString();
        }
    }

}
