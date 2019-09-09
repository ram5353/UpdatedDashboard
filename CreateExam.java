package com.hibernateCon;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
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

import com.utilities.Examiner;
import com.utilities.Exams;

/**
 * Servlet implementation class CreateExam
 */
@WebServlet("/CreateExam")
public class CreateExam extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Examiner examiner;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateExam() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// TODO Auto-generated method stub
				 Configuration configuration = new Configuration().configure();
				 
		         // 2. create sessionfactory
		         SessionFactory sessionFactory = configuration.buildSessionFactory();

		         // 3. Get Session object
		         Session session = sessionFactory.openSession();
		         
		         // 4. Starting Transaction
		         Transaction transaction = session.beginTransaction();
		         HttpSession httpsession=request.getSession();
		         String examname=request.getParameter("exam_name");
		         String email=(String) httpsession.getAttribute("email");
		         Query query1=session.createQuery(" select p.id from Person p where p.uemail like :u_email and user_type like :type").setParameter("u_email", email).setParameter("type", "examiner");                                    
					List list1=query1.list();
					Iterator iter1=list1.iterator();
					
					while(iter1.hasNext())
					{
						int temp=(int)iter1.next();
					 examiner =(Examiner) session.load(Examiner.class,temp);

					}
					System.out.println(examiner.getFname());
					List<Exams> exams=examiner.getExams();
					Exams exam=new Exams();
					query1=session.createQuery("from Exams as e");
					list1=query1.list();
					int id=list1.size();
					id++;
					
					
					
					exam.setId(id);
					exam.setExamname(examname);
					exams.add(exam);
					httpsession.setAttribute("exam_id", id);
					session.update(examiner);
					transaction.commit();
					session.close();
					response.sendRedirect("http://localhost:8050/Project/examples/uploadquestions.jsp");
		
		
	}

}
