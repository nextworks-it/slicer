package it.nextworks.nfvmano.libs.descriptors.elements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.pnfd.nodes.PNF.PNFProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LocationInfo implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private PNFProperties pnfProperties;

    private String countryCode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<CivicAddressElement> civicAddressElements = new ArrayList<>();

    public LocationInfo() {
    }

    public LocationInfo(PNFProperties pnfProperties, String countryCode, List<CivicAddressElement> civicAddressElements) {
        this.pnfProperties = pnfProperties;
        this.countryCode = countryCode;
        this.civicAddressElements = civicAddressElements;
    }

    public LocationInfo(String countryCode, List<CivicAddressElement> civicAddressElements) {
        this.countryCode = countryCode;
        this.civicAddressElements = civicAddressElements;
    }

    public Long getId() {
        return id;
    }

    public PNFProperties getPnfProperties() {
        return pnfProperties;
    }

    @JsonProperty("countryCode")
    public String getCountryCode() {
        return countryCode;
    }

    @JsonProperty("civicAddressElement")
    public List<CivicAddressElement> getCivicAddressElements() {
        return civicAddressElements;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.countryCode == null) {
            throw new MalformattedElementException("LocationInfo without countryCode");
        }
    }
}
