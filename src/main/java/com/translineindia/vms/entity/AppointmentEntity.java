package com.translineindia.vms.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="Appointment_Request")
@Data
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming your table is configured to auto-increment this column
	@Column(name = "req_id")
	private Long reqId; // Using Long for primary key to accommodate large number
	
	@Column(name = "emp_id", length = 10)
	private String empid;
	
	@Column(name = "created_by", length = 50)
	private String visitorId;
	
	@Column(name = "emp_name", length = 30)
	private String empName;
	
	@Column(name = "emp_email", length = 40)
	private String empEmail;
	
	@Column(name="purpose", length = 50)
	private String purpose;
	
	@Column(name = "fordays", length = 1)
	private String forDays;
	
	@Column(name = "from_date")
	private LocalDate fromDate;
	
	@Column(name = "visitor_add", length = 50)
	private String visitorAdd;
	
	@Column(name = "id_proof", length = 20)
	private String idProof;
	
	@Column(name = "id_proof_file")
	private byte[] fileData;
	
	@Column(name = "vis_name", length = 30)
	private String visName;
	
	@Column(name = "vis_DOB")
	private LocalDate visDob;
	
	@Column(name = "vis_num", length = 13)
	private String visNum;
	
	@Column(name = "vis_accessories", length = 30)
	private String visAccessories;
	
	@Column(name = "vis_photo")
	private byte[] visPhoto;
	
	@Column(name = "vis_vehicle")
	private Boolean visVehicle;
	
	@Column(name = "vis_drivername", length = 20)
	private String visDrivername;
	
	@Column(name = "vis_vehicleno", length = 20)
	private String visVehicleno;
	
	@Column(name = "vis_vehicletype", length = 20)
	private String visVehicleType;
	
	@Column(name = "vis_driverdlno", length = 20)
	private String visDriverDlNo;
	
	@Column(name = "vis_driverdlupto", length = 20)
	private LocalDate visDriverDlUpto;
	
}
