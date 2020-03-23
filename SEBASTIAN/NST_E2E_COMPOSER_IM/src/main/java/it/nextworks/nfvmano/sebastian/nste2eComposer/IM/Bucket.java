package it.nextworks.nfvmano.sebastian.nste2eComposer.IM;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Inheritance(
        strategy = InheritanceType.JOINED
)
public class Bucket {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private BucketScenario bucketScenario;

    @Enumerated(EnumType.STRING)
    private BucketType bucketType;



    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name="nst_id")
    @Column(name ="domain_id")
    @CollectionTable(name="nst_id_domain_id", joinColumns=@JoinColumn(name="id"))
    private Map<String,String> nstIdDomainIdMap = new HashMap<String, String>();

    public Bucket(){
        //For JPA only
    }
    public Bucket(BucketType bucketType, BucketScenario bucketScenario){
        this.bucketType=bucketType;
        this.bucketScenario = bucketScenario;
    }

    public BucketScenario getBucketScenario() {
        return bucketScenario;
    }

    public boolean addNstId(String nstId, String ipAddress){
       if(nstIdDomainIdMap.get(nstId)==null){
           nstIdDomainIdMap.put(nstId,ipAddress);
           return true;
       }
        return false;
    }

    public void removeNstId(String nstId){
        nstIdDomainIdMap.remove(nstId);
    }

    public Map<String,String> getNstIdDomainIdMap(){
        return nstIdDomainIdMap;
    }

    public Long getId(){
        return this.id;
    }

    public BucketType getBucketType() {
        return bucketType;
    }
}
