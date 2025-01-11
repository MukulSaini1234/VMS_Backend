package com.translineindia.vms.dtos;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class VisitorRequestDtlsDTO {
    private Long id;
    
    
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    
    @NotEmpty(message = "Date of Birth cannot be empty")
    @NotNull(message = "Date of Birth cannot be null")
    private String dob; // Encrypted format
    
    @NotEmpty(message = "ID Proof cannot be empty")
    private String idProof;
    
    @NotEmpty(message = "ID Proof Number cannot be empty")
    @NotNull(message = "ID Proof Number cannot be null")
    private String idproofNo; // Encrypted format
    
    @NotNull(message = "Contact number cannot be null")
    @NotEmpty(message = "Contact number cannot be empty")
    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be a valid 10-digit number")
    private String contactNo; // Encrypted format 
    private String accessories;
    @NotEmpty 
    private String photo;   // changed from multipart on 11-01-25
   // private String photo;
    
//    private String idProofFile;
    
    private MultipartFile idProofFile;
    private Long visitorMstId; // Reference to `VisitorRequestMst`
}
