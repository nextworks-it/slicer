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
public class UpfInstanceInfo {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private String upfInstanceId;
    private String upfName;
    private String nsdIdCore;

    private String ipCnc;
    private int portCnc;

    @ElementCollection(
            targetClass = String.class
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<String> upfNetworkSliceId;
    @ElementCollection(
            targetClass = String.class
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<String> gNBList;


    public UpfInstanceInfo(){
        upfNetworkSliceId = new HashSet<>();
        gNBList = new HashSet<>();
        upfName = "";
    }


    public Set<String> getUpfNetworkSliceId() {
        return upfNetworkSliceId;
    }

    public void setUpfNetworkSliceId(List<String> upfNetworkSliceId) {
        this.upfNetworkSliceId.addAll(upfNetworkSliceId);
    }

    public void addCoreNetworkSliceInfo(String coreNetworkSliceInfo){
        upfNetworkSliceId.add(coreNetworkSliceInfo);
    }

    public String getUpfInstanceId() {
        return upfInstanceId;
    }

    public void setUpfInstanceId(String upfInstanceId) {
        this.upfInstanceId = upfInstanceId;
    }

    public void removeCoreNetworkSlice(String coreNetworkSliceIdToRemove){
        upfNetworkSliceId.remove(coreNetworkSliceIdToRemove);

    }

    public void setCoreNetworkSliceId(Set<String> coreNetworkSliceId) {
        this.upfNetworkSliceId = coreNetworkSliceId;
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

    public String getUpfName() {
        return upfName;
    }

    public void setUpfName(String upfName) {
        this.upfName = upfName;
    }
}
