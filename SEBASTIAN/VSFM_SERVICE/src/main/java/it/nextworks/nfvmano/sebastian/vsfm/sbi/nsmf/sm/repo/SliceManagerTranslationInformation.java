package it.nextworks.nfvmano.sebastian.vsfm.sbi.nsmf.sm.repo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SliceManagerTranslationInformation {
    @Id
    @GeneratedValue
    private Long id;

    String nsiId;
    String sliceManagerNestId;
    String sliceManagerId;

    public SliceManagerTranslationInformation() {
    }

    public SliceManagerTranslationInformation(String nsiId, String sliceManagerNestId) {
        this.nsiId = nsiId;
        this.sliceManagerNestId = sliceManagerNestId;
    }



    public String getNsiId() {
        return nsiId;
    }

    public String getSliceManagerNestId() {
        return sliceManagerNestId;
    }

    public String getSliceManagerId() {
        return sliceManagerId;
    }

    public void setSliceManagerId(String sliceManagerId) {
        this.sliceManagerId = sliceManagerId;
    }
}
