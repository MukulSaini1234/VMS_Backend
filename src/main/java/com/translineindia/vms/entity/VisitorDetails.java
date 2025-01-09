package com.translineindia.vms.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class VisitorDetails {

	@Column(name = "vis_name", length = 30)
	private String visName;
	
	@Column(name = "vis_DOB")
	private LocalDate visDob;
	
	@Column(name = "vis_num", length = 13)
	private String visNum;
	
	@Column(name = "vis_accessories", length = 30)
	private String visAccessories;
	
//	@Column(name = "vis_photo")
//	private byte[] visPhoto;
	
	@Column(name = "vis_vehicle")
	private Boolean visVehicle;
    // Getters and Setters
}
