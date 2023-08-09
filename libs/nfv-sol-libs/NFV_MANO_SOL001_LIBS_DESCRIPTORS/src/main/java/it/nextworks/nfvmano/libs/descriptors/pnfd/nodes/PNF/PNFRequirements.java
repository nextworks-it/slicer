package it.nextworks.nfvmano.libs.descriptors.pnfd.nodes.PNF;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class PNFRequirements implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private PNFNode pnfNode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<String, String> virtualLink = new HashMap<>();

    public PNFRequirements() {
    }

    public PNFRequirements(PNFNode pnfNode, Map<String,String> virtualLink) {
        this.pnfNode = pnfNode;
        this.virtualLink = virtualLink;
    }

    public PNFRequirements(Map<String,String> virtualLink) {
        this.virtualLink = virtualLink;
    }

    public Long getId() {
        return id;
    }

    public PNFNode getPnfNode() {
        return pnfNode;
    }

    @JsonProperty("virtualLink")
    public Map<String,String> getVirtualLink() {
        return virtualLink;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (virtualLink == null || virtualLink.isEmpty())
            throw new MalformattedElementException("PNF requirements without virtualLinks");
    }
}
