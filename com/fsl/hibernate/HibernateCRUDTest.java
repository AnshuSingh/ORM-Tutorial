package com.fsl.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.fsl.dto.crud.CustomerDetails;

/*
 * This class is used to demonstrate the CRUD transactions in hibernate.
 * It also shows the transient, persistent and detached states.
 * Inheritance and the join tables are demonstrated.
 */

public class HibernateCRUDTest {

	public static void main(String[] args){
		
		CustomerDetails customer0 = new CustomerDetails();
		customer0.setName("Saurabh");// tranisent object

		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
		applySettings(configuration.getProperties());
		SessionFactory factory = configuration.buildSessionFactory(builder.build());
		
		Session session = factory.openSession();
		session.beginTransaction();
		
		for (int i = 0; i < 10; i++){
			CustomerDetails customer = new CustomerDetails();
			customer.setName("John "+i);
			session.save(customer);
		}
		
		session.save(customer0);
		customer0.setName("Kumar");
		customer0.setName("Kumar Saurabh");//persistent object
		
		session.getTransaction().commit();
		session.close();
		
		session=factory.openSession();
		session.beginTransaction();
		CustomerDetails customer = (CustomerDetails) session.get(CustomerDetails.class,6);
		session.delete(customer); //Demonstrates D in CRUD
		
		CustomerDetails customer1 = (CustomerDetails) session.get(CustomerDetails.class,5);
		customer1.setName("Shawn 5");
		session.update(customer1); //Demonstrates U in CRUD
		
		CustomerDetails customer2 = (CustomerDetails) session.get(CustomerDetails.class,7);
		
		session.getTransaction().commit();
		session.close();
		
		
		customer1.setName("Shawn; name after session close");
		System.out.println("Detached to Persistent ");
		
		/** 
		 * Detached object to persistent object
		 * Usually in a different method and/or class.
		 */
		session=factory.openSession();
		session.beginTransaction();
		session.update(customer1);
		customer1.setName("Shawn; change after update");
		System.out.println("Check if there is an extra update ");
		session.update(customer2);//updates data only if there is a change
		session.getTransaction().commit();
		session.close();
		
		
		
		
		
		System.out.println("Customer name pulled up is "+customer);// Demonstrates R in CRUD
		
	}
}
