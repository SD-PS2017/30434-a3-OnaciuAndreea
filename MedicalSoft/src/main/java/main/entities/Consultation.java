package main.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.joda.time.Hours;



@Entity
@Table(name = "consultation")
public class Consultation {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@JoinColumn
	@ManyToOne
	private Patient patient;
	
	@JoinColumn
	@ManyToOne
	private Doctor doctor;
	
	@Column
	private Date date;
	
	@Column
	private String hour;
	
	
	public Consultation(Patient patient, Doctor doctor, Date date, String hour) {
		this.patient = patient;
		this.doctor = doctor;
		this.date = date;
		this.hour = hour;
	}
	

	public Consultation(){
		
	}
	
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
