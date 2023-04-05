package it.nextworks.nfvmano.nsmf.ra.record.elements;

import it.nextworks.nfvmano.libs.vs.common.ra.elements.RAAlgorithmType;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.RAPolicySliceType;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.ResourceAllocationPolicy;

import javax.persistence.*;
import java.util.*;

@Entity
public class ResourceAllocationPolicyRecord {
    @Id
    @GeneratedValue
    private UUID id;

    private String nstId;
    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> nsstIds;
    private RAPolicySliceType sliceType;
    private String tenant;

    private Date expirationTime;
    private RAAlgorithmType algorithmType;
    @ElementCollection(fetch=FetchType.EAGER)
    private Map<String,String> raAlgorithmSpecificParams=new HashMap<>();

    private boolean isDefault;



    public ResourceAllocationPolicyRecord(ResourceAllocationPolicy policy) {
        this.nsstIds=policy.getNsstIds();
        this.nstId = policy.getNstId();
        this.sliceType= policy.getSliceType();
        this.expirationTime= policy.getExpirationTime();
        this.algorithmType= policy.getAlgorithmType();
        this.raAlgorithmSpecificParams= policy.getRaAlgorithmSpecificParams();
        this.tenant=policy.getTenant();
        this.isDefault=policy.isDefault();
    }

    public ResourceAllocationPolicyRecord() {
    }

    public UUID getId() {
        return id;
    }

    public String getNstId() {
        return nstId;
    }

    public List<String> getNsstIds() {
        return nsstIds;
    }

    public RAPolicySliceType getSliceType() {
        return sliceType;
    }

    public String getTenant() {
        return tenant;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public RAAlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    public Map<String, String> getRaAlgorithmSpecificParams() {
        return raAlgorithmSpecificParams;
    }

    public ResourceAllocationPolicy getResourceAllocationPolicy(){
        return new ResourceAllocationPolicy(id, nstId, nsstIds, sliceType, tenant, expirationTime, algorithmType, raAlgorithmSpecificParams,isDefault);
    }
}
