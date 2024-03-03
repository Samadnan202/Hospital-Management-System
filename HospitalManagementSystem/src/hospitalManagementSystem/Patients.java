package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patients 
{
    private Connection connection;
    private Scanner sc;
    
	public Patients(Connection connection, Scanner sc) 
	{
		super();
		this.connection = connection;
		this.sc = sc;
	}
	
	public void addPatients()
	{
		System.out.print("Enter Patient Name: ");
		String name = sc.nextLine();
		name = sc.nextLine();
		
		System.out.print("Enter Patient Age: ");
		double age = sc.nextDouble();
		
		System.out.print("Enter Patient Gender: ");
		String gender = sc.nextLine();
		gender = sc.nextLine();
		
		
		try
		{
			String insertQueary = "INSERT INTO PATIENTS(NAME,AGE,GENDER) VALUES(?,?,?)";
			PreparedStatement ps = connection.prepareStatement(insertQueary);
			ps.setString(1, name);
			ps.setDouble(2, age);
			ps.setString(3, gender);
			
			int affectRows = ps.executeUpdate();
			
			if(affectRows>0)
			{
				System.out.println("Patients Registration Is Successfull...");
			}
			else
			{
				System.err.println("Failed To Registration...");
			}
		}
		catch(SQLException e)
		{
			e.getMessage();
		}
	}
	
	public void viewPatients()
	{
		String selectQuery = "SELECT * FROM PATIENTS";
		
		try
		{
			PreparedStatement ps = connection.prepareStatement(selectQuery);
			ResultSet rs = ps.executeQuery();
			
			System.out.println("Patients Data: ");
			System.out.println("+-----------+---------------------------+------------------+----------------------+");
			System.out.println("| ID        | NAME                      | AGE              | GENDER               |");
			System.out.println("+-----------+---------------------------+------------------+----------------------+");
			
			while(rs.next())
			{
				int id = rs.getInt("ID");
				String name = rs.getString("NAME");
				double age = rs.getDouble("AGE");
				String gender = rs.getString("GENDER");
				
				System.out.printf("| %-9s | %-25s | %-16s | %-20s |\n", id, name, age, gender);

				System.out.println("+-----------+---------------------------+------------------+----------------------+");
				
			}
		}
		catch(SQLException e)
		{
			e.getMessage();
		}
		
	}
	
	public boolean getpatientsId(int id)
	{
		String idRetriveQuery = "SELECT * FROM PATIENTS WHERE ID = ?";
		
		try
		{
			PreparedStatement ps = connection.prepareStatement(idRetriveQuery);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				return true;
			}
			else
			{
			    return false;
			}   
		}
		catch(SQLException e)
		{
			e.getMessage();
		}
		return false;
		
		
	}
    
    
}















