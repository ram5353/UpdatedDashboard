package com.hibernateCon;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.utilities.Admin;
import com.utilities.Examiner;
import com.utilities.Exams;
import com.utilities.Person;
import com.utilities.Student;


public class RegisterDetails {
	static int id=0;
	
	
	
	
	public RegisterDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void addStudentDetails(int id,String fname,String lname, String password, String email,
            String phone,String university) {
        try {
            // 1. configuring hibernate
            Configuration configuration = new Configuration().configure();
 
            // 2. create sessionfactory
            SessionFactory sessionFactory = configuration.buildSessionFactory();
 
            // 3. Get Session object
            Session session = sessionFactory.openSession();
 
            // 4. Starting Transaction
            Transaction transaction = session.beginTransaction();
            Student user = new Student();
            user.setId(id);
            user.setFname(fname);
            user.setLname(lname);
            user.setUpass(password);
            user.setUemail(email);
            user.setUmobile(phone);
            user.setUniversity(university);
            Exams exam=(Exams) session.load(Exams.class, 1001);
            List<Exams> exams=new ArrayList<Exams>();
            exams.add(exam);
            exam=(Exams) session.load(Exams.class, 1002);
            exams.add(exam);
            user.setExams(exams);
            session.save(user);
            transaction.commit();
            System.out.println("\n\n Details Added \n");
 
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            System.out.println("error");
        }
 
    }
	public void addExaminerDetails(int id,String fname,String lname, String password, String email,
            String phone,String organisation) {
        try {
            // 1. configuring hibernate
            Configuration configuration = new Configuration().configure();
 
            // 2. create sessionfactory
            SessionFactory sessionFactory = configuration.buildSessionFactory();
 
            // 3. Get Session object
            Session session = sessionFactory.openSession();
 
            // 4. Starting Transaction
            Transaction transaction = session.beginTransaction();
            Examiner user = new Examiner();
            user.setId(id);
            user.setFname(fname);
            user.setLname(lname);
            user.setUpass(password);
            user.setUemail(email);
            user.setUmobile(phone);
            user.setOrganisation(organisation);
            List <Exams> exams=new ArrayList<Exams>();
            Exams exam=new Exams();
    		exam.setId(1001);
    		exam.setExamname("Java");
    		exams.add(exam);
    		exam=new Exams();
    		exam.setId(1002);
    		exam.setExamname("pyhton");
    		exams.add(exam);
    		user.setExams(exams);
            session.save(user);
            transaction.commit();
            System.out.println("\n\n Details Added \n");
 
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            System.out.println("error");
        }
        }
        
        public void addAdminDetails(int id,String fname,String lname, String password, String email,
                String phone,String organisation) {
            try {
                // 1. configuring hibernate
                Configuration configuration = new Configuration().configure();
     
                // 2. create sessionfactory
                SessionFactory sessionFactory = configuration.buildSessionFactory();
     
                // 3. Get Session object
                Session session = sessionFactory.openSession();
     
                // 4. Starting Transaction
                Transaction transaction = session.beginTransaction();
    
                Admin user = new Admin();

                
          
                user.setApproval(true);
                user.setId(id);
                user.setFname(fname);
                user.setLname(lname);
                user.setUpass(password);
                user.setUemail(email);
                user.setUmobile(phone);
                
              
                session.save(user);
                transaction.commit();
                System.out.println("\n\n Details Added \n");
     
            } catch (HibernateException e) {
                System.out.println(e.getMessage());
                System.out.println("error");
            }
 
    }
	

}
