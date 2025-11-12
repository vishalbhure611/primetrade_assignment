package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name= "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role; // Default role

	// Constructor to ensure role is always initialized
	public User() {
		this.role = Role.USER; // Always set default role in constructor
	}

	// PostLoad callback to ensure role is never null after loading from database
	// This handles cases where existing database records have NULL roles
	@PostLoad
	private void ensureRole() {
		if (this.role == null) {
			this.role = Role.USER;
		}
	}

	// Custom getter to ensure role is never null (additional safety)
	public Role getRole() {
		if (this.role == null) {
			this.role = Role.USER;
		}
		return this.role;
	}

}
