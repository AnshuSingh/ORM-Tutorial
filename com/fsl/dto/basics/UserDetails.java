package com.fsl.dto.basics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity 
@Table (name="USER_DETAILS")
public class UserDetails {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;
	
	//@Transient
	private String userName;
	
	@Temporal (TemporalType.DATE)
	private Date joinedDate;
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="street", column=@Column(name="HOME_STREET")),
		@AttributeOverride(name="city", column=@Column(name="HOME_CITY")),
		@AttributeOverride(name="pincode", column=@Column(name="HOME_PINCODE")),
		@AttributeOverride(name="state", column=@Column(name="HOME_STATE"))})
	private Address homeAddress;
	
	@Embedded
	private Address officeaddress;
	
	@ElementCollection (fetch=FetchType.EAGER)
	@JoinTable(name="USER_CONTACT", joinColumns=@JoinColumn(name="USER_ID"))
	@GenericGenerator(name="hilo-gen", strategy="hilo")
	@CollectionId(columns = { @Column(name="PHONE_ID") }, generator = "hilo-gen", type = @Type(type = "long"))
	private List<PhoneNumber> listOfPhoneNumbers = new ArrayList<PhoneNumber>();
	
	@Lob
	private String description;
	
	@OneToMany (mappedBy="user", cascade=CascadeType.PERSIST)
	private List<Vehicle> vehicle = new ArrayList<Vehicle>();
	
	@ManyToMany
	@JoinTable(name="UserRental_Info", joinColumns=@JoinColumn(name="user_id"))
	private List<Rental> rental = new ArrayList<Rental>();
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getJoinedDate() {
		return joinedDate;
	}
	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Address getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(Address home_address) {
		this.homeAddress = home_address;
	}
	public Address getOfficeaddress() {
		return officeaddress;
	}
	public void setOfficeaddress(Address officeaddress) {
		this.officeaddress = officeaddress;
	}
	public List<PhoneNumber> getListOfPhoneNumbers() {
		return listOfPhoneNumbers;
	}
	public void setListOfPhoneNumbers(List<PhoneNumber> listOfPhoneNumbers) {
		this.listOfPhoneNumbers = listOfPhoneNumbers;
	}
	public void setVehicle(List<Vehicle> vehicle) {
		this.vehicle = vehicle;
	}
	public List<Vehicle> getVehicle() {
		return vehicle;
	}
	public List<Rental> getRental() {
		return rental;
	}
	public void setRental(List<Rental> rental) {
		this.rental = rental;
	}
	
	public String toString(){
		return userName;
	}
	

}
