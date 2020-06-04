package it.nextworks.nfvmano.sebastian.nste2eComposer.IM;

import it.nextworks.nfvmano.libs.ifa.templates.GeographicalAreaInfo;
import it.nextworks.nfvmano.libs.ifa.templates.plugAndPlay.PpFunction;
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

    @OneToMany(cascade= CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<NstAdvertisedInfo> nstAdvertisedInfoList;

    public Bucket(){
        //For JPA only
    }
    public Bucket(BucketType bucketType, BucketScenario bucketScenario){
        this.bucketType=bucketType;
        this.bucketScenario = bucketScenario;
        nstAdvertisedInfoList = new ArrayList<>();
    }

    public BucketScenario getBucketScenario() {
        return bucketScenario;
    }


    private int nstAdvertisedInfoInList(String nstId){
        for(int i=0; i<nstAdvertisedInfoList.size(); i++)
        for (NstAdvertisedInfo nstAdvertisedInfoTmp : nstAdvertisedInfoList){
            if (nstAdvertisedInfoTmp.getNstId().equals(nstId)) {
                return i;
            }
        }
        return -1;
    }

    public boolean addNstId(String nstId, String domainId, List<GeographicalAreaInfo> geographicalAreaInfos, List<PpFunction> ppFunction, List<String> kpiList) {
        if (nstAdvertisedInfoInList(nstId) == -1) {
            nstAdvertisedInfoList.add(new NstAdvertisedInfo(nstId, domainId, geographicalAreaInfos,ppFunction,kpiList));
            return true;
        }
        return false;
    }

    public void removeNstId(String nstId){
        int indexElement = nstAdvertisedInfoInList(nstId);
        if (indexElement != -1) {
            nstAdvertisedInfoList.remove(indexElement);
        }
    }

    public NstAdvertisedInfo getNstAdvertisementInfoById(String nstId){
        for (NstAdvertisedInfo nstAdvertisedInfoTmp : nstAdvertisedInfoList){
            if (nstAdvertisedInfoTmp.getNstId().equals(nstId)) {
                return nstAdvertisedInfoTmp;
            }
        }
        return null;
    }

    public Long getId(){
        return this.id;
    }

    public BucketType getBucketType() {
        return bucketType;
    }

    public List<NstAdvertisedInfo> getNstAdvertisedInfoList() {
        return nstAdvertisedInfoList;
    }

    public void setNstAdvertisedInfoList(List<NstAdvertisedInfo> nstAdvertisedInfoList) {
        this.nstAdvertisedInfoList = nstAdvertisedInfoList;
    }

}
