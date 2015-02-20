package com.fsl.dto.basics;
/*
 * This class shows the use of Many To One mapping.
 */
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
//@DiscriminatorColumn(name="VEHICLE_TYPE")
public class Vehicle {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	@ManyToOne 
	@JoinColumn(name="user_id")
	//@NotFound(action = NotFoundAction.IGNORE)
	private UserDetails user;
	
	private String licenseNumber;
	
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	
	public UserDetails getUser() {
		return user;
	}
	public void setUser(UserDetails user) {
		this.user = user;
	}
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
