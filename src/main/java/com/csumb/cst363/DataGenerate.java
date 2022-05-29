package com.csumb.cst363;
import java.time.LocalDate;
import java.time.Year;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import com.github.javafaker.*;

// Author: Roy Luengas
@SpringBootApplication
public class DataGenerate implements CommandLineRunner {
	
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
			
			//Insert data into doctor column in db
			String sql = "INSERT INTO doctor(dSSN, name, specialty, years_experience) VALUES (?,?,?,?)";
			int result = jdbcTemplate.update(sql, ssn, fullName, specialty, yrOfExp);
			
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
			String fullName = firstName + ' ' + lastName;
			System.out.println(fullName);
			
//			generate random address
			String streetAddress = faker.address().streetAddress();
			System.out.println(streetAddress);
			
			//generate random age
			int age = ThreadLocalRandom.current().nextInt(1, 100 + 1);
			System.out.println(age);
			System.out.println(" ");
			
			//generate doctor_dssn from previous run 10 doctors to deal with constraints
			int[] docSSN = new int[]{ 
					265372243,
					334936971,
					386079941,
					404311669,
					442939902,
					489978033,
					534954693,
					574296767,
					661499449,
					731593908
			};
			Integer dSSN = docSSN[random.nextInt(docSSN.length)];	
			
	 		//generate random primary_physician_dssn
			String pri_dssn = String.format("%09d", random.nextInt(1000000000));
	 		System.out.println(pri_dssn);
			
			//Insert data into patient column in db
			String sql = "INSERT INTO patient(ssn, name, address, age, doctor_dSSN, primary_physician_dssn) VALUES (?,?,?,?,?,?)";
			int result = jdbcTemplate.update(sql, ssn, fullName, streetAddress, age, dSSN, pri_dssn);
		
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
			
			//generate random patient ssn from previous run to deal with constraints
			int[] patientSSN = new int[]{ 
					105075302,
					112794856, 
					115795434, 
					118544507, 
					148056374, 
					153685346, 
					154629999, 
					159872496, 
					163777621, 
					179017188,
					196826050,
			};
			Integer pSSN = patientSSN[random.nextInt(patientSSN.length)];
			System.out.println(pSSN);
			
	 		
	 		//generate random doctor_dssn from previous run 10 doctors  to deal with constraints
			int[] docSSN = new int[]{ 
					265372243,
					334936971,
					386079941,
					404311669,
					442939902,
					489978033,
					534954693,
					574296767,
					661499449,
					731593908
			};
			Integer dSSN = docSSN[random.nextInt(docSSN.length)];
			System.out.println(dSSN);
			System.out.println("");
			
			
	 		//Assuming drug table already has values i.e. tradeName1..100, insert into prescription table
			String sql = "INSERT INTO prescription(RXnumber, phy_ssn, date_prescribed, quantity, trade_name, generic_name, pharma_id, patient_name, drug_trade_name, patient_ssn, doctor_dSSN) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
			int result = jdbcTemplate.update(sql, rx, phySSN, ld, quantity, tradeName,genName, pharmaID, fullName, tradeName, pSSN, dSSN);
			
		}
	}
}
