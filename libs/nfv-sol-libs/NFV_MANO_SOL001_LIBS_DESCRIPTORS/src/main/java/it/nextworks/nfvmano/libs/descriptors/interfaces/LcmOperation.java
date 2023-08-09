package it.nextworks.nfvmano.libs.descriptors.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class LcmOperation implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private Vnflcm vnflcm;

    @OneToOne
    @JsonIgnore
    private Nslcm nslcm;

    private String implementation;

    public LcmOperation() {
    }

    public LcmOperation(String implementation) {
        this.implementation = implementation;
    }

    public LcmOperation(Vnflcm vnflcm, String implementation) {
        this.vnflcm = vnflcm;
        this.implementation = implementation;
    }

    public LcmOperation(Nslcm nslcm, String implementation) {
        this.nslcm = nslcm;
        this.implementation = implementation;
    }

    public Long getId() {
        return id;
    }

    public Vnflcm getVnflcm() {
        return vnflcm;
    }

    public Nslcm getNslcm() {
        return nslcm;
    }

    @JsonProperty("implementation")
    public String getImplementation() {
        return implementation;
    }

    @Override
    public void isValid() throws MalformattedElementException {
    }
}
