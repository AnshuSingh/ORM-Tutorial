package com.fsl.dto.basics;
/*
 * This class demonstrates embeddable values.
 * It shows the use of AttributeOverride annotation.
 */
import javax.persistence.Embeddable;

@Embeddable
public class Address {
	
	private String street;
	private String city;
	private String state;
	private int pincode;
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getPincode() {
		return pincode;
	}
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

}
