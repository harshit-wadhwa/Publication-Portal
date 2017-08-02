package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.user.ConnectionFactory;
import com.user.userObjs;

/**
 * Servlet implementation class pcn
 */
@WebServlet("/pcn")
public class pcn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public pcn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	    String pcn=null;
		String dept=request.getParameter("dept");
		String year=request.getParameter("year");
		//String name=request.getParameter("authors_name");
		String id1=request.getParameter("id");
		int id=Integer.valueOf(id1);
		int val=1000+id;
		String type=request.getParameter("type");
		
		Calendar now = Calendar.getInstance();
		int current = now.get(Calendar.MONTH) + 1;
		String cur_month = null;
		
//		try{
//			connection=ConnectionFactory.getConnection();
//			PreparedStatement ps=connection.prepareStatement("sel set pcn = ?,status=?,pcn_month=? where id = ?");
//			ps.setString(1, pcn);
//			ps.setString(2, "accepted");
//			ps.setString(3, cur_month);
//			ps.setInt(4, id);
//			ps.executeUpdate();
//
//			response.sendRedirect("Journal/Journal.jsp");
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		

		switch (current) {
		case 1:
			cur_month = "JANUARY";
			break;
		case 2:
			cur_month = "FEBRUARY";
			break;
		case 3:
			cur_month = "MARCH";
			break;
		case 4:
			cur_month = "APRIL";
			break;
		case 5:
			cur_month = "MAY";
			break;
		case 6:
			cur_month = "JUNE";
			break;
		case 7:
			cur_month = "JULY";
			break;
		case 8:
			cur_month = "AUGUST";
			break;
		case 9:
			cur_month = "SEPTEMBER";
			break;
		case 10:
			cur_month = "OCTOBER";
			break;
		case 11:
			cur_month = "NOVEMBER";
			break;
		case 12:
			cur_month = "DECEMBER";
			break;
        default:
			System.out.println("failed");
		}
		
		
		//PrintWriter out = response.getWriter();
		//out.println(dept);
		//out.println(year);
		
		if(dept.equals("CSE")){
			dept="CSU";
		}
		else if(dept.equals("MED")){
			dept="MEU";
		}
		else if(dept.equals("ECE")){
			dept="ECU";
		}
		else if(dept.equals("CEE")){
			dept="CEU";
		}
		else if(dept.equals("APS")){
			dept="APS";
		}
		else if(dept.equals("SOM")){
			dept="SOM";
		}
		else if(dept.equals("SOL")){
			dept="SOL";
		}
		else if(dept.equals("CLL")){
			dept="CLL";
		}
		
		int y=Integer.parseInt(year);
		//out.println(y);
		
		if(y>=2000){
			y-=2000;
		}
		else{
			y-=1900;
		}
		
		year=String.valueOf(y);
		
		String value=String.valueOf(val);
		
		if(type.equals("journal")){
			pcn=year+dept+'J'+value;
		}
		else if(type.equals("books")){
			pcn=year+dept+'B'+value;
		}
		else if(type.equals("book_chapter")){
			pcn=year+dept+'O'+value;
		}
		else if(type.equals("conf_proceedings")){
			pcn=year+dept+'P'+value;
		}
		else if(type.equals("conf_presentations")){
			pcn=year+dept+'C'+value;
		}
		else if(type.equals("patents")){
			pcn=year+dept+'T'+value;
		}
		else if(type.equals("tech_report")){
			pcn=year+dept+'R'+value;
		}
		
		
		
	//	String uniqueID = UUID.randomUUID().toString();
		
	//  println(uniqueID);
		
		try{
			
			PreparedStatement  ps=null,ps2=null;
			//Class.forName("com.mysql.jdbc.Driver");                                       
			//Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/publication", "root", "root");
		
			if(type.equals("journal")){
				ps=connection.prepareStatement("update journal set pcn = ?,status=?,pcn_month=? where id = ?");
				ps.setString(1, pcn);
				ps.setString(2, "accepted");
				ps.setString(3, cur_month);
				ps.setInt(4, id);
				
				String name=userObjs.getJUser(id);
				ps2 = connection.prepareStatement("insert into notify(notification,user_id) values(?,?)");
	            ps2.setString(1, "PCN no. : "+pcn+" generated by admin for your Journal publication!");
	            ps2.setString(2, name);
			}
			else if(type.equals("books")){
				ps=connection.prepareStatement("update books set pcn = ?,status=?,month_pcn=? where id = ?");
				ps.setString(1, pcn);
				ps.setString(2, "accepted");
				ps.setString(3, cur_month);
				ps.setInt(4, id);
				
				String name=userObjs.getBUser(id);
				ps2 = connection.prepareStatement("insert into notify(notification,user_id) values(?,?)");
	            ps2.setString(1, "PCN no. : "+pcn+" generated by admin for your Book publication!");
	            ps2.setString(2, name);
			}
			else if(type.equals("book_chapter")){
				ps=connection.prepareStatement("update book_chap set pcn = ?,status=?,month_pcn=? where id = ?");
				ps.setString(1, pcn);
				ps.setString(2, "accepted");
				ps.setString(3, cur_month);
				ps.setInt(4, id);
				
				String name=userObjs.getBCUser(id);
				ps2 = connection.prepareStatement("insert into notify(notification,user_id) values(?,?)");
	            ps2.setString(1, "PCN no. : "+pcn+" generated by admin for your Book Chapter publication!");
	            ps2.setString(2, name);
			}
			else if(type.equals("conf_proceedings")){
				ps=connection.prepareStatement("update conf_proceedings set pcn = ?,status=?,month_pcn=? where id = ?");
				ps.setString(1, pcn);
				ps.setString(2, "accepted");
				ps.setString(3, cur_month);
				ps.setInt(4, id);
				
				String name=userObjs.getCPOUser(id);
				ps2 = connection.prepareStatement("insert into notify(notification,user_id) values(?,?)");
	            ps2.setString(1, "PCN no. : "+pcn+" generated by admin for your Conference Proceedings!");
	            ps2.setString(2, name);
			}
			else if(type.equals("conf_presentations")){
				ps=connection.prepareStatement("update conf_presentations set pcn = ?,status=?,month_pcn=? where id = ?");
				ps.setString(1, pcn);
				ps.setString(2, "accepted");
				ps.setString(3, cur_month);
				ps.setInt(4, id);
				
				String name=userObjs.getCPEUser(id);
				ps2 = connection.prepareStatement("insert into notify(notification,user_id) values(?,?)");
	            ps2.setString(1, "PCN no. : "+pcn+" generated by admin for your Conference Presentations!");
	            ps2.setString(2, name);
			}
			else if(type.equals("patents")){
				ps=connection.prepareStatement("update patents set pcn = ?,status=? where id = ?");
				ps.setString(1, pcn);
				ps.setString(2, "accepted");
				ps.setInt(3, id);
				
				String name=userObjs.getPUser(id);
				ps2 = connection.prepareStatement("insert into notify(notification,user_id) values(?,?)");
	            ps2.setString(1, "PCN no. : "+pcn+" generated by admin for your Patent publication!");
	            ps2.setString(2, name);
			}
			else if(type.equals("tech_report")){
				ps=connection.prepareStatement("update tech_report set pcn = ?,status=? where id = ?");
				ps.setString(1, pcn);
				ps.setString(2, "accepted");
				ps.setInt(3, id);
				
				String name=userObjs.getTRUser(id);
				ps2 = connection.prepareStatement("insert into notify(notification,user_id) values(?,?)");
	            ps2.setString(1, "PCN no. : "+pcn+" generated by admin for your Technical Report publication!");
	            ps2.setString(2, name);
			}
			
			if(ps.executeUpdate()>0){
				ps2.executeUpdate();
			}
			

			if(type.equals("journal")){
				response.sendRedirect("Journal/Journal.jsp");

			}
			else if(type.equals("books")){
				response.sendRedirect("Books/Books.jsp");
			}
			else if(type.equals("book_chapter")){
				response.sendRedirect("Book Chapter/Book_Chapter.jsp");
			}
			else if(type.equals("conf_proceedings")){
				response.sendRedirect("Conf. Proceedings/Conf_Proceedings.jsp");
			}
			else if(type.equals("conf_presentations")){
				response.sendRedirect("Conf. Presentations/Conf_Presentations.jsp");
			}
			else if(type.equals("patents")){
				response.sendRedirect("Patents/Patents.jsp");
			}
			else if(type.equals("tech_report")){
				response.sendRedirect("Tech reports/Tech_Report.jsp");
			}
								
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}

}
