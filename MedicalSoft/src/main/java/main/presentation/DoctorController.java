package main.presentation;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import main.entities.Consultation;
import main.entities.Doctor;
import main.entities.Patient;
import main.service.ConsultationService;
import main.service.DoctorService;
import main.service.PatientService;

@Controller
public class DoctorController {
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private DoctorService doctorService;
	
	
	@Autowired
	private ConsultationService consultationService;
	
	private Patient patient=new Patient();
	private Doctor doctor;
	
	private Date sqlDate;
	
	@RequestMapping(value = "/doctor", method = RequestMethod.GET)
    public String editConsultation(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    doctor=doctorService.findByUsername(name);
       	model.addAttribute("patients",patientService.findAll());
        return "doctor";
    }
	
	@RequestMapping(value = "/viewConsultation", method = RequestMethod.GET)
    public String viewConsultation(Model model) {
       	model.addAttribute("patients",patientService.findAll());
        return "viewConsultation";
    }
	
	
	@RequestMapping(value = "/doctorAddConsult", method = RequestMethod.GET)
    public String doctorAddConsult(Model model) {
		List<String> hours=consultationService.seeFreeHoursForDoctorInAGivenDay(sqlDate, doctor);
       	model.addAttribute("hours",hours);
       	System.out.println("Free hours:"+hours);
        return "doctorAddConsult";
    }
	
	@RequestMapping(value = "/doctorAddConsult", method = RequestMethod.POST)
    public String doctorAddConsultPost(HttpServletRequest request,Model model) {
		String hour=request.getParameter("hour");
		Consultation consultation=new Consultation(patient,doctor,sqlDate,hour);
		consultationService.save(consultation);
		model.addAttribute("success","Consultation has been added successfully");
        return "doctorAddConsult";
    }
	
	@RequestMapping(value = "/doctor", method = RequestMethod.POST)
    public String postAddCons(HttpServletRequest request,Model model) {
	 
	     String cnp=request.getParameter("patient");
	     SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
		 String date = request.getParameter("date");
		    java.util.Date dates;
				try {
					dates = in.parse(date);
					System.out.println("the date is"+dates);
				    sqlDate = new java.sql.Date(dates.getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    patient=patientService.findByCnp(cnp);
        return "redirect:/doctorAddConsult";
    }
	
	
	@RequestMapping(value = "/viewConsultation", method = RequestMethod.POST)
    public String viewConsultationPost(HttpServletRequest request,Model model) {
		String cnp=request.getParameter("patient");
		patient=patientService.findByCnp(cnp);
		model.addAttribute("patients",patientService.findAll());
       	model.addAttribute("consultations",consultationService.seeConsultationsForAGivenPatientAndDoctor(patient, doctor));
        return "viewConsultation";
    }
}
