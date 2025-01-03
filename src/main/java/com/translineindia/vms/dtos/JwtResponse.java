package com.translineindia.vms.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {	
	private String status;
	private String message;
	private String token;
	private String timestamp;
	
}
