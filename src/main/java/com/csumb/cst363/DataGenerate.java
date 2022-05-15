package com.csumb.cst363;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import com.github.javafaker.*;

@SpringBootApplication
public class DataGenerate implements CommandLineRunner {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(Cst363Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//generate 10 random doctor insert
		for (int i = 0; i < 10; i++) {
			Random random = new Random(); 
			
			//generate random ssn
			String ssn = String.format("%09d", random.nextInt(1000000000));
//	 		System.out.println(ssn);
			
	 		//generate random doctor full name
			Faker faker = new Faker();
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String fullName = "Dr." + " "+ firstName + ' ' + lastName;
//			System.out.println(fullName);
			
	 		//generate random specialty
			String[] specialities = { "Internal Medicine", 
					"Family Medicine", "Pediatrics", "Orthopedics",
					"Dermatology",  "Cardiology", "Gynecology", 
					"Gastroenterology", "Psychiatry", "Oncology" };
			String specialty = specialities[random.nextInt(specialities.length)];	
//			System.out.println(specialty);
			
			//generate random years of experience 10-30 years
			int yrOfExp = ThreadLocalRandom.current().nextInt(10, 30 + 1);
//			System.out.println(yrOfExp);
//			System.out.println(" ");
			
			
			//Insert data into doctor column in db?
//			String sql = "INSERT INTO doctor(ssn, name, specialty, year_experience) VALUES (?,?,?,?)";
//			int result = jdbcTemplate.update(sql, ssn, fullName, specialty, yrOfExp);
			
		}
		
		
		// generate 100 random patient insert
		for (int i = 0; i < 100; i++) {
			Random random = new Random();
			//generate random ssn
			String ssn = String.format("%09d", random.nextInt(1000000000));
//	 		System.out.println(ssn);
			
			//generate random name
			Faker faker = new Faker();
			String name = faker.name().fullName();
//			System.out.println(name);
			
//			generate random address
			String streetAddress = faker.address().streetAddress();
			System.out.println(streetAddress);
			
			//generate random age
			int age = ThreadLocalRandom.current().nextInt(1, 100 + 1);
//			System.out.println(age);
//			System.out.println(" ");
			
			//Insert data into patient column in db?
//			String sql = "INSERT INTO patient(ssn, name, address, age) VALUES (?,?,?,?)";
//			int result = jdbcTemplate.update(sql, ssn, name, streetAddress, age);
			
		}
		
		
		//Generate 100 random prescriptions 
		for (int i = 0; i < 100; i++) {
			Random random = new Random();
			
			//generate random doctor ssn
			String doctorSSN = String.format("%09d", random.nextInt(1000000000));
//	 		System.out.println(doctorSSN);
			
	 		//generate random doctor full name
			Faker faker = new Faker();
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String doctorName = "Dr." + " "+ firstName + ' ' + lastName;
//			System.out.println(doctorName);
			
			
			//generate random patient ssn
			String patientSSN = String.format("%09d", random.nextInt(1000000000));
//	 		System.out.println(patientSSN);
			
			
			//generate random patient name
			String patientName = faker.name().fullName();
//			System.out.println(patientName);
			
			
			//generate random drug name
			String[] drugs = { "Tylenol with Codeine", 
					"Proair Proventil", "Accuneb", "Fosamax",
					"Zyloprim",  "Xanax", "Elavil", 
					"Augmentin", "Amoxil", "Adderall XR" };

			String drugName = drugs[random.nextInt(drugs.length)];	
//			System.out.println(drugName);
			
			//generate random quantity
			int quantity = ThreadLocalRandom.current().nextInt(1, 20 + 1);
//			System.out.println(quantity);
//			System.out.println(" ");
			
		}
	}
}
