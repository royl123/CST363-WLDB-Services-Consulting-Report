package com.csumb.cst363;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

//Author: Roy Luengas

//Test by running DataGenerate file once
public class ManagerReport {
   static final String DB_URL = "jdbc:mysql://localhost:3306/pharm";
   static final String USER = "root";
   static final String PASS = "password";

   public static void main(String[] args) {
	   
	   Scanner pharmIDIn = new Scanner(System.in);
	   System.out.print("Enter pharmacy id:");
	   String pharmaID = pharmIDIn.nextLine();
	   
	   Scanner sd = new Scanner(System.in);
	   System.out.print("Enter start date:");
	   String startDate = sd.nextLine();
	   
	   Scanner ed = new Scanner(System.in);
	   System.out.print("Enter end date:");
	   String endDate = ed.nextLine();
	   
	   String QUERY = "SELECT * from prescription where(date_prescribed BETWEEN "+ "'"+startDate+"'"+" AND " + "'"+endDate+"') AND pharma_id="+"'"+pharmaID+"'";
      try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(QUERY);
      ) {		      
         while(rs.next()){
        	System.out.println("");
        	System.out.println("******** Manager Report *******");
        	System.out.println("");
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
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } 
   }
}