package main.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "patient")
public class Patient {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private String name;
	
	@Column
	private String cnp;
	
	@Column
	private String idcn;
	
	@Column
	private Date dateOfBirth;
	
	@Column
	private String address;
	
	public Patient(String name,String cnp,String idcn, Date dateOfBirth,String address){
		this.name=name;
		this.cnp=cnp;
		this.idcn=idcn;
		this.dateOfBirth=dateOfBirth;
		this.address=address;
	}
	
	public Patient(String cnp){
		this.cnp=cnp;
		
	}
	
	public Patient(){
		this(null);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCnp() {
		return cnp;
	}
	public void setCnp(String cNP) {
		cnp = cNP;
	}
	public String getIdcn() {
		return idcn;
	}
	public void setIdcn(String idcn) {
		this.idcn = idcn;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	

}
