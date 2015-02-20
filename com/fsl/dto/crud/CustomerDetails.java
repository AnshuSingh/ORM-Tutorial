package com.fsl.dto.crud;

/*
 * This class is used to demonstrate the CRUD transactions in hibernate.
 * It also shows the transient, persistent and detached states.
 */
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@DynamicInsert
@DynamicUpdate//update data only if there is a change
@SelectBeforeUpdate 
public class CustomerDetails {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	
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
	public String toString(){
		return name;
	}
}
