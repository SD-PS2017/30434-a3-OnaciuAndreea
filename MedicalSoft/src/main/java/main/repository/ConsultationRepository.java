package main.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import main.entities.Consultation;
import main.entities.Doctor;
import main.entities.Patient;

public interface ConsultationRepository extends JpaRepository<Consultation,Long>{
	Consultation findById(int id);
	List<Consultation> findByDoctorAndDate(Doctor doctor,Date d);
	List<Consultation> findByPatient(Patient p);
	List<Consultation> findByDoctorAndPatient(Doctor doctor,Patient p);
	List<Consultation> findByDoctorAndPatientAndDate(Doctor doctor,Patient p,Date date);
	Consultation findByDoctorAndPatientAndDateAndHour(Doctor doctor,Patient p,Date date,String hour);
}
