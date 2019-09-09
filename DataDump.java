package Registration_DB;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.hibernateCon.RegisterDetails;

@WebServlet("/DataDump")
public class DataDump extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DataDump() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out =  response.getWriter();
		
	   /* int id;
	    String id_temp;*/
		String fname,lname,uemail,upass;
		String umobile,org;
		
/*		id_temp=request.getParameter("id");
		id=Integer.parseInt(id_temp);*/
		fname=request.getParameter("fname");
		lname=request.getParameter("lname");
		uemail=request.getParameter("uemail");
		upass=request.getParameter("upass");
		umobile=request.getParameter("umobile");
		org=request.getParameter("org");
		
		
		// 1. configuring hibernate
        Configuration configuration = new Configuration().configure();

        // 2. create sessionfactory
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // 3. Get Session object
        Session session = sessionFactory.openSession();

        // 4. Starting Transaction
        Transaction transaction = session.beginTransaction();
		
		
		
        Query query1=session.createQuery("from Person as p");  
        List list1=query1.list();
        int id=list1.size();
		id++;
		
		
		
		
		
		
		
		
		
		
		String user_type=request.getParameter("user_type");
		System.out.println("outside_if");
		if(user_type.equals("Admin")) {
			RegisterDetails admin = new RegisterDetails();
			admin.addAdminDetails( id,fname,lname, upass, uemail,umobile,org);
			System.out.println("admin");
			request.setAttribute("email", uemail);
			RequestDispatcher rd = request.getRequestDispatcher("login.html");
    		rd.forward(request,  response);

			
		}else if(user_type.equals("Student")) {
			RegisterDetails student = new RegisterDetails();
			student.addStudentDetails( id,fname,lname, upass, uemail,umobile,org);
			System.out.println("student");
			request.setAttribute("email", uemail);
			RequestDispatcher rd = request.getRequestDispatcher("login.html");
    		rd.forward(request,  response);
			
		}else if(user_type.equals("Examiner")) {
			RegisterDetails examiner = new RegisterDetails();
			examiner.addExaminerDetails( id,fname,lname, upass, uemail,umobile,org);
			System.out.println("examiner");
			request.setAttribute("email", uemail);
			RequestDispatcher rd = request.getRequestDispatcher("login.html");
    		rd.forward(request,  response);
			
		} else {
			System.out.println("else");
			out.println("Invalid Details! Please try again!");
			RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
    		rd.include(request,  response);
		}
		
		
	}

}
