package com.csumb.cst363;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

//Author: Roy Luengas

//Test by running DataGenerate file ONCE
public class ManagerReport {
   static final String DB_URL = "jdbc:mysql://localhost:3306/pharm";
   static final String USER = "root";
   static final String PASS = "password";

   public static void main(String[] args) {
	   
	   String pharmaIDTest ="";
		//generate doctor_dssn from previous run to handle constraints
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("Select * FROM prescription LIMIT 1");
		      ) {		      
		         while(rs.next()){
		        	 pharmaIDTest = rs.getString("pharma_id");
		         }
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } 
	   
	   Scanner pharmIDIn = new Scanner(System.in);
	   System.out.print("Enter pharmacy id(test with "+pharmaIDTest+"):");
	   String pharmaID = pharmIDIn.nextLine();
	   
	   Scanner sd = new Scanner(System.in);
	   System.out.print("Enter start date(test with 2021-01-01):");
	   String startDate = sd.nextLine();
	   
	   Scanner ed = new Scanner(System.in);
	   System.out.print("Enter end date(test with 2021-12-31):");
	   String endDate = ed.nextLine();
	   
	   System.out.println("");
	   System.out.println("******** Manager Report *******");
	   System.out.println("");
	   
	   String QUERY = "SELECT * from prescription where(date_prescribed BETWEEN "+ "'"+startDate+"'"+" AND " + "'"+endDate+"') AND pharma_id="+"'"+pharmaID+"'";
      try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(QUERY);
      ) {		      
         while(rs.next()){
            System.out.println("RXnumber: " + rs.getInt("RXnumber"));
            System.out.println("phy_ssn: " + rs.getInt("phy_ssn"));
            System.out.println("date_prescribed: " + rs.getString("date_prescribed"));
            System.out.println("quantity: " + rs.getString("quantity"));
            System.out.println("trade_name: " + rs.getString("trade_name"));
            System.out.println("generic_name: " + rs.getString("generic_name"));
            System.out.println("pharma_id: " + rs.getString("pharma_id"));
            System.out.println("drug_trade_name: " + rs.getString("drug_trade_name"));
            System.out.println("patient_ssn: " + rs.getString("patient_ssn"));
            System.out.println("doctor_dSSN: " + rs.getString("doctor_dSSN"));
            System.out.println("");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } 
   }
}