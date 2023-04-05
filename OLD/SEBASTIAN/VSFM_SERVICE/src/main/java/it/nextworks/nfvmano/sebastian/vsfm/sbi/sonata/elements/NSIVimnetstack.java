package it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.elements;

import java.util.*;

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
 * A dict composed by id, vimAccountId (net_created, vim-id).
 */
@ApiModel(description = "A dict composed by id, vimAccountId (net_created, vim-id).")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-24T10:30:36.627Z")

public class NSIVimnetstack {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("vimAccountId")
    @Valid
    private List<NSIVimAccountId> vimAccountId = null;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public NSIVimnetstack id(String id) {
        this.id = id;
        return this;
    }

    /**
     * The uuid of the IA stack that manages the network within each fo the VIMs.
     *
     * @return id
     **/
    @ApiModelProperty(value = "The uuid of the IA stack that manages the network within each fo the VIMs.")

    @Pattern(regexp = "^[a-zA-Z0-9\\-_.]+$")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NSIVimnetstack vimAccountId(List<NSIVimAccountId> vimAccountId) {
        this.vimAccountId = vimAccountId;
        return this;
    }

    public NSIVimnetstack addVimAccountIdItem(NSIVimAccountId vimAccountIdItem) {
        if (this.vimAccountId == null) {
            this.vimAccountId = new ArrayList<NSIVimAccountId>();
        }
        this.vimAccountId.add(vimAccountIdItem);
        return this;
    }

    /**
     * List of VIMs and their IA stacks uuids for a better multi-deployment management
     *
     * @return vimAccountId
     **/
    @ApiModelProperty(value = "List of VIMs and their IA stacks uuids for a better multi-deployment management")

    @Valid

    public List<NSIVimAccountId> getVimAccountId() {
        return vimAccountId;
    }

    public void setVimAccountId(List<NSIVimAccountId> vimAccountId) {
        this.vimAccountId = vimAccountId;
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
        NSIVimnetstack nsIVimnetstack = (NSIVimnetstack) o;
        return Objects.equals(this.id, nsIVimnetstack.id) &&
                Objects.equals(this.vimAccountId, nsIVimnetstack.vimAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vimAccountId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NSIVimnetstack {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    vimAccountId: ").append(toIndentedString(vimAccountId)).append("\n");
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

