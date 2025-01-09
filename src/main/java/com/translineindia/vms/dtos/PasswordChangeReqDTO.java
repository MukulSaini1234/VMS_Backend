package com.translineindia.vms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordChangeReqDTO {

	private String cmpCd;
	private String newPassword;
	private String confirmPassword;
	private String email;
}
