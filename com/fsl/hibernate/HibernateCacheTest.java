package com.fsl.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.fsl.dto.cache.Directory;

/*
 * This class shows how to set the Second level Cache in hibernate. 
 * Precaution: Careful with the usage of query cache. A lot of data and data that is not required will effect the performance.
 */

public class HibernateCacheTest {
	
	public static void main(String[] args){
		
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
		applySettings(configuration.getProperties());
		SessionFactory factory = configuration.buildSessionFactory(builder.build());
		
		Session session = factory.openSession();
		session.beginTransaction();
		/*
		 * Populate data
		 */
		for (int i = 0; i < 10; i++){
			Directory directory = new Directory();
			directory.setName("John "+i);
			directory.setPhoneNumber("768-564-356"+i);
			session.save(directory);
		}
				
		session.getTransaction().commit();
		session.close();
		
		session = factory.openSession();
		session.beginTransaction();
		
		Directory directory1 = (Directory)session.get(Directory.class,4);
		
		Query query = session.createQuery("from Directory directory where directory.id = 8");
		query.setCacheable(true); //set values in Cache.
		List<Directory> directoryEntries = (ArrayList<Directory>) query.list();
		
		
		session.getTransaction().commit();
		session.close(); // session closed , first level cache closed.
		
		/*
		 * Second level cache being used.
		 * No new Select statements getting generated in session2.
		 */
		Session session2 = factory.openSession();
		session2.beginTransaction();
		
		Directory directory2 = (Directory)session2.get(Directory.class,4);
		
		Query query2 = session2.createQuery("from Directory directory where directory.id = 8");
		query2.setCacheable(true); // pull up values from Cache.
		List<Directory> directoryEntries2 = (ArrayList<Directory>) query2.list();
		
		session2.getTransaction().commit();
		session2.close();
		
	}

}
