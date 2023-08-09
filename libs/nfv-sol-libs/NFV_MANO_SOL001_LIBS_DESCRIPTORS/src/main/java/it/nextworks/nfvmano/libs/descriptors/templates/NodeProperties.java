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
import java.util.ArrayList;
import java.util.List;

@Entity
public class NodeProperties implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JsonIgnore
    private NodeType nodeType;

    private String type;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "nodeProperties", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<NodeConstraint> constraints = new ArrayList<>();
    private String defaultValue;

    public NodeProperties() {
    }

    public NodeProperties(NodeType nodeType, String type, List<NodeConstraint> constraints, String defaultValue) {
        this.nodeType = nodeType;
        this.type = type;
        this.constraints = constraints;
        this.defaultValue = defaultValue;
    }

    public Long getId() {
        return id;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("constraints")
    public List<NodeConstraint> getConstraints() {
        return constraints;
    }

    @JsonProperty("default")
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
