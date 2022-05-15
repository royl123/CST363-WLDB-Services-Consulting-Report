package com.csumb.cst363;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller    
public class ControllerPrescriptionCreate {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*
	 * Doctor requests blank form for new prescription.
	 */
	@GetMapping("/prescription/new")
	public String newPrescripton(Model model) {
		model.addAttribute("prescription", new Prescription());
		return "prescription_create";
	}
	
	/* 
	 * Process the new prescription form.
	 * 1.  Validate that Doctor SSN exists and matches Doctor Name.
	 * 2.  Validate that Patient SSN exists and matches Patient Name.
	 * 3.  Validate that Drug name exists.
	 * 4.  Insert new prescription.
	 * 5.  If error, return error message and the prescription form
	 * 6.  Otherwise, return the prescription with the rxid number that was generated by the database.
	 */
	@PostMapping("/prescription/new")
	public String newPrescription( Prescription p, Model model) {
		
		try (Connection con = getConnection();) {
			PreparedStatement ps = con.prepareStatement("insert into prescription(RXnumber, patient_name, pharmacy_name, pharmacy_address) values(?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, p.getDoctor_ssn());
			ps.setString(2, p.getDoctorFirstName());
			ps.setString(3, p.getDoctorLastName());
			ps.setString(4, p.getPatient_ssn());
			ps.setString(5, p.getPatientFirstName());
			ps.setString(6, p.getPatientLastName());
			ps.setString(7, p.getDrugName());
			ps.setInt(8, p.getQuantity());

			// set fake data for auto-generated prescription id.
			p.setRxid("RX1980031234");
	
			model.addAttribute("message", "Prescription created.");
			model.addAttribute("prescription", p);
			return "prescription_show";
		
		} catch (SQLException e) {
			model.addAttribute("message", "SQL Error."+e.getMessage());
			model.addAttribute("prescription", p);
			return "prescription_new";	
		}

	}
	
	/*
	 * return JDBC Connection using jdbcTemplate in Spring Server
	 */

	private Connection getConnection() throws SQLException {
		Connection conn = jdbcTemplate.getDataSource().getConnection();
		return conn;
	}
	
}
