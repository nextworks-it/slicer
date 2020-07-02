package it.nextworks.nfvmano.sebastian.nste2eComposer.IM;

import it.nextworks.nfvmano.libs.ifa.templates.GeographicalAreaInfo;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import it.nextworks.nfvmano.libs.ifa.templates.plugAndPlay.PpFunction;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.ArrayList;
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


    @OneToMany(cascade= CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PpFunction> ppFunctionList = new ArrayList();

    @ElementCollection(fetch=FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> kpiList;

public NstAdvertisedInfo(){

}

public NstAdvertisedInfo(String nstId,
                         String domainId,
                         List<GeographicalAreaInfo> geographicalAreaInfoList,
                         List<PpFunction> ppFunctionList,
                         List<String> kpiList){
    this.nstId=nstId;
    this.domainId=domainId;
    this.geographicalAreaInfoList=geographicalAreaInfoList;
    this.ppFunctionList = ppFunctionList;
    this.kpiList = kpiList;
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

    public List<PpFunction> getPpFunctionList() {
        return ppFunctionList;
    }

    public List<String> getKpiList() {
        return kpiList;
    }

    public void setKpiList(List<String> kpiList){
    this.kpiList=kpiList;
    }
}
