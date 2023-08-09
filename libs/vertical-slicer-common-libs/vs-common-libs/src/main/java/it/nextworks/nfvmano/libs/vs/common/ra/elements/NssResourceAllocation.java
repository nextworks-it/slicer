package it.nextworks.nfvmano.libs.vs.common.ra.elements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "allocationType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ComputeNssResourceAllocation.class, 	name = "COMPUTE"),
        @JsonSubTypes.Type(value = TransportNssResourceAllocation.class, 	name = "TRANSPORT"),
        @JsonSubTypes.Type(value = SdnNssResourceAllocation.class, name = "SDN"),
        @JsonSubTypes.Type(value = RanNssResourceAllocation.class, name = "RAN")

})
public class NssResourceAllocation {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nsstId;

    NssResourceAllocationType allocationType;



    public  NssResourceAllocation(String nsstId){
        this.nsstId=nsstId;
    }

    public String getNsstId() {
        return nsstId;
    }

    public void setNsstId(String nsstId) {
        this.nsstId = nsstId;
    }

    public NssResourceAllocationType getAllocationType() {
        return allocationType;
    }
}
