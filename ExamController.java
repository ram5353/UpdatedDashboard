package com.kar;

import java.io.IOException;
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

import com.utilities.Student;

/**
 * Servlet implementation class ExamController
 */
@WebServlet("/ExamController")
public class ExamController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Student student;
	int presentQuestion_index = 0;
	QuestionsUtility Q;
    public ExamController() {
        super();
  
    }
    
    @Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		Q = new QuestionsUtility();
	}

	public void nextQ(){
    	presentQuestion_index++;
    	
    }
    public void prevQ(){
    	presentQuestion_index--;
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = request.getRequestDispatcher("Solve.jsp");
		
		Q.Category(request.getParameter("Start"));
		request.setAttribute("currentQuestion", presentQuestion_index);
		request.setAttribute("question",Q.question(presentQuestion_index));
		request.setAttribute("totalQuestions",Q.NumberOfQuestions());
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		System.out.println(">" + request.getParameter("N"));
		System.out.println("<" + request.getParameter("P"));
		RequestDispatcher rd = request.getRequestDispatcher("Solve.jsp");
		//System.out.println(request.getParameter("P"));
		if(request.getParameter("refresh") != null)
		{
		
		//Next Question toggle
		if(request.getParameter("next")!=null) {
			//request.setAttribute("next", null);
			presentQuestion_index = Integer.parseInt(request.getParameter("N"));
			if(presentQuestion_index < Q.NumberOfQuestions()) {
				//nextQ();
				request.setAttribute("question",Q.question(presentQuestion_index));
				request.setAttribute("currentQuestion", presentQuestion_index);
				request.setAttribute("cOption",Q.getResponse(presentQuestion_index));
				request.setAttribute("totalQuestions",Q.NumberOfQuestions());
				rd.forward(request, response);
				//doGet(request, response);
				//response.sendRedirect("Redirect?ADD=success");
				
			}
			else {
				request.setAttribute("question",Q.question(Q.NumberOfQuestions()-1));
				request.setAttribute("currentQuestion", Q.NumberOfQuestions()-1);
				request.setAttribute("cOption",Q.getResponse(presentQuestion_index));
				request.setAttribute("totalQuestions",Q.NumberOfQuestions());
				rd.forward(request, response);
				//doGet(request, response);
			}
		}
		//Previous question toggle
		if(request.getParameter("prev")!=null) {
			//request.setAttribute("next", null);
			presentQuestion_index = Integer.parseInt(request.getParameter("P"));
			if(presentQuestion_index >= 0) {
				//prevQ();
				request.setAttribute("question",Q.question(presentQuestion_index));
				request.setAttribute("currentQuestion", presentQuestion_index);
				request.setAttribute("cOption",Q.getResponse(presentQuestion_index));
				request.setAttribute("totalQuestions",Q.NumberOfQuestions());
				rd.forward(request, response);
				//doGet(request, response);
			}	
			else {
				request.setAttribute("question",Q.question(0));
				request.setAttribute("currentQuestion", 0);
				request.setAttribute("cOption",Q.getResponse(presentQuestion_index));
				request.setAttribute("totalQuestions",Q.NumberOfQuestions());
				rd.forward(request, response);
				//doGet(request, response);
			}
		}
		//Save Question toggle
		if (request.getParameter("save")!=null) {
			presentQuestion_index = Integer.parseInt(request.getParameter("SV"));
			System.out.println(presentQuestion_index);
			System.out.println(request.getParameter("op"));
			request.setAttribute("currentQuestion", presentQuestion_index);
			request.setAttribute("question",Q.question(presentQuestion_index));
			request.setAttribute("chosenOption",Q.saveResponse(request.getParameter("op"),presentQuestion_index));
			request.setAttribute("cOption",Q.getResponse(presentQuestion_index));
			request.setAttribute("totalQuestions",Q.NumberOfQuestions());
			rd.forward(request, response);
		}
		
		
		// 1. configuring hibernate
        Configuration configuration = new Configuration().configure();

        // 2. create sessionfactory
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // 3. Get Session object
        Session session = sessionFactory.openSession();

        // 4. Starting Transaction
        Transaction transaction = session.beginTransaction();
        
        HttpSession httpsession=request.getSession(false);
        
        String email=(String)httpsession.getAttribute("email");
		
        Query query1=session.createQuery(" select p.id from Person p where p.uemail like :u_email and user_type like :type").setParameter("u_email", email).setParameter("type", "student");                                    
			List list1=query1.list();
			Iterator iter1=list1.iterator();
			
			while(iter1.hasNext())
			{
				int temp=(int)iter1.next();
			 student =(Student) session.load(Student.class,temp);

			}
	
			
			
		String eid=(String) httpsession.getAttribute("exam_id");
		
		
		
		
		
	
		
		if(request.getParameter("sub")!=null) {
			request.setAttribute("score",Q.submitResponse(student.getId(),Integer.parseInt(eid)));
			RequestDispatcher rd2 = request.getRequestDispatcher("result.jsp");
			rd2.forward(request, response);
		}
		
		}

		else {
			rd.forward(request, response);
		}


		
	}

}
