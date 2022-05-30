package com.csumb.cst363;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import com.github.javafaker.*;

// Author: Roy Luengas
//UNCOMMENT INSERTS BEFORE RUNNING lines 82,138,& 218
//COMMENT OUT BACK AGAIN AFTER RUNNING FILE ONCE


@SpringBootApplication
public class DataGenerate implements CommandLineRunner {
	static final String DB_URL = "jdbc:mysql://localhost:3306/pharm";
    static final String USER = "root";
    static final String PASS = "password";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(Cst363Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//generate 10 random doctors
		System.out.println("*********Generate 10 random doctors*********");
		for (int i = 0; i < 10; i++) {
			Random random = new Random(); 
			
			//generate random ssn
			String ssn = String.format("%09d", random.nextInt(1000000000));
	 		System.out.println(ssn);
			
	 		//generate random doctor full name
			Faker faker = new Faker();
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String fullName = firstName + ' ' + lastName;
			System.out.println(fullName);
			
	 		//generate random specialty
			String[] specialities = { "Internal Medicine", 
					"Family Medicine", "Pediatrics", "Orthopedics",
					"Dermatology",  "Cardiology", "Gynecology", 
					"Gastroenterology", "Psychiatry", "Oncology" };
			String specialty = specialities[random.nextInt(specialities.length)];	
			System.out.println(specialty);
			
			//generate random years of experience 10-30 years
			int yrOfExp = ThreadLocalRandom.current().nextInt(10, 30 + 1);
			System.out.println(yrOfExp);
			System.out.println(" ");
			
			//generate random phone number 
			String phoneNumber = String.format("%10d", random.nextInt(1000000000));
			
			//generate pharmacy
			String pharmName=lastName+" Pharma";
			
			//generate random address
			String streetAddress = faker.address().streetAddress();
			System.out.println(streetAddress);
			
//----------------------------UNCOMMENT BELOW------------------------------
			
			//Handle pharmacy constraint
//			String sqlPharm = "INSERT INTO pharmacy(name, address, phone_number ) VALUES (?,?,?)";
//			int resultPharm = jdbcTemplate.update(sqlPharm, pharmName, streetAddress, phoneNumber);
			
			
			//Insert data into doctor column in db
//			String sql = "INSERT INTO doctor(dSSN, name, specialty, years_experience) VALUES (?,?,?,?)";
//			int result = jdbcTemplate.update(sql, ssn, fullName, specialty, yrOfExp);
		
		}
		
		
		// generate 100 random patients
		System.out.println("*********Generate 100 random patients*********");
		for (int i = 0; i < 100; i++) {
			Random random = new Random();
			//generate random ssn
			String ssn = String.format("%09d", random.nextInt(1000000000));
	 		System.out.println(ssn);
			
			//generate random name
			Faker faker = new Faker();
			String firstName = faker.name().firstName();
			
			String lastName = faker.name().lastName();
			
			//generate random address
			String streetAddress = faker.address().streetAddress();
			System.out.println(streetAddress);
			
			//generate random age
			int age = ThreadLocalRandom.current().nextInt(1, 100 + 1);
			System.out.println(age);
			System.out.println(" ");
			
			String dSSN ="";
			//generate doctor_dssn from previous run to handle constraints
			try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			         Statement stmt = conn.createStatement();
			         ResultSet rs = stmt.executeQuery("Select * FROM doctor LIMIT 1");
			      ) {		      
			         while(rs.next()){
			        	 dSSN = rs.getString("dSSN");
			         }
			      } catch (SQLException e) {
			         e.printStackTrace();
			      } 
	  
			
			
	 		//generate random primary_physician_dssn
			String pri_dssn = String.format("%09d", random.nextInt(1000000000));
	 		System.out.println(pri_dssn);

//----------------------------UNCOMMENT BELOW------------------------------
			//Insert data into patient column in db
//			String sql = "INSERT INTO patient(ssn, name, address, age, doctor_dSSN, primary_physician_dssn) VALUES (?,?,?,?,?,?)";
//			int result = jdbcTemplate.update(sql, ssn, firstName, streetAddress, age, dSSN, pri_dssn);
		
		}
		
		
		//Generate 100 random prescriptions
		System.out.println("*********Generate 100 random prescriptions*********");
		for (int i = 1; i < 101; i++) {
			Random random = new Random();
			
			//generate random RX number
			String rx = String.format("%012d", random.nextInt(1000000000));
	 		System.out.println(rx);
	 		
	 		//generate random phy_ssn
			String phySSN = String.format("%09d", random.nextInt(1000000000));
	 		System.out.println(phySSN);
	 		
	 		//generate random date_prescribed
	 		Year y = Year.of(2021);
	 		LocalDate ld = y.atDay( ThreadLocalRandom.current().nextInt( 1 , y.length() ) );
	 		System.out.println(ld);
	 		
	 		//generate random quantity
			int quantity = ThreadLocalRandom.current().nextInt(1, 20 + 1);
			System.out.println(quantity);
			
			//generate trade and generic names
			String tradeName = "tradeName"+i;
			String genName = "GenericName"+i;
			System.out.println(tradeName);
			System.out.println(genName);
	 		
	 		//generate random pharma_id number
			String pharmaID = String.format("%09d", random.nextInt(1000000000));
	 		System.out.println(pharmaID);
			
	 		//generate random patient name
	 		Faker faker = new Faker();
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String fullName = firstName + ' ' + lastName;
			System.out.println(fullName);
			
			String firstName2 = faker.name().firstName();
			String lastName2 = faker.name().lastName();
			
			
			//generate random patient ssn from previous run to deal with constraints
			String pSSN ="";
			try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			         Statement stmt = conn.createStatement();
			         ResultSet rs = stmt.executeQuery("Select * FROM patient LIMIT 1");
			      ) {		      
			         while(rs.next()){
			        	 pSSN = rs.getString("ssn");
			         }
			      } catch (SQLException e) {
			         e.printStackTrace();
			      } 
			
	 		
			String dSSN ="";
			//generate doctor_dssn from previous run to handle constraints
			try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			         Statement stmt = conn.createStatement();
			         ResultSet rs = stmt.executeQuery("Select * FROM doctor LIMIT 1");
			      ) {		      
			         while(rs.next()){
			        	 dSSN = rs.getString("dSSN");
			         }
			      } catch (SQLException e) {
			         e.printStackTrace();
			      } 
			System.out.println("");
			
			
//----------------------------UNCOMMENT BELOW------------------------------
			
			//Handle drug table constraint
//			String sqlDrug= "INSERT INTO drug(trade_name, generic_name) VALUES (?,?)";
//			int resultDrug = jdbcTemplate.update(sqlDrug, tradeName, genName);
			
			
//			String sql = "INSERT INTO prescription(RXnumber, phy_ssn, date_prescribed, doctor_dSSN, "
//					+ "trade_name, generic_name, pharma_id,"
//					+ "doc_first_name, doc_last_name, patient_ssn, patient_first_name, patient_last_name,"
//					+ " drug_trade_name, quantity) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//			int result = jdbcTemplate.update(sql, rx, dSSN, ld, dSSN, tradeName, genName, dSSN, firstName, lastName, pSSN, firstName2, lastName2, tradeName, quantity);
			
		}
	}
}
