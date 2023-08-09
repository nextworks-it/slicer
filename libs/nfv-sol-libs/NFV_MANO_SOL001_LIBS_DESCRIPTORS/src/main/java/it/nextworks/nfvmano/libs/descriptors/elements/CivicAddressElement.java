package it.nextworks.nfvmano.libs.descriptors.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

@Embeddable
public class CivicAddressElement implements DescriptorInformationElement {

    private String caType;
    private String caValue;

    public CivicAddressElement() {
    }

    public CivicAddressElement(String caType, String caValue) {
        this.caType = caType;
        this.caValue = caValue;
    }

    @JsonProperty("caType")
    public String getCaType() {
        return caType;
    }

    @JsonProperty("caValue")
    public String getCaValue() {
        return caValue;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.caType == null || this.caValue == null) {
            throw new MalformattedElementException("CivicAddressType without caType and/or caValue");
        }
    }
}
