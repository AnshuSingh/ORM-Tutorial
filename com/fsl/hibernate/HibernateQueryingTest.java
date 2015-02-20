package com.fsl.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fsl.dto.crud.CustomerDetails;
import com.fsl.dto.querying.EmployeeDetails;

/*
 * This class shows the use of HQL, pagination, parameter binding, SQL injection,
 * named queries and CriteriaAPI(Restrictions projections, ordering and Query By Example(QBE)).
 * It also shows the transient, persistent and detached states.
 */
public class HibernateQueryingTest {

	@SuppressWarnings("unchecked")
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
			EmployeeDetails employee = new EmployeeDetails();
			employee.setName("John "+i);
			session.save(employee);
		}
		
		for (int i = 0; i < 10; i++){
			CustomerDetails customer = new CustomerDetails();
			customer.setName("John "+i);
			session.save(customer);
		}
		
		session.getTransaction().commit();
		session.close();
		
		String minId = "5 or 1 = 1 "; //used to do SQL injection
		String maxId = "5";
		
		session=factory.openSession();
		session.beginTransaction();
		
		/*
		 * HQL examples
		 * use class name for table and member name for column
		 */
		Query query1 = session.createQuery("select name from EmployeeDetails");
		List<String> nameList = (ArrayList<String>) query1.list();
		
		/*
		 * HQL example for map
		 */
		Query query2 = session.createQuery("select new map(id,name) from EmployeeDetails");
		List<Map<Integer,String>> nameIdMap = (ArrayList<Map<Integer,String>>) query2.list();
		
		/*
		 * Pagination
		 */
		Query query3 = session.createQuery("select new map(a.name,b.name) from EmployeeDetails a, CustomerDetails b where a.name = b.name");
		query3.setFirstResult(2);  //offset (start of page)
		query3.setMaxResults(2); //limit (end of page)
		List<Map<String,String>> customerEmployeeMap = (ArrayList<Map<String,String>>) query3.list();
		
		/*
		 * SQL Injection
		 */
		Query query = session.createQuery("from EmployeeDetails where id > " + minId);//potential for SQL injection
		List<EmployeeDetails> employees = (ArrayList<EmployeeDetails>) query.list();
		
		/*
		 * Parameter Binding
		 * query4: position wise; deprecated now
		 */
		Query query4 = session.createQuery("from EmployeeDetails where id < ?");
		query4.setInteger(0,Integer.parseInt(maxId));//<position, parameter value>
		List<EmployeeDetails> oldEmployees = (ArrayList<EmployeeDetails>)query4.list();
		
		/*
		 * Parameter Binding
		 * query5: named placeholder
		 */
		Query query5 = session.createQuery("from EmployeeDetails where name = :empName");
		query5.setString("empName", "John 8");
		String searchResult = ((EmployeeDetails)query5.list().get(0)).getName();
		
		/*
		 * Named Query
		 */
		Query query6= session.getNamedQuery("EmployeeDetails.byId");
		query6.setInteger("empId", 2);
		List<EmployeeDetails> namedQueryResult = (ArrayList<EmployeeDetails>)query6.list();
		
		/*
		 * Named Native Query
		 */
		Query query7= session.getNamedQuery("EmployeeDetails.byName");
		query7.setString("empName", "John 4");
		List<EmployeeDetails> namedNativeQueryResult = (ArrayList<EmployeeDetails>)query7.list();
		
		
		/*
		 * Criteria API
		 * "And" Restrictions
		 */
		Criteria criteria1 = session.createCriteria(EmployeeDetails.class);
		criteria1.add(Restrictions.like("name","John %"))  //equivalent to "and" clause in SQL
				.add(Restrictions.between("id",5, 9));
		List<EmployeeDetails> criteriaResult = (ArrayList<EmployeeDetails>)criteria1.list();

		/*
		 * Criteria API
		 * "Or" Restrictions
		 */
		Criteria criteria2 = session.createCriteria(EmployeeDetails.class);
		criteria2.add(Restrictions.or(Restrictions.between("id", 2, 5), Restrictions.between("id", 7, 9)));
		List<EmployeeDetails> orCriteriaResult = (ArrayList<EmployeeDetails>)criteria2.list();
		
		/*
		 * Projections with Criteria API
		 */
		Criteria criteria3 = session.createCriteria(EmployeeDetails.class)
									.setProjection(Projections.property("id"));//max, count, property
		List<EmployeeDetails> projectionResult = (ArrayList<EmployeeDetails>)criteria3.list();
		
		/*
		 * Ordering with Criteria API
		 */
		Criteria criteria4 = session.createCriteria(EmployeeDetails.class)
									.addOrder(Order.desc("id"));
		List<EmployeeDetails> orderResult = (ArrayList<EmployeeDetails>)criteria4.list();	
		
		/*
		 * Query By Example with Criteria API
		 */
		EmployeeDetails exampleEmployee = new EmployeeDetails();
		exampleEmployee.setId(2); // null properties and primary key properties are not considered.
		exampleEmployee.setName("John 1%");
		Example example = Example.create(exampleEmployee).excludeProperty("id").enableLike();
		Criteria criteria5 = session.createCriteria(EmployeeDetails.class)
									.add(example);
		List<EmployeeDetails> exampleResult = (ArrayList<EmployeeDetails>)criteria5.list();
		
		session.getTransaction().commit();
		session.close();
		
		System.out.println("string of objects:" + employees);
		System.out.println("string of names:" + nameList);
		System.out.println("map of id and names:" + nameIdMap);
		System.out.println("map of customer and employee names:" + customerEmployeeMap);
		System.out.println("older employees:" + oldEmployees);
		System.out.println("Search result: "+ searchResult);
		System.out.println("Named query result: "+ namedQueryResult);
		System.out.println("Named native query result: "+ namedNativeQueryResult);
		System.out.println("Criteria result for 'and' clause: "+ criteriaResult);
		System.out.println("Criteria result for 'or' clause: "+ orCriteriaResult);
		System.out.println("Criteria result with projections: "+ projectionResult);
		System.out.println("Criteria result with ordering: "+ orderResult);
		System.out.println("Criteria result with query by example: "+ exampleResult);
		
	}
}
