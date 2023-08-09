package it.nextworks.nfvmano.libs.descriptors.groups;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

@Embeddable
public class PlacementGroupProperties implements DescriptorInformationElement {

    private String description;

    public PlacementGroupProperties() {
    }

    public PlacementGroupProperties(String description) {
        this.description = description;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.description == null)
            throw new MalformattedElementException("PlacementGroupProperties without description");
    }
}
