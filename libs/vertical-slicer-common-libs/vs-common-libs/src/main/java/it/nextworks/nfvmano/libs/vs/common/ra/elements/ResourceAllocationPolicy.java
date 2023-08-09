package it.nextworks.nfvmano.libs.vs.common.ra.elements;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;

import java.text.SimpleDateFormat;
import java.util.*;

public class ResourceAllocationPolicy {
    private UUID  resourceAllocationPolicyId;
    private String nstId;
    private List<String> nsstIds;
    private RAPolicySliceType sliceType;
    private String tenant;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date expirationTime;
    private RAAlgorithmType algorithmType;
    private Map<String,String> raAlgorithmSpecificParams=new HashMap<>();
    private boolean isDefault;


    public ResourceAllocationPolicy(){}

    public ResourceAllocationPolicy(UUID resourceAllocationPolicyId, String nstId,
                                  List<String> nsstIds,
                                  RAPolicySliceType sliceType,
                                  String tenant,
                                  Date expirationTime,
                                  RAAlgorithmType algorithmType,
                                  Map<String, String> raAlgorithmSpecificParams,
                                    boolean isDefault){
        this.nstId=nstId;
        this.resourceAllocationPolicyId = resourceAllocationPolicyId;
        this.nsstIds=nsstIds;
        this.sliceType=sliceType;
        this.tenant=tenant;
        this.expirationTime=expirationTime;
        this.algorithmType=algorithmType;
        this.raAlgorithmSpecificParams=raAlgorithmSpecificParams;
        this.isDefault= isDefault;
    }

    public void isValid() throws MalformattedElementException {

    }

    public String getNstId() {
        return nstId;
    }

    public void setNstId(String nstId) {
        this.nstId = nstId;
    }

    public List<String> getNsstIds() {
        return nsstIds;
    }

    public void setNsstIds(List<String> nsstIds) {
        this.nsstIds = nsstIds;
    }

    public RAPolicySliceType getSliceType() {
        return sliceType;
    }

    public void setSliceType(RAPolicySliceType sliceType) {
        this.sliceType = sliceType;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime=expirationTime;
    }

    public RAAlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(RAAlgorithmType algorithmType) {
        this.algorithmType = algorithmType;
    }

    public Map<String, String> getRaAlgorithmSpecificParams() {
        return raAlgorithmSpecificParams;
    }

    public void setRaAlgorithmSpecificParams(Map<String, String> raAlgorithmSpecificParams) {
        this.raAlgorithmSpecificParams = raAlgorithmSpecificParams;
    }
}
