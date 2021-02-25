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
 * Three elements nsrName-ref, nsd-cp-ref, nsr-vld-nsd-ref
 */
@ApiModel(description = "Three elements nsrName-ref, nsd-cp-ref, nsr-vld-nsd-ref")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-24T10:30:36.627Z")

public class NSINsconnpointref {
    @JsonProperty("nsrName-ref")
    private String nsrNameRef = null;

    @JsonProperty("nsd-cp-ref")
    private String nsdCpRef = null;

    @JsonProperty("nsr-vld-nsd-ref")
    private String nsrVldNsdRef = null;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public NSINsconnpointref nsrNameRef(String nsrNameRef) {
        this.nsrNameRef = nsrNameRef;
        return this;
    }

    /**
     * Reference to one of the nsi.nsr-list.nsrName items.
     *
     * @return nsrNameRef
     **/
    @ApiModelProperty(value = "Reference to one of the nsi.nsr-list.nsrName items.")


    public String getNsrNameRef() {
        return nsrNameRef;
    }

    public void setNsrNameRef(String nsrNameRef) {
        this.nsrNameRef = nsrNameRef;
    }

    public NSINsconnpointref nsdCpRef(String nsdCpRef) {
        this.nsdCpRef = nsdCpRef;
        return this;
    }

    /**
     * Reference to the nsd.connection-point within the NSD where to connect the VLD.
     *
     * @return nsdCpRef
     **/
    @ApiModelProperty(value = "Reference to the nsd.connection-point within the NSD where to connect the VLD.")


    public String getNsdCpRef() {
        return nsdCpRef;
    }

    public void setNsdCpRef(String nsdCpRef) {
        this.nsdCpRef = nsdCpRef;
    }

    public NSINsconnpointref nsrVldNsdRef(String nsrVldNsdRef) {
        this.nsrVldNsdRef = nsrVldNsdRef;
        return this;
    }

    /**
     * Reference to the internal network service vld in the NSD.
     *
     * @return nsrVldNsdRef
     **/
    @ApiModelProperty(value = "Reference to the internal network service vld in the NSD.")


    public String getNsrVldNsdRef() {
        return nsrVldNsdRef;
    }

    public void setNsrVldNsdRef(String nsrVldNsdRef) {
        this.nsrVldNsdRef = nsrVldNsdRef;
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
        NSINsconnpointref nsINsconnpointref = (NSINsconnpointref) o;
        return Objects.equals(this.nsrNameRef, nsINsconnpointref.nsrNameRef) &&
                Objects.equals(this.nsdCpRef, nsINsconnpointref.nsdCpRef) &&
                Objects.equals(this.nsrVldNsdRef, nsINsconnpointref.nsrVldNsdRef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nsrNameRef, nsdCpRef, nsrVldNsdRef);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NSINsconnpointref {\n");

        sb.append("    nsrNameRef: ").append(toIndentedString(nsrNameRef)).append("\n");
        sb.append("    nsdCpRef: ").append(toIndentedString(nsdCpRef)).append("\n");
        sb.append("    nsrVldNsdRef: ").append(toIndentedString(nsrVldNsdRef)).append("\n");
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

