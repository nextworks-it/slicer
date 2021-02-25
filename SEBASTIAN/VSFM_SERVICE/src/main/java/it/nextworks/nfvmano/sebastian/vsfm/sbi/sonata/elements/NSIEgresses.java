package it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.elements;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Each element is composed by a pair parameters: location, nap.
 */
@ApiModel(description = "Each element is composed by a pair parameters: location, nap.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-24T10:30:36.627Z")

public class NSIEgresses {
    @JsonProperty("location")
    private String location = null;

    @JsonProperty("nap")
    private String nap = null;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public NSIEgresses location(String location) {
        this.location = location;
        return this;
    }

    /**
     * Identifier of egress location
     *
     * @return location
     **/
    @ApiModelProperty(value = "Identifier of egress location")

    @Pattern(regexp = "^[a-zA-Z0-9\\-_.]+$")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public NSIEgresses nap(String nap) {
        this.nap = nap;
        return this;
    }

    /**
     * IP range for the egress location
     *
     * @return nap
     **/
    @ApiModelProperty(value = "IP range for the egress location")


    public String getNap() {
        return nap;
    }

    public void setNap(String nap) {
        this.nap = nap;
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return otherProperties;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        otherProperties.put(name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NSIEgresses nsIEgresses = (NSIEgresses) o;
        return Objects.equals(this.location, nsIEgresses.location) &&
                Objects.equals(this.nap, nsIEgresses.nap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, nap);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NSIEgresses {\n");

        sb.append("    location: ").append(toIndentedString(location)).append("\n");
        sb.append("    nap: ").append(toIndentedString(nap)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

