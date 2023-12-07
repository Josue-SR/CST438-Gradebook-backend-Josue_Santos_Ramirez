package com.cst438.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="user")
public class User {
	@Id
    @Column(name = "user")
	private String user;
	private String password;
	private String role;
	
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String pwd) {
		this.password = pwd;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}