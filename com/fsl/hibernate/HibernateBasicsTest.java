package com.fsl.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.fsl.dto.basics.Address;
import com.fsl.dto.basics.FourWheeler;
import com.fsl.dto.basics.PhoneNumber;
import com.fsl.dto.basics.Rental;
import com.fsl.dto.basics.TwoWheeler;
import com.fsl.dto.basics.UserDetails;
import com.fsl.dto.basics.Vehicle;

public class HibernateBasicsTest {
	
	public static void main(String[] args){
		
		Vehicle vehicle = new Vehicle();
		vehicle.setName("Lamborghini");
		
		Vehicle vehicle2 = new Vehicle();
		vehicle2.setName("Audi");
		
		Vehicle vehicle3 = new Vehicle();
		vehicle3.setName("Tesla");
		
		TwoWheeler bike = new TwoWheeler();
		bike.setName("Honda");
		bike.setSteeringHandle("Bike Steering Handle");
		
		FourWheeler tesla = new FourWheeler();
		tesla.setName("Tesla 3.0");
		tesla.setSteeringWheel("Awesome steering wheel");
		
		Rental rental1 = new Rental();
		rental1.setType("Camping");
		rental1.setName("Tent");
		
		Rental rental2 = new Rental();
		rental2.setType("Camping");
		rental2.setName("FireWood");
		
		Rental rental3 = new Rental();
		rental3.setType("Camping");
		rental3.setName("Gas-stove");
		
		
		UserDetails user = new UserDetails();
		user.setUserName("John");
		
		Address addr = new Address();
		addr.setStreet("1019 University Park Drive");
		addr.setCity("Austin");
		addr.setPincode(78749);
		addr.setState("TX");
		
		PhoneNumber number1 = new PhoneNumber();
		number1.setType("mobile");
		number1.setNumber("432-632-1294");
		
		PhoneNumber number2 = new PhoneNumber();
		number2.setType("home");
		number2.setNumber("328-489-3891");
		
		PhoneNumber number3 = new PhoneNumber();
		number3.setType("office");
		number3.setNumber("912-234-2590");
		
		user.getListOfPhoneNumbers().add(number1);
		user.getListOfPhoneNumbers().add(number2);
		user.getListOfPhoneNumbers().add(number3);
		
		user.setHomeAddress(addr);
		user.setOfficeaddress(addr);
		user.setJoinedDate(new Date());
		user.setDescription("First User");
		
		user.getVehicle().add(vehicle);
		user.getVehicle().add(vehicle2);
		user.getVehicle().add(vehicle3);
		vehicle.setUser(user);
		vehicle2.setUser(user);
		vehicle3.setUser(user);
		
		user.getRental().add(rental1);
		user.getRental().add(rental2);
		user.getRental().add(rental3);
		rental1.getUserList().add(user);
		rental2.getUserList().add(user);
		rental3.getUserList().add(user);
		
		
		UserDetails user2 = new UserDetails();
		user2.setHomeAddress(new Address());
		user2.setOfficeaddress(new Address());
		user2.setUserName("Shawn");
		user2.setJoinedDate(new Date());
		user2.setDescription("Second User");
		user2.getRental().add(rental2);
		user2.getRental().add(rental3);
		rental2.getUserList().add(user2);
		rental3.getUserList().add(user2);
		
		
		
		
		
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
		applySettings(configuration.getProperties());
		SessionFactory factory = configuration.buildSessionFactory(builder.build());
		
		Session session = factory.openSession();
		session.beginTransaction();
		session.persist(user);
		session.save(user2);
		session.save(bike);
		session.save(tesla);
		/*session.save(vehicle);
		session.save(vehicle2);
		session.save(vehicle3);*/
		session.save(rental1);
		session.save(rental2);
		session.save(rental3);
		session.getTransaction().commit();
		session.close();
		
		user = null;
		vehicle = null;
		rental1=null;
		
		session = factory.openSession();
		session.beginTransaction();
		user =(UserDetails) session.get(UserDetails.class, 1);
		vehicle = (Vehicle) session.get(Vehicle.class,1);
		rental1 = (Rental) session.get(Rental.class,  2);
		System.out.println ("user:" + vehicle.getUser() + " vehicle: "+vehicle);
		System.out.println ("user:" + user + " vehicle 2 : "+user.getVehicle().get(2));
		System.out.println("user: " + user + " Rental 2: " + user.getRental());
		System.out.println("Rental: " + rental1 + " User 2: "+rental1.getUserList());
		session.close();
		System.out.println("size = :" +user.getListOfPhoneNumbers().size());

		
	}

}
