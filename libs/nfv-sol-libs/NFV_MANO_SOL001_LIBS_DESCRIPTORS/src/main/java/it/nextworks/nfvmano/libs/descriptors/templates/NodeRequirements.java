package it.nextworks.nfvmano.libs.descriptors.templates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.xpath.internal.operations.String;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class NodeRequirements implements DescriptorInformationElement {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<String> virtualLink = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<String> virtualLinkBackend = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<String> virtualLinkService = new ArrayList<>();
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    @OneToOne
    @JsonIgnore
    private NodeType nodeType;

    public NodeRequirements() {
    }

    public NodeRequirements(NodeType nodeType, List<String> virtualLink, List<String> virtualLinkBackend, List<String> virtualLinkService) {
        this.nodeType = nodeType;
        this.virtualLink = virtualLink;
        this.virtualLinkBackend = virtualLinkBackend;
        this.virtualLinkService = virtualLinkService;
    }

    @JsonProperty("virtualLink")
    public List<String> getVirtualLink() {
        return virtualLink;
    }

    @JsonProperty("virtualLinkBackend")
    public List<String> getVirtualLinkBackend() {
        return virtualLinkBackend;
    }

    @JsonProperty("virtualLinkService")
    public List<String> getVirtualLinkService() {
        return virtualLinkService;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
