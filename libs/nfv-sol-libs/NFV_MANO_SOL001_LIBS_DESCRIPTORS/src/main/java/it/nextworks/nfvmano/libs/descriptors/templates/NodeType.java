package it.nextworks.nfvmano.libs.descriptors.templates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class NodeType implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JsonIgnore
    private DescriptorTemplate descriptorTemplate;

    private String derivedFrom;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "nodeType", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, NodeProperties> properties = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nodeType", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private NodeRequirements requirements;

    public NodeType() {
    }

    public NodeType(DescriptorTemplate descriptorTemplate, Map<String, NodeProperties> properties, NodeRequirements requirements) {
        this.descriptorTemplate = descriptorTemplate;
        this.properties = properties;
        this.requirements = requirements;
    }

    public Long getId() {
        return id;
    }

    public DescriptorTemplate getDescriptorTemplate() {
        return descriptorTemplate;
    }

    @JsonProperty("derivedFrom")
    public String getDerivedFrom() {
        return derivedFrom;
    }

    @JsonProperty("properties")
    public Map<String, NodeProperties> getProperties() {
        return properties;
    }

    @JsonProperty("requirements")
    public NodeRequirements getRequirements() {
        return requirements;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
