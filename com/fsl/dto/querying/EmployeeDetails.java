package com.fsl.dto.querying;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/*
 * This class shows the use of named queries and named native queries.
 * It also shows the transient, persistent and detached states.
 */

@Entity
@NamedQuery(name = "EmployeeDetails.byId", query = "from EmployeeDetails where id = :empId")
@NamedNativeQuery(name="EmployeeDetails.byName", 
				  query = "select * from Employee_details where name = :empName",
				  resultClass = EmployeeDetails.class) //Use only if no other way
@Table(name="Employee_details")
public class EmployeeDetails {
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
