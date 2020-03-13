package it.nextworks.nfvmano.sebastian.nste2eComposer.IM;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @LazyCollection(LazyCollectionOption.FALSE)
    @ElementCollection(targetClass=String.class)
    private List<String> nstIdList;

    public Bucket(){
        //For JPA only
    }
    public Bucket(BucketType bucketType, BucketScenario bucketScenario){
        this.bucketType=bucketType;
        this.bucketScenario = bucketScenario;
        nstIdList=new ArrayList<>();
    }

    public BucketScenario getBucketScenario() {
        return bucketScenario;
    }

    public List<String> getNstId() {
        return nstIdList;
    }

    public void addNstId(String nstId){
        nstIdList.add(nstId);
    }

    public Long getId(){
        return this.id;
    }

    public BucketType getBucketType() {
        return bucketType;
    }
}
