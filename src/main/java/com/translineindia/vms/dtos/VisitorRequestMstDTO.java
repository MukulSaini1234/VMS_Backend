package com.translineindia.vms.dtos;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VisitorRequestMstDTO {
    private Long id;
    
    @NotEmpty(message = "employee Id cannot be empty")
    private String empId;
    
    @NotEmpty(message = "employee name cannot be empty")
    private String empName;
    
    @Email
    @NotEmpty(message = "employee email cannot be empty")
    private String empEmail;
    
    @NotEmpty(message = "Visitor organization canot be empty")
    private String visOrganization;
    
    @NotEmpty(message = "Purpose cannot be empty")
    private String purpose;
    
//    @NotEmpty(message = "from date cannot be empty")    
    private LocalDate fromDate;
    private LocalDate toDate;
    private String visitorAddress;
    
    @NotNull(message = "has vehicle value cannot be empty")
    private Boolean hasVehicle;
    private String vehicleNo;
    private String driverName;
    private String vehicleType;
    private String driverDlNo;
    private String driverDlUpto;
    
    @NotNull(message = "Visitor Id cannot be null")
    @NotEmpty(message = "Visitor Id cannot be empty")
    private String visitorId; // added on 08-01-25
    private List<VisitorRequestDtlsDTO> visitorDtls; // Nested DTO list
    @NotNull
    @NotEmpty
    private String cmpCd;
    private String reqStatus; // added on 15-01-25
    private String statusRemarks;// added on 15-01-25
    private boolean isExtension; // added on 23-01-25
    private LocalDate exFromDate; // added on 23-01-25
    
    private LocalDate exToDate; // added on 23-01-25
    
    private String rec_req_status; // added on 30-01-25
    
    private String rec_req_remarks; // added on 20-01-25
    
    private String face_registeration;// added on 30-01-25
    private String off_cd; // added on 03-02-25
    private String admin_req_status; // added on 03-02-25
    private String admin_req_remarks; // added on 03-02-25
}
