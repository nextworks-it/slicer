package it.nextworks.nfvmano.libs.vs.common.ra.elements;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class NsResourceAllocation {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nsiId;

    private List<NssResourceAllocation> nssResourceAllocations;

    public NsResourceAllocation(){}

    public NsResourceAllocation(String nsiRaId, String nsiId, List<NssResourceAllocation> nssResourceAllocations){

        this.nsiId=nsiId;
        this.nssResourceAllocations=nssResourceAllocations;
    }



    public String getNsiId() {
        return nsiId;
    }

    public void setNsiId(String nsiId) {
        this.nsiId = nsiId;
    }

    public List<NssResourceAllocation> getNssResourceAllocations() {
        return nssResourceAllocations;
    }

    public void setNssResourceAllocations(List<NssResourceAllocation> nssResourceAllocations) {
        this.nssResourceAllocations = nssResourceAllocations;
    }

    public void isValid() {
    }
}
