package it.nextworks.nfvmano.sebastian.record.elements;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.ManyToOne;

@Entity
public class NsSapInfo {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @ManyToOne
    private NetworkSliceInstance networkSliceInstance;

    private String sapdId;


    private String sapName;
    private String description;
    private String address;

    public NsSapInfo() {
    }

    public NsSapInfo(String sapdId, String sapName, String description, String address) {
        this.sapdId = sapdId;
        this.sapName = sapName;
        this.description = description;
        this.address = address;
    }

    public NetworkSliceInstance getNetworkSliceInstance() {
        return networkSliceInstance;
    }


    public String getSapdId() {
        return sapdId;
    }

    public void setSapdId(String sapdId) {
        this.sapdId = sapdId;
    }

    public String getSapName() {
        return sapName;
    }

    public void setSapName(String sapName) {
        this.sapName = sapName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
