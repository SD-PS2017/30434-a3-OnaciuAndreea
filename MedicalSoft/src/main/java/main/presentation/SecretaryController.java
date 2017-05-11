package main.presentation;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entities.Consultation;
import main.entities.Doctor;
import main.entities.Notification;
import main.entities.Patient;
import main.entities.Specialization;
import main.service.ConsultationService;
import main.service.DoctorService;
import main.service.NotificationService;
import main.service.PatientService;
import main.validator.PatientValidator;

@Controller
public class SecretaryController {

		@Autowired
		private PatientValidator patientValidator;
	
		@Autowired
		private PatientService patientService;
		
		@Autowired
		private DoctorService doctorService;
		
		@Autowired
		private ConsultationService consultationService;
		
		@Autowired
		private NotificationService notificationService;
		
		List<Doctor> listOfDoctors=new ArrayList<Doctor>();
		Consultation consultToEdit;
		Patient patient=new Patient();
		Patient patientToConsult=new Patient();
		Doctor doctor=new Doctor();
		Date sqlDate;
		
				
	    @RequestMapping(value = "/editPatient", method = RequestMethod.GET)
	    public String editPatient(Model model) {
	    	model.addAttribute("editPatient",new Patient());
	        return "editPatient";
	    }
	    
	    @RequestMapping(value = "/notifyDoctor", method = RequestMethod.GET)
	    public String notifyDoctor(Model model) {
	    	model.addAttribute("doctors",doctorService.findAll());
	    	model.addAttribute("patients",patientService.findAll());
	        return "notifyDoctor";
	    }
	  
	    @RequestMapping(value = "/addConsultation", method = RequestMethod.GET)
	    public String addConsultation(Model model) {
	    	model.addAttribute("editPatient",new Patient());
	    	Specialization[] enumSpecialization=Specialization.values();
	    	model.addAttribute("specialization",enumSpecialization);
	        return "addConsultation";
	    }
	    
	    
	    
	    @RequestMapping(value = "/searchDoctor", method = RequestMethod.GET)
	    public String searchDoctor(Model model) { 
	    	model.addAttribute("doctors",listOfDoctors);
	    	java.util.Date utilDate=new java.util.Date();
	    	java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    	model.addAttribute("date",sqlDate);
	        return "searchDoctor";
	    }
	    
	    
	    @RequestMapping(value = "/doctorSchedule", method = RequestMethod.GET)
	    public String schedualeDoctor(Model model) { 
	    	List<String> hours=consultationService.seeFreeHoursForDoctorInAGivenDay(sqlDate, doctor);
	    	model.addAttribute("hours",hours);       
	    	return "doctorSchedule";
	    }
	    
	    @RequestMapping(value = "/deleteConsultation", method = RequestMethod.GET)
	    public String deleteConsultation(Model model) {
	    	model.addAttribute("doctors",doctorService.findAll());
	    	model.addAttribute("patients",patientService.findAll());
	    	String hours[]={"9:00","10:00","11:00","12:00","13:00","14:00","15:00"};
			List<String> hourList=new ArrayList<String>(Arrays.asList(hours));
	    	model.addAttribute("hours",hourList);
	        return "deleteConsultation";
	    }
	    
	    @RequestMapping(value = "/editConsultation", method = RequestMethod.GET)
	    public String editConsultation(Model model) {
	    	model.addAttribute("doctors",doctorService.findAll());
	    	model.addAttribute("patients",patientService.findAll());
	        return "editConsultation";
	    }
         
	        
	    @RequestMapping(value = "/changeDateTime", method = RequestMethod.GET)
	    public String editConsultationChange(Model model) {
	    	List<Consultation> consult=consultationService.seeConsultationsForAGivenPatientAndDoctorAndDate(patient, doctor,sqlDate);
	        List<String> hours=consultationService.seeFreeHoursForDoctorInAGivenDay(sqlDate, doctor);
	        model.addAttribute("hours",hours);
	    	model.addAttribute("patient",patient.getName());
	    	model.addAttribute("doctor",doctor.getName());
	    	model.addAttribute("dateconsult",sqlDate);
	    	model.addAttribute("hourconsult",consult.get(0).getHour());
	    	consultToEdit=consult.get(0);
	        return "changeDateTime";	
	    }
	    
	    @RequestMapping(value = "/seeConsultation", method = RequestMethod.GET)
	    public String seeConsultation(Model model) {
	    	model.addAttribute("consultations",consultationService.seeAll());
	        return "seeConsultation";
	    }
	    
	    
	    @RequestMapping(value = {"/secretary"}, method = RequestMethod.POST)
	    public String saveUser(@ModelAttribute("patientForm") Patient patientForm, BindingResult bindingResult, Model model) {
 
	    	String isValid=patientValidator.validatePatient(patientForm);
	        if (!isValid.equals("")) {
	            model.addAttribute("error",isValid);
	        }
	        else {
	        	model.addAttribute("success","Patient saved successfully");
	        	patientService.save(patientForm);
	        }
	        
	        return "secretary";
	    }
	    
	    @RequestMapping(value="/editPatient",method=RequestMethod.POST)
	    public String editUser(@ModelAttribute("editPatient")Patient patient,@ModelAttribute("patient")Patient patientEdit,Model model){
	    	Patient toEdit=new Patient();
	    	toEdit=patientService.findByCnp(patient.getCnp());
	    	if (toEdit==null) {
	    		model.addAttribute("error","No patient with this cnp has been found.");
	    	}
	    	else {
	    		if (patientEdit.getName()==null || patientEdit.getName().equals("")){
	    			model.addAttribute("patient",toEdit);
	    		}
	    		else {
	    			String isValid=patientValidator.validatePatientUpdate(patientEdit);
	    	        if (!isValid.equals("")) {
	    	            model.addAttribute("error1",isValid);
	    	        }
	    	        else {
	    	        	model.addAttribute("success","Patient saved successfully");
	    	        	patientService.updatePatient(patient.getCnp(), patientEdit);
	    	        }
	    		}
	    	}
	    	
	    	return "editPatient";
	    }

	    @RequestMapping(value = "/addConsultation", method = RequestMethod.POST)
	    public String schedualeConsultation(HttpServletRequest request,@ModelAttribute("editPatient")Patient patient,Model model) {
	      	patientToConsult=patientService.findByCnp(patient.getCnp());
	    	if (patientToConsult==null) {
	    		model.addAttribute("error","No patient with this cnp has been found.");
	    		return "addConsultation";
	    	}
	    	else {
                String type=request.getParameter("type");   
		    	listOfDoctors=doctorService.findBySpecialization(Specialization.valueOf(type));
                
	    		return "redirect:/searchDoctor";
	    	}	        
	    }
	    
	    @RequestMapping(value = "/doctorSchedule", method = RequestMethod.POST)
	    @MessageMapping("/doctor")
	    @SendTo("/doctor")
	    public String scheduleDoctor(HttpServletRequest request,Model model) {
	    	   
	    	   String hour=request.getParameter("hour");
	    	   System.out.println("patient"+patientToConsult.getName()+"doctor:"+doctor.getName()+"sqlDate"+sqlDate+"hour"+hour);
               Consultation newConsult=new Consultation(patientToConsult,doctor,sqlDate,hour);
               consultationService.save(newConsult);
               model.addAttribute("success","Consultation added successfully!");
	    	   return "doctorSchedule";
	    	
	    }
	    
	    
	    @RequestMapping(value = "/searchDoctor", method = RequestMethod.POST)
	    public String s(HttpServletRequest request,Model model) {
	        String doctorUsername=request.getParameter("doctor");
	        String date=request.getParameter("date");
	        SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
	        
	        java.util.Date dates;
			try {
				dates = in.parse(date);
				System.out.println("the date is"+dates);
			    sqlDate = new java.sql.Date(dates.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("the username of the doctor is:"+doctorUsername);
	        doctor=doctorService.findByUsername(doctorUsername);
	  
	    	return "redirect:/doctorSchedule";	 
	    }
	    
	    
	    @RequestMapping(value = "/editConsultation", method = RequestMethod.POST)
	    public String editConsultationGetInfo(HttpServletRequest request,Model model) {
	    	String cnp=request.getParameter("patient");
	    	String username=request.getParameter("doctor");
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
	    	doctor=doctorService.findByUsername(username);
	    	List<Consultation> consult=consultationService.seeConsultationsForAGivenPatientAndDoctorAndDate(patient, doctor,sqlDate);
	    	if (!consult.isEmpty())
	    		return "redirect:/changeDateTime";
	    	else {
	    		model.addAttribute("error","No consultation available");
	    		return "editConsultation";
	    	}
	    }
	    
	    @RequestMapping(value = "/changeDateTime", method = RequestMethod.POST)
	    public String changeDateAndTime(HttpServletRequest request,Model model) {
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
				
	    	consultToEdit.setDate(sqlDate);
	    	consultToEdit.setHour(request.getParameter("hour"));
	    	consultationService.update(consultToEdit.getId(), consultToEdit);
	        model.addAttribute("success","Consultation has been updated");
	        return "changeDateTime";
	    }
	    
	    @RequestMapping(value = "/deleteConsultation", method = RequestMethod.POST)
	    public String deleteConsult(HttpServletRequest request,Model model) {
	    	String cnp=request.getParameter("patient");
	    	String username=request.getParameter("doctor");
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
	    	doctor=doctorService.findByUsername(username);
	    	String hour=request.getParameter("hour");
	    	Consultation consult=consultationService.seeConsultationsForAGivenPatientAndDoctorAndDateAndHour(patient, doctor,sqlDate,hour);
	    	if (consult==null){
	    		model.addAttribute("error","No consultation found");
	    	}
	    	else {
	    		model.addAttribute("success","Consultation deleted");
	    		consultationService.delete(consult.getId());
	        }
	    	
	    	return "deleteConsultation";
	    }
	    
	    
	    @RequestMapping(value = "/notifyDoctor", method = RequestMethod.POST)
	    public String someAction(HttpServletRequest request,Model model) {
	    	String doctor=request.getParameter("doctor");
	    	String patientName=request.getParameter("patient");
	      notificationService.notify(
	        new Notification("Patient "+patientName+" has arrived to the consultation!"), 
	          doctor                    
	      );
	     
	      return "notifyDoctor";
	    }
	    
}
