package com.hibernateCon;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.oreilly.servlet.MultipartRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.utilities.Examiner;
import com.utilities.Exams;
import com.utilities.Questions;



/**
 * Servlet implementation class UploadFile
 */
@WebServlet("/UploadFile")
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String path="E:\\Vihari\\Project\\WebContent\\uploads";
	Examiner examiner;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
         HttpSession httpsession=request.getSession(false);
//         int id=Integer.parseInt(request.getParameter("examid"));
//         System.out.println(id);
//         String examname=request.getParameter("examname");
//         String email=(String) httpsession.getAttribute("email");
//         Query query1=session.createQuery(" select p.id from Person p where p.uemail like :u_email and user_type like :type").setParameter("u_email", email).setParameter("type", "examiner");                                    
//			List list1=query1.list();
//			Iterator iter1=list1.iterator();
//			
//			while(iter1.hasNext())
//			{
//				int temp=(int)iter1.next();
//			 examiner =(Examiner) session.load(Examiner.class,temp);
//
//			}
		//	List<Exams> exams=examiner.getExams();
			
			int temp=(int) httpsession.getAttribute("exam_id");
			
			Exams exam= (Exams) session.load(Exams.class,temp);
			
			
	
			/*exam.setId(id);
			exam.setExamname(examname);
			exams.add(exam);
			examiner.setExams(exams);
			session.update(examiner);
			*/
         
		MultipartRequest m=new MultipartRequest(request,path);
		File file=new File(path);
		
		String arr[]=file.list();
		for (String a:arr) {
			System.out.println(a);
		}
		
		

		Query query1=session.createQuery("from Questions as q");
		List list1=query1.list();
		int id=list1.size();
		
		
		  //FileInputStream input_document = new FileInputStream(new File(path+"\\Book1.xlsx")); //Read XLS document - Office 97 -2003 format     
          XSSFWorkbook my_xls_workbook = new XSSFWorkbook(path+"\\"+arr[0]); //Read the Excel Workbook in a instance object    
          XSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0); //This will read the sheet for us into another object
          Iterator<Row> rowIterator = my_worksheet.rowIterator(); // Create iterator object
    /*      exam=(Exams) session.load(Exams.class,new Integer(id));*/
          while(rowIterator.hasNext()) {
        	  		id++;
        	  		Questions question=new Questions();
        	  		question.setId(id);
        	  		question.setExam(exam);
                  Row row = rowIterator.next(); //Read Rows from Excel document 
                  Iterator<Cell> cellIterator = row.cellIterator();//Read every column for every row that is READ
                  Cell cell = cellIterator.next(); 
                  question.setQuestion(cell.getStringCellValue());
                  cell = cellIterator.next(); 
                  question.setOption_1(cell.getStringCellValue());
                  cell = cellIterator.next(); 
                  question.setOption_2(cell.getStringCellValue());
                  cell = cellIterator.next();
                  question.setOption_3(cell.getStringCellValue());
                  cell = cellIterator.next();
                  question.setOption_4(cell.getStringCellValue());
                  cell = cellIterator.next();
                  question.setOption_crt(cell.getStringCellValue());
                  session.save(question);
                  transaction.commit();
          System.out.println(""); // To iterate over to the next row
          }
          File File= new File(path+"\\"+arr[0]);
          File.delete();
          response.sendRedirect("http://localhost:8050/Project/examples/examiner_dashboard.jsp");
	}

}
