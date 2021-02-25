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
 * All the information parameters definning a netservice record.
 */
@ApiModel(description = "All the information parameters definning a netservice record.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-24T10:30:36.627Z")

public class NSIVld {
    @JsonProperty("vld-ref")
    private String vldRef = null;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public NSIVld vldRef(String vldRef) {
        this.vldRef = vldRef;
        return this;
    }

    /**
     * Name of the vld connected to the nsr.
     *
     * @return vldRef
     **/
    @ApiModelProperty(value = "Name of the vld connected to the nsr.")


    public String getVldRef() {
        return vldRef;
    }

    public void setVldRef(String vldRef) {
        this.vldRef = vldRef;
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
        NSIVld nsIVld = (NSIVld) o;
        return Objects.equals(this.vldRef, nsIVld.vldRef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vldRef);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NSIVld {\n");

        sb.append("    vldRef: ").append(toIndentedString(vldRef)).append("\n");
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

