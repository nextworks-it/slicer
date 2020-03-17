package it.nextworks.nfvmano.sebastian.nste2eComposer.IM;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @Column(name ="ip_address")
    @CollectionTable(name="nst_id_ip_address", joinColumns=@JoinColumn(name="id"))
    private Map<String,String> nstIdNspMap = new HashMap<String, String>();

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
       if(nstIdNspMap.get(nstId)==null){
           nstIdNspMap.put(nstId,ipAddress);
           return true;
       }
        return false;
    }

    public void removeNstId(String nstId){
        nstIdNspMap.remove(nstId);
    }

    public Map<String,String> getNstIdNspMap(){
        return nstIdNspMap;
    }

    public Long getId(){
        return this.id;
    }

    public BucketType getBucketType() {
        return bucketType;
    }
}
