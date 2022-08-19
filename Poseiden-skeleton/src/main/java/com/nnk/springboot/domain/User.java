package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="Id", unique = true, nullable = false)
    private Integer id;
    
    @NotEmpty(message = "Cannot be empty")
    @Column(name="username")
    private String username;

    @ValidPassword(message = "Password must be at least 8 characters long and 30 max."
    		+ "Password must contain at least one uppercase letter."
    		+ "Password must at least contain 1 number and 1 special character (allowed) : !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~)")
    @Column(name="password")
    private String password;

    @NotEmpty(message = "Cannot be empty")
    @Column(name="fullname")
    private String fullname;

    @NotEmpty(message = "You must choose a role")
    @Column(name="role")
    private String role;
    
    

    public User(Integer id, String username, String password, String fullname,  String role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.role = role;
	}
    
    

	public User() {
	}



	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
