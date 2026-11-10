package com.connectx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "username" , nullable = false , unique = true , length = 255)
    private String username;

    @Column(name = "pass_hash" , nullable = false)
    private String password_hash;

    @Column(name = "email" , nullable = false , unique = true , length = 255)
    private String email;

    @Column(name = "display_name" , nullable = false)
    private String display_name;

    @Column(name = "created_at" , nullable = false)
    private String created_at;

    protected UserEntity() {

    }

    @PrePersist
    public void onCreate(){
        if(created_at == null){
            created_at = LocalDateTime.now().toString();
        }
    }

}
