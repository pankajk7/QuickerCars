package com.pankaj.quicker.modal;

import java.io.Serializable;

public class City implements Serializable{
	private static final long serialVersionUID = -818281809160459402L;
	String city;
	String users;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
}
