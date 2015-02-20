package com.fsl.dto.basics;

import javax.persistence.Entity;

@Entity
//@DiscriminatorValue("Bike")
public class TwoWheeler extends Vehicle {

	String steeringHandle;

	public String getSteeringHandle() {
		return steeringHandle;
	}

	public void setSteeringHandle(String steeringHandle) {
		this.steeringHandle = steeringHandle;
	}
}
