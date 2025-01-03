package com.translineindia.vms.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtRequest {
	@NotEmpty
	private String cmpCd;
	@NotEmpty
	private String userId;
	@NotEmpty
	private String userPw;
}
