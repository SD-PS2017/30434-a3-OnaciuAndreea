package main.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "doctor")
public class Doctor extends User{

	
	@Enumerated
	private Specialization specialization;

	
	public Doctor(String username, String password, String passwordConfirm, String name, float salary,
			Specialization specialization) {
		super(username, password, passwordConfirm, name, salary);
		this.specialization = specialization;
	}


	public Doctor(){
		
	}
	
	public Specialization getSpecialization() {
		return specialization;
	}

	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}
	
}
