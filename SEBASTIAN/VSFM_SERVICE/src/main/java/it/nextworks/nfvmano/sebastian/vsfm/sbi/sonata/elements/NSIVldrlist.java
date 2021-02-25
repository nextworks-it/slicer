package it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.elements;

import java.util.*;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * A service uuid with related parameters (i.e slaId)
 */
@ApiModel(description = "A service uuid with related parameters (i.e slaId)")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-24T10:30:36.627Z")

public class NSIVldrlist {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("vim-net-stack")
    @Valid
    private List<NSIVimnetstack> vimNetStack = null;

    @JsonProperty("vim-net-id")
    private String vimNetId = null;

    @JsonProperty("mgmt-network")
    private Boolean mgmtNetwork = null;

    /**
     * Type of vld
     */
    public enum TypeEnum {
        LINE("E-LINE"),

        TREE("E-TREE"),

        LAN("E-LAN");

        private String value;

        TypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static TypeEnum fromValue(String text) {
            for (TypeEnum b : TypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("type")
    private TypeEnum type = null;

    /**
     * Status of the vld (vim-net) instantiation.
     */
    public enum VldStatusEnum {
        ACTIVE("ACTIVE"),

        INACTIVE("INACTIVE"),

        ERROR("ERROR");

        private String value;

        VldStatusEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static VldStatusEnum fromValue(String text) {
            for (VldStatusEnum b : VldStatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("vld-status")
    private VldStatusEnum vldStatus = null;

    @JsonProperty("access_net")
    private Boolean accessNet = null;

    @JsonProperty("shared-nsrs-list")
    @Valid
    private List<String> sharedNsrsList = null;

    @JsonProperty("ns-conn-point-ref")
    @Valid
    private List<NSINsconnpointref> nsConnPointRef = null;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public NSIVldrlist id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Id of the vld (should have the same value as the vld id in the nst).
     *
     * @return id
     **/
    @ApiModelProperty(required = true, value = "Id of the vld (should have the same value as the vld id in the nst).")
    @NotNull


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NSIVldrlist name(String name) {
        this.name = name;
        return this;
    }

    /**
     * VLD name
     *
     * @return name
     **/
    @ApiModelProperty(required = true, value = "VLD name")
    @NotNull


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NSIVldrlist vimNetStack(List<NSIVimnetstack> vimNetStack) {
        this.vimNetStack = vimNetStack;
        return this;
    }

    public NSIVldrlist addVimNetStackItem(NSIVimnetstack vimNetStackItem) {
        if (this.vimNetStack == null) {
            this.vimNetStack = new ArrayList<NSIVimnetstack>();
        }
        this.vimNetStack.add(vimNetStackItem);
        return this;
    }

    /**
     * List of network stacks to create the VIM networks though the IA component.
     *
     * @return vimNetStack
     **/
    @ApiModelProperty(value = "List of network stacks to create the VIM networks though the IA component.")

    @Valid

    public List<NSIVimnetstack> getVimNetStack() {
        return vimNetStack;
    }

    public void setVimNetStack(List<NSIVimnetstack> vimNetStack) {
        this.vimNetStack = vimNetStack;
    }

    public NSIVldrlist vimNetId(String vimNetId) {
        this.vimNetId = vimNetId;
        return this;
    }

    /**
     * VIM network uuid
     *
     * @return vimNetId
     **/
    @ApiModelProperty(value = "VIM network uuid")


    public String getVimNetId() {
        return vimNetId;
    }

    public void setVimNetId(String vimNetId) {
        this.vimNetId = vimNetId;
    }

    public NSIVldrlist mgmtNetwork(Boolean mgmtNetwork) {
        this.mgmtNetwork = mgmtNetwork;
        return this;
    }

    /**
     * Defines if the vld is of management or not
     *
     * @return mgmtNetwork
     **/
    @ApiModelProperty(value = "Defines if the vld is of management or not")


    public Boolean isMgmtNetwork() {
        return mgmtNetwork;
    }

    public void setMgmtNetwork(Boolean mgmtNetwork) {
        this.mgmtNetwork = mgmtNetwork;
    }

    public NSIVldrlist type(TypeEnum type) {
        this.type = type;
        return this;
    }

    /**
     * Type of vld
     *
     * @return type
     **/
    @ApiModelProperty(value = "Type of vld")


    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public NSIVldrlist vldStatus(VldStatusEnum vldStatus) {
        this.vldStatus = vldStatus;
        return this;
    }

    /**
     * Status of the vld (vim-net) instantiation.
     *
     * @return vldStatus
     **/
    @ApiModelProperty(value = "Status of the vld (vim-net) instantiation.")


    public VldStatusEnum getVldStatus() {
        return vldStatus;
    }

    public void setVldStatus(VldStatusEnum vldStatus) {
        this.vldStatus = vldStatus;
    }

    public NSIVldrlist accessNet(Boolean accessNet) {
        this.accessNet = accessNet;
        return this;
    }

    /**
     * Defines if the vld is external (true) and so, gets a floating IP or internal (false).
     *
     * @return accessNet
     **/
    @ApiModelProperty(value = "Defines if the vld is external (true) and so, gets a floating IP or internal (false).")


    public Boolean isAccessNet() {
        return accessNet;
    }

    public void setAccessNet(Boolean accessNet) {
        this.accessNet = accessNet;
    }

    public NSIVldrlist sharedNsrsList(List<String> sharedNsrsList) {
        this.sharedNsrsList = sharedNsrsList;
        return this;
    }

    public NSIVldrlist addSharedNsrsListItem(String sharedNsrsListItem) {
        if (this.sharedNsrsList == null) {
            this.sharedNsrsList = new ArrayList<String>();
        }
        this.sharedNsrsList.add(sharedNsrsListItem);
        return this;
    }

    /**
     * Uuid list of the shared nsr conected to this vld.
     *
     * @return sharedNsrsList
     **/
    @ApiModelProperty(value = "Uuid list of the shared nsr conected to this vld.")


    public List<String> getSharedNsrsList() {
        return sharedNsrsList;
    }

    public void setSharedNsrsList(List<String> sharedNsrsList) {
        this.sharedNsrsList = sharedNsrsList;
    }

    public NSIVldrlist nsConnPointRef(List<NSINsconnpointref> nsConnPointRef) {
        this.nsConnPointRef = nsConnPointRef;
        return this;
    }

    public NSIVldrlist addNsConnPointRefItem(NSINsconnpointref nsConnPointRefItem) {
        if (this.nsConnPointRef == null) {
            this.nsConnPointRef = new ArrayList<NSINsconnpointref>();
        }
        this.nsConnPointRef.add(nsConnPointRefItem);
        return this;
    }

    /**
     * List of the instances and their cps connected to each VLD.
     *
     * @return nsConnPointRef
     **/
    @ApiModelProperty(value = "List of the instances and their cps connected to each VLD.")

    @Valid

    public List<NSINsconnpointref> getNsConnPointRef() {
        return nsConnPointRef;
    }

    public void setNsConnPointRef(List<NSINsconnpointref> nsConnPointRef) {
        this.nsConnPointRef = nsConnPointRef;
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
        NSIVldrlist nsIVldrlist = (NSIVldrlist) o;
        return Objects.equals(this.id, nsIVldrlist.id) &&
                Objects.equals(this.name, nsIVldrlist.name) &&
                Objects.equals(this.vimNetStack, nsIVldrlist.vimNetStack) &&
                Objects.equals(this.vimNetId, nsIVldrlist.vimNetId) &&
                Objects.equals(this.mgmtNetwork, nsIVldrlist.mgmtNetwork) &&
                Objects.equals(this.type, nsIVldrlist.type) &&
                Objects.equals(this.vldStatus, nsIVldrlist.vldStatus) &&
                Objects.equals(this.accessNet, nsIVldrlist.accessNet) &&
                Objects.equals(this.sharedNsrsList, nsIVldrlist.sharedNsrsList) &&
                Objects.equals(this.nsConnPointRef, nsIVldrlist.nsConnPointRef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, vimNetStack, vimNetId, mgmtNetwork, type, vldStatus, accessNet, sharedNsrsList, nsConnPointRef);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NSIVldrlist {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    vimNetStack: ").append(toIndentedString(vimNetStack)).append("\n");
        sb.append("    vimNetId: ").append(toIndentedString(vimNetId)).append("\n");
        sb.append("    mgmtNetwork: ").append(toIndentedString(mgmtNetwork)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    vldStatus: ").append(toIndentedString(vldStatus)).append("\n");
        sb.append("    accessNet: ").append(toIndentedString(accessNet)).append("\n");
        sb.append("    sharedNsrsList: ").append(toIndentedString(sharedNsrsList)).append("\n");
        sb.append("    nsConnPointRef: ").append(toIndentedString(nsConnPointRef)).append("\n");
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

