package com.csumb.cst363;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * Controller class for patient interactions.
 *   register as a new patient.
 *   update patient profile.
 */
@Controller
public class ControllerPatient {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*
	 * Request blank patient registration form.
	 */
	@GetMapping("/patient/new")
	public String newPatient(Model model) {
		// return blank form for new patient registration
		model.addAttribute("patient", new Patient());
		return "patient_register";
	}
	
	//////////////////////////////////////
	  /*
    * Process doctor registration.
   
   @PostMapping("/doctor/register")
   public String createDoctor(Doctor doctor, Model model) {
      
      try (Connection con = getConnection();) {
         PreparedStatement ps = con.prepareStatement("insert into doctor(last_name, first_name, specialty, practice_since,  ssn ) values(?, ?, ?, ?, ?)", 
               Statement.RETURN_GENERATED_KEYS);
         ps.setString(1, doctor.getLast_name());
         ps.setString(2, doctor.getFirst_name());
         ps.setString(3, doctor.getSpecialty());
         ps.setString(4, doctor.getPractice_since_year());
         ps.setString(5, doctor.getSsn());
         
         ps.executeUpdate();
         ResultSet rs = ps.getGeneratedKeys();
         if (rs.next()) doctor.setId((int)rs.getLong(1));
      
         // display message and patient information
         model.addAttribute("message", "Registration successful.");
         model.addAttribute("doctor", doctor);
         return "doctor_show";
         
      } catch (SQLException e) {
         model.addAttribute("message", "SQL Error."+e.getMessage());
         model.addAttribute("doctor", doctor);
         return "doctor_register";  
      }
   } */
	
	
	  /*
    * Process new patient registration 
    * Maybe working 5/21 */
   @PostMapping("/patient/new")
   public String newPatient(Patient p, Model model) {

      try (Connection con = getConnection();) {    
      PreparedStatement ps = con.prepareStatement("insert into patient(ssn, fullname, address, age) values (?,?,?,?)",
            Statement.RETURN_GENERATED_KEYS);
      
      ps.setString(1, p.getPSSN());
      ps.setString(2, p.getPatientFullName());
      ps.setString(3, p.getPatientAddress());
      ps.setInt(4, p.getPatientAge());
      
      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next()) p.setPatientSSN((String)rs.getString(1));
      
      model.addAttribute("message", "Registration successful.");
      model.addAttribute("patient", p);
      return "patient_show";
      
      } catch (SQLException e) {
         model.addAttribute("message", "SQL Error."+e.getMessage());
         model.addAttribute("patient", p);
         return "patient_register";  
      }

   }
	
	/*
	 * Perform search for patient by patient id and name.
	 */

	////Kevin attempt
     @PostMapping("/patient/show")
      public String getPatientForm(Patient p, Model model) {
   
        try (Connection con = getConnection();) {
        
         PreparedStatement ps = con.prepareStatement("Select fullname, patientAddress, patientAge, patientPrimaryName from patient where patientSSN =?");
         ps.setString(1, p.getPSSN());
              
         ResultSet rs = ps.executeQuery();
         if (rs.next()) {
            p.setPatientFullName(rs.getString(1));
            p.setPatientAddress(rs.getString(2));
            p.setPatientAge(rs.getInt(3));
            p.setPatientPrimaryName(rs.getString(4));
         }
         model.addAttribute("patient", p);
         return "patient_show";
         
        } catch (SQLException e) {
            System.out.println("SQL error in getPatient "+e.getMessage());
            model.addAttribute("message", "SQL Error."+e.getMessage());
            model.addAttribute("patient", p);
            return "patient_get";
         }
      }
	     
	     
	/*  ////// ORIGINAL
	 *  Display patient profile for patient id.
	 */
	@GetMapping("/patient/edit/{patientId}")
	public String updatePatientOG(@PathVariable String patientId, Model model) {

		// TODO Complete database logic search for patient by id.

		// return fake data.
		Patient p = new Patient();
		p.setPatientId(patientId);
		p.setFirst_name("Alex");
		p.setLast_name("Patient");
		p.setBirthdate("2001-01-01");
		p.setStreet("123 Main");
		p.setCity("SunCity");
		p.setState("CA");
		p.setZipcode("99999");
		p.setPrimaryID(11111);
		p.setPrimaryName("Dr. Watson");
		p.setSpecialty("Family Medicine");
		p.setYears("1992");

		model.addAttribute("patient", p);
		return "patient_edit";
	}
	//// Kevin
	  @GetMapping("/patient/edit/{patientSSN}")
	   public String updatePatient(@PathVariable String patientSSN, Model model) {

	      Patient p = new Patient();
	      p.setPatientId(patientSSN);
	      
	      try (Connection con = getConnection();) {
         PreparedStatement ps = con.prepareStatement("Select fullname, patientAddress, patientAge, patientPrimaryName from patient where patientSSN =?");
         ps.setString(1, p.getPSSN());
         
         ResultSet rs = ps.executeQuery();
         if(rs.next()) {
            p.setPatientFullName(rs.getString(1));
            p.setPatientAddress(rs.getString(2));
            p.setPatientAge(rs.getInt(3));
            p.setPatientPrimaryName(rs.getString(4));
            
         model.addAttribute("patient", p);
	      return "patient_edit";
	      }
	      else {
            model.addAttribute("message", "Patient not found.");
            model.addAttribute("doctor", p);
            return "patient_get";
         }
	   
     } catch (SQLException e) {
        model.addAttribute("message", "SQL Error."+e.getMessage());
        model.addAttribute("doctor", p);
        return "patient_get";
     }
	      
     }
	
	/*
	 * Process changes to patient profile.  
	 */
	@PostMapping("/patient/edit")
	public String updatePatient(Patient p, Model model) {

	   try (Connection con = getConnection();) {

	   PreparedStatement ps = con.prepareStatement("update patient set fullname=?, patientAddress=?, patientAge=?, patientPrimaryName=? where patientSSN =?");
	   ps.setString(1, p.getPatientFullName());
	   ps.setString(2, p.getPatientAddress());
	   ps.setInt(3, p.getPatientAge());
	   ps.setString(4, p.getPatientPrimaryName());
	   
      int rc = ps.executeUpdate();
      if (rc==1) {
         model.addAttribute("message", "Update successful");
         model.addAttribute("doctor", p);
         return "patient_show";
         
      }else {
         model.addAttribute("message", "Error. Update was not successful");
         model.addAttribute("doctor", p);
         return "patient_edit";
      }
	}
	   
	   catch (SQLException e) {
         model.addAttribute("message", "SQL Error."+e.getMessage());
         model.addAttribute("doctor", p);
         return "patient_edit";
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
