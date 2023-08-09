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
public class NodeConstraint implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JsonIgnore
    private NodeProperties nodeProperties;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> validValues = new ArrayList<>();

    public NodeConstraint() {
    }

    public NodeConstraint(NodeProperties nodeProperties, List<String> validValues) {
        this.nodeProperties = nodeProperties;
        this.validValues = validValues;
    }

    public Long getId() {
        return id;
    }

    public NodeProperties getNodeProperties() {
        return nodeProperties;
    }

    @JsonProperty("validValues")
    public List<String> getValidValues() {
        return validValues;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
