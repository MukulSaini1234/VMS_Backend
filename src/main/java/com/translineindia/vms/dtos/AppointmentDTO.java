package com.translineindia.vms.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AppointmentDTO {

    @NotBlank(message = "Employee ID cannot be blank")
    @Size(max = 10, message = "Employee ID must not exceed 10 characters")
    private String empId;

    @NotBlank(message = "Employee Name cannot be blank")
    @Size(max = 30, message = "Employee Name must not exceed 30 characters")
    private String empName;

    @NotBlank(message = "Employee Email cannot be blank")
    @Email(message = "Invalid email format")
    @Size(max = 40, message = "Employee Email must not exceed 40 characters")
    private String empEmail;

    @NotBlank(message = "Purpose cannot be blank")
    @Size(max = 50, message = "Purpose must not exceed 50 characters")
    private String purpose;

    @NotBlank(message = "For Days cannot be blank")
    @Pattern(regexp = "\\d", message = "For Days must be a single digit")
    private String forDays;

    @NotBlank(message = "From Date cannot be blank")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "From Date must be in YYYY-MM-DD format")
    private String fromDate;

    @NotBlank(message = "Visitor Address cannot be blank")
    @Size(max = 50, message = "Visitor Address must not exceed 50 characters")
    private String visitorAdd;

    @NotBlank(message = "ID Proof cannot be blank")
    @Size(max = 20, message = "ID Proof must not exceed 20 characters")
    private String idProof;

    @NotNull(message = "File data cannot be null")
    private byte[] fileData;

    @NotBlank(message = "Visitor Name cannot be blank")
    @Size(max = 30, message = "Visitor Name must not exceed 30 characters")
    private String visName;

    @NotBlank(message = "Visitor DOB cannot be blank")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "DOB must be in YYYY-MM-DD format")
    private String visDob;

    @NotBlank(message = "Visitor Number cannot be blank")
    @Size(max = 13, message = "Visitor Number must not exceed 13 characters")
    private String visNum;

    @Size(max = 30, message = "Visitor Accessories must not exceed 30 characters")
    private String visAccessories;

    @NotNull(message = "Visitor Photo cannot be null")
    private byte[] visPhoto;

    @NotNull(message = "Visitor Vehicle status cannot be null")
    private Boolean visVehicle;

    @Size(max = 20, message = "Visitor Driver Name must not exceed 20 characters")
    private String visDrivername;

    @Size(max = 20, message = "Visitor Vehicle Number must not exceed 20 characters")
    private String visVehicleno;

    @Size(max = 20, message = "Visitor Vehicle Type must not exceed 20 characters")
    private String visVehicleType;

    @Size(max = 20, message = "Visitor Driver DL Number must not exceed 20 characters")
    private String visDriverDlNo;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Driver DL Upto must be in YYYY-MM-DD format")
    private String visDriverDlUpto;
}
