package it.nextworks.nfvmano.libs.descriptors.templates;

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
import java.util.List;

@Entity
public class SubstitutionMappingsRequirements implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    Long id;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<VirtualLinkPair> virtualLink = new ArrayList<>();
    @OneToOne
    @JsonIgnore
    private SubstitutionMappings subMapping;

    public SubstitutionMappingsRequirements() {
    }

    public SubstitutionMappingsRequirements(SubstitutionMappings subMapping, List<VirtualLinkPair> virtualLink) {
        this.subMapping = subMapping;
        this.virtualLink = virtualLink;
    }

    public Long getId() {
        return id;
    }

    public SubstitutionMappings getSubMapping() {
        return subMapping;
    }

    @JsonProperty("virtualLink")
    public List<VirtualLinkPair> getVirtualLink() {
        return virtualLink;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }

    public void setVirtualLink(List<VirtualLinkPair> virtualLink) {
        this.virtualLink = virtualLink;
    }
}
