package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem 
{
    private static final String  url = "jdbc:mysql://localhost:3306/hospital";
    
    private static final String userName = "root";
    
    private static final String passWord = "Adnan@202";
    
    
    public static void main(String[] args) 
    {
    	Scanner sc = new Scanner(System.in);
	    try
	    {
	    	Class.forName("com.mysql.cj.jdbc.Driver");
	    }
	    catch(ClassNotFoundException e)
	    {
	    	e.getMessage();
	    }
	    
	    try
	    {
	    	Connection con = DriverManager.getConnection(url,userName,passWord);
	    	Patients p = new Patients(con,sc);
	    	Doctor   d = new Doctor(con);
	    	
	    	
	    	while(true)
	    	{
	    		System.out.println("\n               ***********WELCOME TO THE SHIFA HOSPITAL SHEGAON*************");
	    		System.out.println();
	    		System.out.println();
	    		System.out.println("               PRESS 1 TO PATIENT REGISTRATION.");
	    		System.out.println("               PRESS 2 TO VIEW PATIENTS INFORMATION.");
	    		System.out.println("               PRESS 3 TO VIEW DOCTORS INFORMATION.");
	    		System.out.println("               PRESS 4 TO BOOK APPOINMENT.");
	    		System.out.println("               PRESS 0 TO EXIST.");
	    		
	    		System.out.print("\nEnter Your Choice: ");
	    		int choice = sc.nextInt();
	    		
	    		switch(choice)
	    		{
	    		   case 1:
	    		   {
	    			   p.addPatients();
	    			   break;
	    		   }
	    		   case 2:
	    		   {
	    			   p.viewPatients();
	    			   break;
	    		   }
	    		   case 3:
	    		   {
	    			   d.viewDoctors();
	    			   break;
	    		   }
	    		   case 4:
	    		   {
	    			   bookAppointment(p, d, con, sc);
	    			   break;
	    		   }
	    		   case 5:
	    		   {
	    			   return;
	    		   }
	    		   default:
	    		   {
	    			   System.err.println("Please Enter Valid Choice.");
	    			   break;
	    		   }
	    		}
	    	}
	    }
	    catch(SQLException e)
	    {
	    	e.getMessage();
	    }
	}
    
    public static void bookAppointment(Patients p, Doctor d, Connection con, Scanner sc)
    {
    	System.out.print("Enter Patient Id: ");
    	int patientId = sc.nextInt();
    	System.out.print("Enter Doctor Id: ");
    	int doctorId = sc.nextInt();
    	System.out.print("Enter Appointment Date(YYYY-MM-DD): ");
    	String appointmentDate = sc.nextLine();
    	appointmentDate = sc.nextLine();
    	
    	if(p.getpatientsId(patientId) && d.getDoctorsId(doctorId))
    	{
    		if(checkDoctorAvalability(doctorId, appointmentDate, con))
    		{
    			String appointmentQuery = "INSERT INTO APPOINTMENTS(PATIENTS_ID , DOCTORS_ID, APPOINTMENT_DATE) VALUES(?,?,?)";
    			
    			try
    			{
    				PreparedStatement ps = con.prepareStatement(appointmentQuery);
    				
    				ps.setInt(1, patientId);
    				ps.setInt(2, doctorId);
    				ps.setString(3, appointmentDate);
    				
    				int affectRows = ps.executeUpdate();
    				
    				if(affectRows>0)
    				{
    					System.out.println("Appointment Successfully Booked...");
    				}
    				else
    				{
    					System.out.println("Failed To Booked Appointment..");
    				}
    			}
    			catch(SQLException e)
    			{
    				e.getMessage();
    			}
    		}
    		else
    		{
    			System.err.println("Doctor Not Available On This Date.");
    		}
    	}
    	else
    	{
    		System.err.println("Either Doctor & Patient is Doesn'n Exist.");
    	}
    }
    
    public static boolean checkDoctorAvalability(int doctorId, String appointmentDate, Connection con)
    {
    	String checkDoctorAvalabilityQuery = "SELECT COUNT(*) FORM APPOINTMENTS WHERE DOCTORS_ID = ? AND APPOINTMENT_DATE = ?";
    	
    	try
    	{
    		PreparedStatement ps = con.prepareStatement(checkDoctorAvalabilityQuery);
    		
    		ps.setInt(1, doctorId);
    		ps.setString(2, appointmentDate);
    		
    		ResultSet rs = ps.executeQuery();
    		
    		if(rs.next())
    		{
    			int count = rs.getInt(1);
    			
    			if(count == 0)
    			{
    				return true;
    			}
    			else
    			{
    				return false;
    			}
    		}
    	}
    	catch(SQLException e)
    	{
    		e.getMessage();
    	}
    	return false;
    }
}









