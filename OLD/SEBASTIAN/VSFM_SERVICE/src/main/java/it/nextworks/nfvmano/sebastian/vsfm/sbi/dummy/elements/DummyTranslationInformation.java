package it.nextworks.nfvmano.sebastian.vsfm.sbi.dummy.elements;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DummyTranslationInformation {

    @Id
    @GeneratedValue
    private Long id;
    private String nsiId;
    private String nstId;
    private String domainId;

    public DummyTranslationInformation() {
    }

    public DummyTranslationInformation(String nsiId, String nstId, String domainId) {
        this.nsiId = nsiId;
        this.nstId = nstId;
        this.domainId = domainId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNsiId() {
        return nsiId;
    }

    public void setNsiId(String nsiId) {
        this.nsiId = nsiId;
    }

    public String getNstId() {
        return nstId;
    }

    public void setNstId(String nstId) {
        this.nstId = nstId;
    }
}
