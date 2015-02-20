package com.fsl.dto.basics;

import javax.persistence.Entity;

@Entity
//@DiscriminatorValue("Car")
public class FourWheeler extends Vehicle {
	
	String steeringWheel;

	public String getSteeringWheel() {
		return steeringWheel;
	}

	public void setSteeringWheel(String steeringWheel) {
		this.steeringWheel = steeringWheel;
	}

}
