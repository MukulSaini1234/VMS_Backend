package com.translineindia.vms.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public class OfficeId implements Serializable {

	private String cmpCd;
	
	private String offCd;
	
	public OfficeId() {}

    public OfficeId(String cmpCd, String offCd) {
        this.cmpCd = cmpCd;
        this.offCd = offCd;
    }
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfficeId officeKey = (OfficeId) o;
        return Objects.equals(cmpCd, officeKey.cmpCd) && Objects.equals(offCd, officeKey.offCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cmpCd, offCd);
    }
}
