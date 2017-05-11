package main.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.entities.Consultation;
import main.entities.Doctor;
import main.entities.Patient;
import main.repository.ConsultationRepository;

@Service
public class ConsultationService {

	@Autowired
	private ConsultationRepository consultationRepo;
	
	public void save(Consultation consultation){
		consultationRepo.save(consultation);
	}
	
	public List<Consultation> seeAll(){
		List<Consultation> list=consultationRepo.findAll();
		return list;
	}
	
	public void update(int id,Consultation consultation){
		Consultation consult=consultationRepo.findById(id);
		consult.setDate(consultation.getDate());
		consult.setDoctor(consultation.getDoctor());
		consult.setPatient(consultation.getPatient());
		consult.setHour(consultation.getHour());
		consultationRepo.save(consult);
	}
	
	public void delete(int id){
		Consultation consult=consultationRepo.findById(id);
		consultationRepo.delete(consult);
	}
	
	public List<Consultation> seeConsultationsForDoctorInAGivenDay(Doctor doctor,Date date){
		List<Consultation> list=consultationRepo.findByDoctorAndDate(doctor, date);
		return list;
	}
	
	public List<Consultation>  seeConsultationsForAGivenPatient(Patient patient){
		List<Consultation> list=consultationRepo.findByPatient(patient);
		return list;
	}
	
	public List<Consultation>  seeConsultationsForAGivenPatientAndDoctor(Patient patient,Doctor doctor){
		List<Consultation> list=consultationRepo.findByDoctorAndPatient(doctor, patient);
		return list;
	}
	
	public List<Consultation>  seeConsultationsForAGivenPatientAndDoctorAndDate(Patient patient,Doctor doctor,Date date){
		List<Consultation> list=consultationRepo.findByDoctorAndPatientAndDate(doctor, patient,date);
		return list;
	}
	
	public Consultation  seeConsultationsForAGivenPatientAndDoctorAndDateAndHour(Patient patient,Doctor doctor,Date date,String hour){
		Consultation list=consultationRepo.findByDoctorAndPatientAndDateAndHour(doctor, patient,date,hour);
		return list;
	}
	public List<String> seeFreeHoursForDoctorInAGivenDay(Date date,Doctor doctor){
		
		String hours[]={"9:00","10:00","11:00","12:00","13:00","14:00","15:00"};
		List<String> string=new ArrayList<String>(Arrays.asList(hours));
		List<Consultation> consultations=seeConsultationsForDoctorInAGivenDay(doctor, date);
		List<String> hoursOccupied=consultations.stream().map(consultation->consultation.getHour()).collect(Collectors.toList());
		string=string.stream().filter(s->!hoursOccupied.contains(s)).collect(Collectors.toList());

		return string;
	}
}
