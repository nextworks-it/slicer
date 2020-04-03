package it.nextworks.nfvmano.sebastian.nste2eComposer.IM;

import it.nextworks.nfvmano.libs.ifa.templates.GeographicalAreaInfo;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
public class NstAdvertisedInfo {

    @Id
    @GeneratedValue
    private Long id;
    private String nstId;
    private String domainId;

    @OneToMany(cascade= CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<GeographicalAreaInfo> geographicalAreaInfoList;

public NstAdvertisedInfo(){

}

public NstAdvertisedInfo(String nstId, String domainId,  List<GeographicalAreaInfo> geographicalAreaInfoList){
    this.nstId=nstId;
    this.domainId=domainId;
    this.geographicalAreaInfoList=geographicalAreaInfoList;
}


    public String getNstId() {
        return nstId;
    }

    public String getDomainId() {
        return domainId;
    }

    public List<GeographicalAreaInfo> getGeographicalAreaInfoList() {
        return geographicalAreaInfoList;
    }

}
