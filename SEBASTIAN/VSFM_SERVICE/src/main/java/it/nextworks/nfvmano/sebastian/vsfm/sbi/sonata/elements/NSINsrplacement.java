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
 * A dict composed by nsd-comp-ref and vim-id.
 */
@ApiModel(description = "A dict composed by nsd-comp-ref and vim-id.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-24T10:30:36.627Z")

public class NSINsrplacement {
    @JsonProperty("nsd-comp-ref")
    private String nsdCompRef = null;

    @JsonProperty("vim-id")
    private String vimId = null;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public NSINsrplacement nsdCompRef(String nsdCompRef) {
        this.nsdCompRef = nsdCompRef;
        return this;
    }

    /**
     * Id ref of the NSD component (either VNF or CNF).
     *
     * @return nsdCompRef
     **/
    @ApiModelProperty(value = "Id ref of the NSD component (either VNF or CNF).")


    public String getNsdCompRef() {
        return nsdCompRef;
    }

    public void setNsdCompRef(String nsdCompRef) {
        this.nsdCompRef = nsdCompRef;
    }

    public NSINsrplacement vimId(String vimId) {
        this.vimId = vimId;
        return this;
    }

    /**
     * VIM id where the NSD component is placed.
     *
     * @return vimId
     **/
    @ApiModelProperty(value = "VIM id where the NSD component is placed.")

    @Pattern(regexp = "^[a-zA-Z0-9\\-_.]+$")
    public String getVimId() {
        return vimId;
    }

    public void setVimId(String vimId) {
        this.vimId = vimId;
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
        NSINsrplacement nsINsrplacement = (NSINsrplacement) o;
        return Objects.equals(this.nsdCompRef, nsINsrplacement.nsdCompRef) &&
                Objects.equals(this.vimId, nsINsrplacement.vimId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nsdCompRef, vimId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NSINsrplacement {\n");

        sb.append("    nsdCompRef: ").append(toIndentedString(nsdCompRef)).append("\n");
        sb.append("    vimId: ").append(toIndentedString(vimId)).append("\n");
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

