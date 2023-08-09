package it.nextworks.nfvmano.libs.descriptors.pnfd.nodes.PNF;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.elements.LocationInfo;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class PNFProperties implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private PNFNode pnfNode;

    private String descriptorId;
    private String functionDescription;
    private String provider;
    private String version;
    private String descriptorInvariantId;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "pnfProperties", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LocationInfo geographicalLocationInfo;

    public PNFProperties() {
    }

    public PNFProperties(PNFNode pnfNode, String descriptorId, String functionDescription, String provider, String version, String descriptorInvariantId, String name, LocationInfo geographicalLocationInfo) {
        this.pnfNode = pnfNode;
        this.descriptorId = descriptorId;
        this.functionDescription = functionDescription;
        this.provider = provider;
        this.version = version;
        this.descriptorInvariantId = descriptorInvariantId;
        this.name = name;
        this.geographicalLocationInfo = geographicalLocationInfo;
    }

    public PNFProperties(String descriptorId, String functionDescription, String provider, String version, String descriptorInvariantId, String name, LocationInfo geographicalLocationInfo) {
        this.descriptorId = descriptorId;
        this.functionDescription = functionDescription;
        this.provider = provider;
        this.version = version;
        this.descriptorInvariantId = descriptorInvariantId;
        this.name = name;
        this.geographicalLocationInfo = geographicalLocationInfo;
    }

    public Long getId() {
        return id;
    }

    public PNFNode getPnfNode() {
        return pnfNode;
    }

    @JsonProperty("descriptorId")
    public String getDescriptorId() {
        return descriptorId;
    }

    @JsonProperty("functionDescription")
    public String getFunctionDescription() {
        return functionDescription;
    }

    @JsonProperty("provider")
    public String getProvider() {
        return provider;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("descriptorInvariantId")
    public String getDescriptorInvariantId() {
        return descriptorInvariantId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("geographicalLocationInfo")
    public LocationInfo getGeographicalLocationInfo() {
        return geographicalLocationInfo;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.descriptorId == null)
            throw new MalformattedElementException("PNF properties without descriptorId");
        if (this.functionDescription == null)
            throw new MalformattedElementException("PNF properties without functionDescription");
        if (this.provider == null)
            throw new MalformattedElementException("PNF properties without provider");
        if (this.version == null)
            throw new MalformattedElementException("PNF properties without version");
        if (this.descriptorInvariantId == null)
            throw new MalformattedElementException("PNF properties without descriptorInvariantId");
        if (this.name == null)
            throw new MalformattedElementException("PNF properties without name");
    }
}
