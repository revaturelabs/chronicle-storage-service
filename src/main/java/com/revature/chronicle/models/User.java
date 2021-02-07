package com.revature.chronicle.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a user of Chronicle
 * -NOTE- may change depending on progress
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userID;

    private String name;
    
    private String email;
}
