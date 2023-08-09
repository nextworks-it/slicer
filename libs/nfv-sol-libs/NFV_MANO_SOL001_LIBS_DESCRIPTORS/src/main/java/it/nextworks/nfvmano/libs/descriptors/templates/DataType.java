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
import java.util.HashMap;
import java.util.Map;

@Entity
public class DataType implements DescriptorInformationElement {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    Map<String, ParameterProperties> properties = new HashMap<>();
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    @ManyToOne
    @JsonIgnore
    private DescriptorTemplate descriptorTemplate;

    public DataType() {
    }

    public DataType(DescriptorTemplate descriptorTemplate, Map<String, ParameterProperties> properties) {
        this.descriptorTemplate = descriptorTemplate;
        this.properties = properties;
    }

    public Long getId() {
        return id;
    }

    public DescriptorTemplate getDescriptorTemplate() {
        return descriptorTemplate;
    }

    @JsonProperty("properties")
    public Map<String, ParameterProperties> getProperties() {
        return properties;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
