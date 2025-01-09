package com.translineindia.vms.dtos;

import lombok.Data;

@Data
public class VisitorRequestDtlsDTO {
    private Long id;
    private String name;
    private String dob; // Encrypted format
    private String idProof;
    private String idproofNo; // Encrypted format
    private String contactNo; // Encrypted format
    private String accessories;
    private String photo;
    private String idProofFile;
    private Long visitorMstId; // Reference to `VisitorRequestMst`
}
