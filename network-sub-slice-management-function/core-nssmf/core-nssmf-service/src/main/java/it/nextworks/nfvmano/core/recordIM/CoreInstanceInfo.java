package it.nextworks.nfvmano.core.recordIM;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class CoreInstanceInfo {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private String coreInstanceId;

    private String nsdIdCore;

    private String ipCnc;
    private int portCnc;

    @ElementCollection(
            targetClass = String.class
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<String> coreNetworkSliceId;
    @ElementCollection(
            targetClass = String.class
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<String> gNBList;


    public CoreInstanceInfo(){
        coreNetworkSliceId = new HashSet<>();
        gNBList = new HashSet<>();
    }


    public Set<String> getCoreNetworkSliceId() {
        return coreNetworkSliceId;
    }

    public void setCoreNetworkSliceId(List<String> coreNetworkSliceId) {
        this.coreNetworkSliceId.addAll(coreNetworkSliceId);
    }

    public void addCoreNetworkSliceInfo(String coreNetworkSliceInfo){
        coreNetworkSliceId.add(coreNetworkSliceInfo);
    }

    public String getCoreInstanceId() {
        return coreInstanceId;
    }

    public void setCoreInstanceId(String coreInstanceId) {
        this.coreInstanceId = coreInstanceId;
    }

    public void removeCoreNetworkSlice(String coreNetworkSliceIdToRemove){
        coreNetworkSliceId.remove(coreNetworkSliceIdToRemove);

    }

    public void setCoreNetworkSliceId(Set<String> coreNetworkSliceId) {
        this.coreNetworkSliceId = coreNetworkSliceId;
    }

    public Set<String> getgNBList() {
        return gNBList;
    }

    public void setgNBList(Set<String> gNBList) {
        this.gNBList = gNBList;
    }

    public String getNsdIdCore() {
        return nsdIdCore;
    }

    public void setNsdIdCore(String nsdIdCore) {
        this.nsdIdCore = nsdIdCore;
    }

    public String getIpCnc() {
        return ipCnc;
    }

    public void setIpCnc(String ipCnc) {
        this.ipCnc = ipCnc;
    }

    public int getPortCnc() {
        return portCnc;
    }

    public void setPortCnc(int portCnc) {
        this.portCnc = portCnc;
    }
}
