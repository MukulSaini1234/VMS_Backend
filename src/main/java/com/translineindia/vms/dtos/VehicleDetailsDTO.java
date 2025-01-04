package com.translineindia.vms.dtos;

import java.time.LocalDate;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Validated
public class VehicleDetailsDTO {
	@Size(max = 20, message = "Visitor Driver Name must not exceed 20 characters")
    private String visDrivername;

    @Size(max = 20, message = "Visitor Vehicle Number must not exceed 20 characters")
    private String visVehicleno;

    @Size(max = 20, message = "Visitor Vehicle Type must not exceed 20 characters")
    private String visVehicleType;

    @Size(max = 20, message = "Visitor Driver DL Number must not exceed 20 characters")
    private String visDriverDlNo;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate visDriverDlUpto;
}
