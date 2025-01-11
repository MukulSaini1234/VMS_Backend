package com.translineindia.vms.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UploadsDTO {
	private int uploadId;
	private String uploadType;
	private String refId;// another table PK
	private String filePath;
	private String fileDescription;
	private String fileSize;
//	private String createdBy;
//	private LocalDateTime createdAt;

	private String createdBy = "system";
	private LocalDateTime createdAt = LocalDateTime.now();
}