package it.nextworks.nfvmano.libs.descriptors.templates;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

@Embeddable
public class ParameterProperties implements DescriptorInformationElement {

    private String type;
    private boolean required;
    private String defaultValue;

    public ParameterProperties() {
    }

    public ParameterProperties(String type, boolean required, String defaultValue) {
        this.type = type;
        this.required = required;
        this.defaultValue = defaultValue;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("required")
    public boolean isRequired() {
        return required;
    }

    @JsonProperty("default")
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
