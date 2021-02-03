package com.revature.chronicle.models;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Whitelist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Whitelist {
	
	@Id
	@Column(name="whitelist_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int whitelist_id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="user_id", referencedColumnName = "user_id", nullable = false)
	private List<User> users;
	
}
