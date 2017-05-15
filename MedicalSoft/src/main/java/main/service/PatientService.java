package main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.entities.Patient;
import main.repository.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepo;

	public void save(Patient patient) {	
		patientRepo.save(patient);
    }
	
	public Patient findByCnp(String cnp) {
		return patientRepo.findByCnp(cnp);
	}
	
	public void updatePatient(String cnp,Patient newPatient){
		Patient patientToSave=patientRepo.findByCnp(cnp);
		patientToSave.setAddress(newPatient.getAddress());
		patientToSave.setDateOfBirth(newPatient.getDateOfBirth());
		patientToSave.setIdcn(newPatient.getIdcn());
		patientToSave.setName(newPatient.getName());
		patientRepo.save(patientToSave);
	}
	
	public List<Patient> findAll(){
		return patientRepo.findAll();
	}
	
	public void deleteByCnp(String cnp){
		patientRepo.deleteByCnp(cnp);
	}
}
