package it.nextworks.nfvmano.libs.descriptors.elements.lcm;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

@Embeddable
public class VnfOperationAdditionalParameters implements DescriptorInformationElement {

    private String parameter;

    public VnfOperationAdditionalParameters() {

    }

    public VnfOperationAdditionalParameters(String parameter) {
        this.parameter = parameter;
    }

    @JsonProperty("parameter")
    public String getParameter() {
        return parameter;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }

}
