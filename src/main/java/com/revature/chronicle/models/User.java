package com.revature.chronicle.models;

import lombok.Data;

import javax.persistence.*;

/**
 * Represents a user of Chronicle
 * -NOTE- may change depending on progress
 */
@Entity
@Table(name="user")
@Data
public class User {
    @Id
    @Column(name = "user_id",columnDefinition = "serial primary key")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @Column(name = "username")
    private String username;
}
