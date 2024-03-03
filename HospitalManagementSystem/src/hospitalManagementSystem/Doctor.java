package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor 
{
	    private Connection connection;
	    
		public Doctor(Connection connection) 
		{
			super();
			this.connection = connection;
		}
		
		
		
		public void viewDoctors()
		{
			String selectQuery = "SELECT * FROM DOCTORS";
			
			try
			{
				PreparedStatement ps = connection.prepareStatement(selectQuery);
				ResultSet rs = ps.executeQuery();
				
				System.out.println("Doctors Data: ");
				System.out.println("+------------------+------------------------------------------+--------------------------------+");
				System.out.println("| DOCTOR_ID        | DOCTOR NAME                              | SPECIALIZATION                 |");
				System.out.println("+------------------+------------------------------------------+--------------------------------+");
				
				while(rs.next())
				{
					int id = rs.getInt("ID");
					String name = rs.getString("NAME");
					String spl = rs.getString("SPECIALIZATION");
					System.out.printf("| %-16s | %-40s | %-30s |\n", id, name, spl);

					System.out.println("+------------------+------------------------------------------+--------------------------------+");
					
				}
			}
			catch(SQLException e)
			{
				e.getMessage();
			}
			
		}
		
		public boolean getDoctorsId(int id)
		{
			String idRetriveQuery = "SELECT * FROM DOCTORS WHERE ID = ?";
			
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
