package com.csumb.cst363;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//Author: Roy Luengas
@Controller   
public class ControllerPrescriptionFill {
	
	static final String DB_URL = "jdbc:mysql://localhost:3306/pharm";
    static final String USER = "root";
    static final String PASS = "password";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	/* 
	 * Patient requests form to search for prescription.
	 */
	@GetMapping("/prescription/fill")
	public String getfillForm(Model model) {
		model.addAttribute("prescription", new Prescription());
		return "prescription_fill";
	}


	/*
	 * Process the prescription fill request from a patient.
	 * 1.  Validate that Prescription p contains rxid, pharmacy name and pharmacy address
	 *     and uniquely identify a prescription and a pharmacy.
	 * 2.  update prescription with pharmacyid, name and address.
	 * 3.  update prescription with today's date.
	 * 4.  Display updated prescription 
	 * 5.  or if there is an error show the form with an error message.
	 */
	@PostMapping("/prescription/fill")
	public String processFillForm(Prescription p,  Model model) {


		try (Connection con = getConnection();) {
			PreparedStatement ps = con.prepareStatement("insert into fill(prescription_RXnumber, patient_last_name, pharmacy_name, pharmacy_address) values(?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, p.getRxid());
			ps.setString(2, p.getPatientLastName());
			ps.setString(3, p.getPharmacyName());
			ps.setString(4, p.getPharmacyAddress());
			
		     Random random = new Random();
		        String randnumb1 = String.format("%09d", random.nextInt(1000000000));
			
	        ZoneId zonedId = ZoneId.of( "America/Montreal" );
	        LocalDate date = LocalDate.now( zonedId );

			String sql = "INSERT INTO fill(prescription_RXnumber, patient_last_name, pharmacy_name, pharmacy_address, fill_no, date_filled) VALUES (?,?,?,?,?,?)";
			int result = jdbcTemplate.update(sql, p.getRxid(), p.getPatientLastName(), p.getPharmacyName(),  p.getPharmacyAddress(), randnumb1, date);

			
			
			String pharm_id ="";
			String cost="";
			String phone="";
			String pfn="";
			String pssn="";
			String tradeName="";
			String dssn="";
			String dfn="";
			String dln="";
			try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			         Statement stmt = conn.createStatement();
			         ResultSet rs = stmt.executeQuery("select distinct phone_number, price, patient_first_name, patient_ssn, trade_name,doctor_dSSN, doc_first_name, doc_last_name FROM prescription join sells join pharmacy where address="
			         		+ "'"+p.getPharmacyAddress()+"'"+" AND pharmacy_name="+"'"+p.getPharmacyName()+"'"+" AND patient_last_name="+"'"+p.getPatientLastName()+"'"+" AND RXnumber="+"'"+p.getRxid()+"'");
			      ) {		      
			         while(rs.next()){
			        	 pharm_id = rs.getString("pharma_id");
			        	 cost = rs.getString("price");
			        	 phone = rs.getString("phone_number");
			        	 pfn=rs.getString("patient_first_name");
			        	 pssn=rs.getString("patient_ssn"); 
			        	 tradeName=rs.getString("trade_name");
			        	 dssn=rs.getString("doctor_dSSN");
			        	 dfn=rs.getString("doc_first_name");
			        	 dln=rs.getString("doc_last_name");
			         }
			      } catch (SQLException e) {
			         e.printStackTrace();
			      } 
			
			
			p.setPharmacyID(pharm_id);
			p.setCost(cost);
			p.setPharmacyPhone(phone);
			p.setPatientFirstName(pfn);
			p.setPatient_ssn(pssn);
			p.setDrugName(tradeName);
			p.setDoctor_ssn(dssn);
			p.setDoctorFirstName(dfn);
			p.setDoctorLastName(dln);
			p.setDateFilled( new java.util.Date().toString());

			// display the updated prescription
			model.addAttribute("message", "Prescription has been filled.");
			model.addAttribute("prescription", p);
			return "prescription_show";
		
		} catch (SQLException e) {
			model.addAttribute("message", "SQL Error."+e.getMessage());
			model.addAttribute("prescription", p);
			return "prescription_fill";	
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