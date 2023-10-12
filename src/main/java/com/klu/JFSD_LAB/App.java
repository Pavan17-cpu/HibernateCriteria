package com.klu.JFSD_LAB;

import org.hibernate.Criteria;
import org.hibernate.Query;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("Hibernate.cfg.xml").build();
		Metadata md = new MetadataSources(ssr).getMetadataBuilder().build();
		SessionFactory sf = md.getSessionFactoryBuilder().build();
		Session s = sf.openSession();

		Transaction t;
		Scanner scan = new Scanner(System.in);
		char ch;
		do {
			Student s1 = new Student();

			System.out.print("Enter student name: ");
			s1.setName(scan.nextLine());

			System.out.print("Enter student gender: ");
			s1.setGender(scan.nextLine());
			System.out.print("Enter student department: ");
			s1.setDept(scan.nextLine());

			System.out.print("Enter student program: ");
			s1.setProgram(scan.nextLine());
			System.out.print("Enter student DateofBirth: ");
			s1.setDob(scan.nextLine());

			System.out.print("Enter student graduation status: ");
			s1.setGraduationstatus(scan.nextLine());

			System.out.print("Enter student contact number: ");
			s1.setContactno(scan.nextLong());

			System.out.print("Enter student cgpa: ");
			s1.setCgpa(scan.nextDouble());
			scan.nextLine();

			System.out.print("Enter student backlogs: ");
			s1.setBacklogs(scan.nextInt());
			scan.nextLine();

			t = s.beginTransaction();
			s.save(s1);
			t.commit();
			System.out.println("Insert success");
			System.out.print("Do you want to insert details of another student? (y/n): ");
			ch = scan.nextLine().charAt(0);

		} while (ch == 'y' || ch == 'Y');

		Criteria c = s.createCriteria(Student.class);
		List<Student> l = c.list();
		for (Student stu : l) {
			System.out.println(stu.getId());
			System.out.println(stu.getName());
			System.out.println(stu.getGender());
			System.out.println(stu.getDept());
			System.out.println(stu.getProgram());
			System.out.println(stu.getDob());
			System.out.println(stu.getGraduationstatus());
			System.out.println(stu.getContactno());
			System.out.println(stu.getCgpa());
			System.out.println(stu.getBacklogs());

		}

		Long count = (Long) c.setProjection(Projections.rowCount()).uniqueResult();
		System.out.println("Total number of students: " + count);

		Double sum = (Double) c.setProjection(Projections.sum("cgpa")).uniqueResult();
		System.out.println("Sum of CGPA: " + sum);

		Double average = (Double) c.setProjection(Projections.avg("cgpa")).uniqueResult();
		System.out.println("Average CGPA: " + average);

		Double minCGPA = (Double) c.setProjection(Projections.min("cgpa")).uniqueResult();
		System.out.println("Minimum CGPA: " + minCGPA);

		Double maxCGPA = (Double) c.setProjection(Projections.max("cgpa")).uniqueResult();
		System.out.println("Maximum CGPA: " + maxCGPA);

		Criteria criteria = s.createCriteria(Student.class);
		criteria.setProjection(Projections.projectionList().add(Projections.property("name"), "name")
				.add(Projections.property("cgpa"), "cgpa"))
				.setResultTransformer(Transformers.aliasToBean(Student.class));
		List<Student> students = criteria.list();

		for (Student student : students) {
			System.out.println("Name: " + student.getName() + ", CGPA: " + student.getCgpa());
		}
		Criteria c1 = s.createCriteria(Student.class);
		c1.add(Restrictions.gt("cgpa", 9.2));

		List<Student> la = c1.list();
		for (Student stu : la) {

			System.out.println(stu.getName());

		}
		System.out.print("Enter student ID to delete: ");
		int studentIdToDelete = scan.nextInt();
		scan.nextLine();
		t = s.beginTransaction();
		String hqlDelete = "DELETE FROM Student s WHERE s.id = :studentId";
		Query query = s.createQuery(hqlDelete);
		query.setParameter("studentId", studentIdToDelete);
		int rowsAffected = query.executeUpdate();

		if (rowsAffected > 0) {
			System.out.println("Student with ID " + studentIdToDelete + " deleted successfully.");
		} else {
			System.out.println("Student with ID " + studentIdToDelete + " not found.");
		}

		t.commit();

		System.out.print("Enter student ID to update: ");
		int studentIdToUpdate = scan.nextInt();
		scan.nextLine(); // Consume the newline character left in the buffer

		t = s.beginTransaction();
		Student studentToUpdate = s.get(Student.class, studentIdToUpdate);

		if (studentToUpdate != null) {
			System.out.print("Do you want to update name? (y/n): ");
			ch = scan.nextLine().charAt(0);
			if (ch == 'y' || ch == 'Y') {
				System.out.print("Enter new name: ");
				String newName = scan.nextLine();
				studentToUpdate.setName(newName);
			}

			System.out.print("Do you want to update CGPA? (y/n): ");
			ch = scan.nextLine().charAt(0);
			if (ch == 'y' || ch == 'Y') {
				System.out.print("Enter new CGPA: ");
				double newCGPA = scan.nextDouble();
				studentToUpdate.setCgpa(newCGPA);
			}

			s.update(studentToUpdate);
			t.commit();
			System.out.println("Details updated for student with ID " + studentIdToUpdate);
		} else {
			System.out.println("Student with ID " + studentIdToUpdate + " not found.");
		}
		try {
			t = s.beginTransaction();

			String hqlQuery = "FROM Student ORDER BY id";
			List<Student> students1 = s.createQuery(hqlQuery, Student.class).setFirstResult(2).setMaxResults(10)
					.getResultList();

			for (Student student : students1) {
				System.out.println(student);
			}

			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Criteria c7 = s.createCriteria(Student.class);
		c7.add(Restrictions.gt("cgpa", 9.0));
		List<Student> lo = c7.list();
		for (Student e : lo) {

			System.out.println(e.getName());

		}
		Criteria c8 = s.createCriteria(Student.class);
		c8.add(Restrictions.lt("cgpa", 7.0));
		List<Student> li = c8.list();
		for (Student e : li) {

			System.out.println(e.getName());

		}

		Criteria c81 = s.createCriteria(Student.class);
		c81.add(Restrictions.ge("cgpa", 7.0));
		List<Student> lil = c81.list();
		for (Student e : lil) {

			System.out.println(e.getName());

		}
		Criteria c91 = s.createCriteria(Student.class);
		c91.add(Restrictions.le("cgpa", 7.0));
		List<Student> lis = c91.list();
		for (Student e : lis) {

			System.out.println(e.getName());

		}
		Criteria c911 = s.createCriteria(Student.class);
		c911.add(Restrictions.eq("cgpa", 7.0));
		List<Student> list = c911.list();
		for (Student e : list) {

			System.out.println(e.getName());

		}
		Criteria c9110 = s.createCriteria(Student.class);
		c9110.add(Restrictions.ne("cgpa", 7.0));
		List<Student> list1 = c9110.list();
		for (Student e : list1) {

			System.out.println(e.getName());

		}
		try {
			t = s.beginTransaction();

			// Ascending order
			Criteria criteriaAscending = s.createCriteria(Student.class);
			criteriaAscending.addOrder(Order.asc("name"));
			List<Student> ascendingStudents = criteriaAscending.list();

			System.out.println("Students in ascending order:");
			for (Student student : ascendingStudents) {
				System.out.println(student);
			}

			// Descending order
			Criteria criteriaDescending = s.createCriteria(Student.class);
			criteriaDescending.addOrder(Order.desc("name"));
			List<Student> descendingStudents = criteriaDescending.list();

			System.out.println("Students in descending order:");
			for (Student student : descendingStudents) {
				System.out.println(student);
			}

			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
