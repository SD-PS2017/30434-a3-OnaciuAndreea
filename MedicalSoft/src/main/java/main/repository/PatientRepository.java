package main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import main.entities.Patient;


public interface PatientRepository  extends JpaRepository<Patient, Long> {
	Patient findByCnp(String cnp);
	void deleteByCnp(String cnp);
}
