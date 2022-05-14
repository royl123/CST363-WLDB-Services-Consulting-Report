package com.csumb.cst363;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Cst363Application implements CommandLineRunner {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(Cst363Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String sql = "INSERT INTO doctor(ssn, name, specialty, year_experience) VALUES (?,?,?,?)";
		int result = jdbcTemplate.update(sql, "987654321", "Robert Jackson", "cardiology", "20");
		
		if(result > 0) {
			System.out.println("new row inserted");
		}
	}

}
