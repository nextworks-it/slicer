package it.nextworks.nfvmano.libs.descriptors.pnfd.nodes.PnfExtCp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VnfExtCp.VnfExtCpNode;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PnfExtCpRequirements implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private PnfExtCpNode pnfExtCpNode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> externalVirtualLink = new ArrayList<>();

    public PnfExtCpRequirements() {

    }

    public PnfExtCpRequirements(PnfExtCpNode pnfExtCpNode, List<String> externalVirtualLink) {
        this.pnfExtCpNode = pnfExtCpNode;
        this.externalVirtualLink = externalVirtualLink;
    }

    public PnfExtCpRequirements(List<String> externalVirtualLink) {
        this.externalVirtualLink = externalVirtualLink;
    }

    public Long getId() {
        return id;
    }

    public PnfExtCpNode getPnfExtCpNode() {
        return pnfExtCpNode;
    }

    @JsonProperty("externalVirtualLink")
    public List<String> getExternalVirtualLink() {
        return externalVirtualLink;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
