package it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.elements;


import javax.persistence.*;
import java.util.UUID;

@Entity
public class OsmTranslationInformation {

    @Id
    private UUID id;

    private String catInfoId;
    private String osmInfoId;
    private String catDescriptorId;
    private String osmDescriptorId;
    private String descriptorVersion;

    private String osmManoId;

    public OsmTranslationInformation(){

    }

    public OsmTranslationInformation(String catInfoId, String osmInfoId, String catDescriptorId, String osmDescriptorId, String descriptorVersion, String osmManoId){
        this.id = UUID.randomUUID();
        this.catInfoId = catInfoId;
        this.osmInfoId = osmInfoId;
        this.catDescriptorId = catDescriptorId;
        this.osmDescriptorId = osmDescriptorId;
        this.descriptorVersion = descriptorVersion;
        this.osmManoId = osmManoId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCatInfoId() {
        return catInfoId;
    }

    public String getOsmInfoId() {
        return osmInfoId;
    }

    public String getCatDescriptorId() {
        return catDescriptorId;
    }

    public String getOsmDescriptorId() {
        return osmDescriptorId;
    }

    public String getDescriptorVersion() {
        return descriptorVersion;
    }

    public String getOsmManoId() {
        return osmManoId;
    }

    public void setOsmManoId(String osmManoId) {
        this.osmManoId = osmManoId;
    }
}