package com.fsl.dto.basics;
/*
 * This class demonstrates the use of Lazy and Eager initializations.
 * It also shows the use of Collection Ids and JoinTable annotations.
 */
import javax.persistence.Embeddable;

@Embeddable
public class PhoneNumber {
	
	String type;
	String number;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

}
