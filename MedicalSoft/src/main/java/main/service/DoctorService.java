package main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.entities.Doctor;
import main.entities.Specialization;
import main.entities.User;
import main.repository.DoctorRepository;
import main.repository.UserRepository;

@Service
public class DoctorService {

	    @Autowired
	    private UserRepository userRepository;
	    
	    @Autowired
	    private DoctorRepository doctorRepository;
	    
	    public void save(Doctor d){
	    	User user=  userRepository.findByUsername(d.getUsername()).get(0);
	    	d.setId(user.getId());
	    	doctorRepository.save(d);
	    }
	    
	    public List<Doctor> findAll(){
	    	return doctorRepository.findAll();
	    }
	    
	    public List<Doctor> findBySpecialization(Specialization type){
	    	return doctorRepository.findBySpecialization(type);
	    }
	    
	    public Doctor findByUsername(String username){
	    	return doctorRepository.findByUsername(username);
	    }
	
}
