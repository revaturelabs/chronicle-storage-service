package com.revature.chronicle.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a user of Chronicle
 * -NOTE- may change depending on progress
 */
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@Column(name = "user_id")
    private String userID;
	
	@Column(name = "isAdmin")
    private boolean admin;
	
//    @ManyToMany(mappedBy = "whitelist", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    private List<Video> whitelistedVids;
//    
//    @ManyToMany(mappedBy = "whitelist", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    private List<Note> whitelistedNotes;
}
