package it.nextworks.nfvmano.libs.descriptors.pnfd.nodes.PnfExtCp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.templates.Node;
import it.nextworks.nfvmano.libs.descriptors.templates.TopologyTemplate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class PnfExtCpNode extends Node implements DescriptorInformationElement {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "pnfExtCpNode", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PnfExtCpProperties properties;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "pnfExtCpNode", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PnfExtCpRequirements requirements;

    public PnfExtCpNode() {

    }

    public PnfExtCpNode(String type, PnfExtCpProperties properties,
                        PnfExtCpRequirements requirements) {
        super(type);
        this.properties = properties;
        this.requirements = requirements;
    }

    public PnfExtCpNode(TopologyTemplate topologyTemplate, String type, PnfExtCpProperties properties,
                        PnfExtCpRequirements requirements) {
        super(topologyTemplate, type);
        this.properties = properties;
        this.requirements = requirements;
    }

    @JsonProperty("properties")
    public PnfExtCpProperties getProperties() {
        return properties;
    }

    @JsonProperty("requirements")
    public PnfExtCpRequirements getRequirements() {
        return requirements;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.properties == null)
            throw new MalformattedElementException("PnfExtCp Node without properties");
        else
            this.properties.isValid();
        if (this.requirements == null)
            throw new MalformattedElementException("PnfExtCp Node without requirements");
        else
            this.requirements.isValid();
    }
}
