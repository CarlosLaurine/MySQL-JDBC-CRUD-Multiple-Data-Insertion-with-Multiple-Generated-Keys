package application;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class MainProgram {

	public static void main(String[] args) {
		
		//Creating SimpleDateFormat object to stance data at PreparedStatement object
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		//Initiating null variables with the JDBC Class-Types needed for selection
		Connection con = null;
		
		/*Using Method PreparedStatement to set query commands with parameters
		that will be fulfilled later*/	
		PreparedStatement pst = null;
		
		
		//Using try catch body to treat SQL exceptions
		try {
			
		//Opening db connection	
		con = DB.getConnection();
		/*Setting PreparedStatement Object pst with Insertion command 
		  with harcode-passed parameters "('D5'),('D6')"*/
		
		/*Also overriding prepareStatement method's contructor to include
		a new parameter - the Statement.RETURN_GENERATED_KEYS*/
		
		//============================ TESTING THE INSERTION OF MULTIPLE VALUES AT A DEFINED TABLE============================//
	
		//OBS1: No case sensitivity at the command string
		
		pst = con.prepareStatement("insert into department (Name) values ('D5'),('D6')", Statement.RETURN_GENERATED_KEYS);
			
		/*Executing command with all updates with an integer of net number of 
	      lines as return, and saving this information in an int variable 
	      rowsChanged
	     */

		int rowsChanged = pst.executeUpdate();
		
		
		//============================ TESTING THE PRINTING AND MANIPULATION OF MULTIPLE VALUES AT THE RESULT SET============================//

	
		if(rowsChanged>0) {
			/*Getting all generated keys from Statement object and storing 
			  them at a Result Set object*/
			ResultSet rs = pst.getGeneratedKeys();
			//Going through ResultSet generated table to get the Keys
			
			/* The possibility of multiple values storage by the ResultSet makes appropriate the
			   preventive technique of always using a while (rs.next) loop to go through the rs 
			   object in case of future query command changes at the code
			*/
			
			while(rs.next()) {
				int id = rs.getInt(1);
				System.out.println("Done, ID = " + id + "!");
			}
		}
		else {
			System.out.println("No Rows were Changed!");
		}
		
		
		}//Handling specific exceptions
		catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		}
		
		
		//Using finally block to ensure all external resources to JVM will be closed
		finally {
			
			DB.closeConnection();
			
			//Upcast in closeStatement as PreparedStatement pst is passed as a Statement parameter
			
			DB.closeStatement(pst);
			
			
		}


}}