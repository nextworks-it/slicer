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

public class NSIVimAccountId {
    @JsonProperty("vim-id")
    private String vimId = null;

    @JsonProperty("net-created")
    private Boolean netCreated = null;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public NSIVimAccountId vimId(String vimId) {
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

    public NSIVimAccountId netCreated(Boolean netCreated) {
        this.netCreated = netCreated;
        return this;
    }

    /**
     * Defines wether the network is created or not in the vim (for multiple-vim deployment).
     *
     * @return netCreated
     **/
    @ApiModelProperty(value = "Defines wether the network is created or not in the vim (for multiple-vim deployment).")


    public Boolean isNetCreated() {
        return netCreated;
    }

    public void setNetCreated(Boolean netCreated) {
        this.netCreated = netCreated;
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
        NSIVimAccountId nsIVimAccountId = (NSIVimAccountId) o;
        return Objects.equals(this.vimId, nsIVimAccountId.vimId) &&
                Objects.equals(this.netCreated, nsIVimAccountId.netCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vimId, netCreated);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NSIVimAccountId {\n");

        sb.append("    vimId: ").append(toIndentedString(vimId)).append("\n");
        sb.append("    netCreated: ").append(toIndentedString(netCreated)).append("\n");
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

