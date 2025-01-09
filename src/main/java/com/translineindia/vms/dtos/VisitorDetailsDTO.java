package com.translineindia.vms.dtos;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class VisitorDetailsDTO {

  @NotBlank(message = "Visitor Address cannot be blank")
  @Size(max = 50, message = "Visitor Address must not exceed 50 characters")
  private String visitorAdd;

  @NotBlank(message = "ID Proof cannot be blank")
  @Size(max = 20, message = "ID Proof must not exceed 20 characters")
  private String idProof;

//  @NotNull(message = "File data cannot be null")
//  private MultipartFile fileData;

  @NotBlank(message = "Visitor Name cannot be blank")
  @Size(max = 30, message = "Visitor Name must not exceed 30 characters")
  private String visName;

//  @NotBlank(message = "Visitor DOB cannot be blank")
//  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "DOB must be in YYYY-MM-DD format")
  @NotNull(message = "DOB must be in YYYY-MM-DD format")
  @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
  private LocalDate visDob;

  @NotBlank(message = "Visitor Number cannot be blank")
  @Size(max = 13, message = "Visitor Number must not exceed 13 characters")
  private String visNum;

  @Size(max = 30, message = "Visitor Accessories must not exceed 30 characters")
  private String visAccessories;

//  @NotNull(message = "Visitor Photo cannot be null")
//  private MultipartFile visPhoto;
	
	
	
}
