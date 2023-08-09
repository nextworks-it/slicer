package it.nextworks.nfvmano.catalogue.engine.elements;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

//class needed to store invariant Id for ONAP Ns (needed for compatibility with portal catalogue)
@Entity
public class NsdIdInvariantIdMapping {

    @Id
    @GeneratedValue
    private Long id;

    String nsdId;
    String invariantId;

    public NsdIdInvariantIdMapping() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNsdId() {
        return nsdId;
    }

    public void setNsdId(String nsdId) {
        this.nsdId = nsdId;
    }

    public String getInvariantId() {
        return invariantId;
    }

    public void setInvariantId(String invariantId) {
        this.invariantId = invariantId;
    }
}
