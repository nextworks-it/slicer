package it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.elements;

import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.common.ManoObjectType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SoObject {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String descriptorId;
    private String version;
    private ManoObjectType type;
    private String catalogueId;
    private Long epoch;
    private String soId;

    private String path; //TODO remove??

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescriptorId() {
        return descriptorId;
    }

    public void setDescriptorId(String descriptorId) {
        this.descriptorId = descriptorId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ManoObjectType getType() {
        return type;
    }

    public void setType(ManoObjectType type) {
        this.type = type;
    }

    public Long getEpoch() {
        return epoch;
    }

    public void setEpoch(Long epoch) {
        this.epoch = epoch;
    }

    public String getSoId() {
        return soId;
    }

    public void setSoId(String soId) {
        this.soId = soId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(String catalogueId) {
        this.catalogueId = catalogueId;
    }
}
