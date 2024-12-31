package com.translineindia.vms.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
public class VisitorId implements Serializable{	
	private String cmpCd;
		
	private String visitorId;
}
