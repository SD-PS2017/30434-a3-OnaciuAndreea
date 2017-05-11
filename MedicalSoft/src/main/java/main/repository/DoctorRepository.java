package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import main.entities.Doctor;
import main.entities.Specialization;

public interface DoctorRepository  extends JpaRepository<Doctor,String> {
   Doctor findByUsername(String username);
   List<Doctor> findBySpecialization(Specialization type);
}
