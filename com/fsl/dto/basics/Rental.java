package com.fsl.dto.basics;

/*
 * This class demonstrates the use of Many to Many mapping.
 */
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Rental {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private String type;
	
	@ManyToMany(mappedBy="rental") 
	private List<UserDetails> userList = new ArrayList<UserDetails>();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public List<UserDetails> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDetails> userList) {
		this.userList = userList;
	}
	
	public String toString(){
		return name;
	}

}
