package it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.elements;

import java.util.*;

import com.fasterxml.jackson.annotation.*;
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

public class NSINsrlist {
    @JsonProperty("nsrName")
    private String nsrName = null;

    @JsonProperty("nsrId")
    private String nsrId = null;

    @JsonProperty("subnet-ref")
    private String subnetRef = null;

    @JsonProperty("subnet-nsdId-ref")
    private String subnetNsdIdRef = null;

    /**
     * Status of the service instantiation.
     */
    public enum WorkingStatusEnum {
        NEW("NEW"),

        INSTANTIATING("INSTANTIATING"),

        INSTANTIATED("INSTANTIATED"),

        TERMINATING("TERMINATING"),

        TERMINATED("TERMINATED"),

        READY("READY"),

        ERROR("ERROR"),

        NOT_INSTANTIATED("NOT_INSTANTIATED");

        private String value;

        WorkingStatusEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static WorkingStatusEnum fromValue(String text) {
            for (WorkingStatusEnum b : WorkingStatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("working-status")
    private WorkingStatusEnum workingStatus = null;

    @JsonProperty("requestId")
    private String requestId = null;

    @JsonProperty("nsr-placement")
    @Valid
    private List<NSINsrplacement> nsrPlacement = null;

    @JsonProperty("isshared")
    private Boolean isshared = null;

    @JsonProperty("sla-name")
    private String slaName = null;

    @JsonProperty("sla-ref")
    private String slaRef = null;

    @JsonProperty("ingresses")
    @Valid
    private List<NSIIngresses> ingresses = null;

    @JsonProperty("egresses")
    @Valid
    private List<NSIEgresses> egresses = null;

    @JsonProperty("vld")
    @Valid
    private List<NSIVld> vld = null;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public NSINsrlist nsrName(String nsrName) {
        this.nsrName = nsrName;
        return this;
    }

    /**
     * Name of the instantiation composed by the <slice_name-subnet_name-index>
     *
     * @return nsrName
     **/
    @ApiModelProperty(value = "Name of the instantiation composed by the <slice_name-subnet_name-index>")


    public String getNsrName() {
        return nsrName;
    }

    public void setNsrName(String nsrName) {
        this.nsrName = nsrName;
    }

    public NSINsrlist nsrId(String nsrId) {
        this.nsrId = nsrId;
        return this;
    }

    /**
     * UUID of a network service record (instance).
     *
     * @return nsrId
     **/
    @ApiModelProperty(value = "UUID of a network service record (instance).")

    @Pattern(regexp = "^[a-zA-Z0-9\\-_.]+$")
    public String getNsrId() {
        return nsrId;
    }

    public void setNsrId(String nsrId) {
        this.nsrId = nsrId;
    }

    public NSINsrlist subnetRef(String subnetRef) {
        this.subnetRef = subnetRef;
        return this;
    }

    /**
     * References to the ID of the slice-subnet in NST.slice_ns_subnets list.
     *
     * @return subnetRef
     **/
    @ApiModelProperty(value = "References to the ID of the slice-subnet in NST.slice_ns_subnets list.")


    public String getSubnetRef() {
        return subnetRef;
    }

    public void setSubnetRef(String subnetRef) {
        this.subnetRef = subnetRef;
    }

    public NSINsrlist subnetNsdIdRef(String subnetNsdIdRef) {
        this.subnetNsdIdRef = subnetNsdIdRef;
        return this;
    }

    /**
     * NSD uuid which this instance is based on.
     *
     * @return subnetNsdIdRef
     **/
    @ApiModelProperty(value = "NSD uuid which this instance is based on.")

    @Pattern(regexp = "^[a-zA-Z0-9\\-_.]+$")
    public String getSubnetNsdIdRef() {
        return subnetNsdIdRef;
    }

    public void setSubnetNsdIdRef(String subnetNsdIdRef) {
        this.subnetNsdIdRef = subnetNsdIdRef;
    }

    public NSINsrlist workingStatus(WorkingStatusEnum workingStatus) {
        this.workingStatus = workingStatus;
        return this;
    }

    /**
     * Status of the service instantiation.
     *
     * @return workingStatus
     **/
    @ApiModelProperty(value = "Status of the service instantiation.")


    public WorkingStatusEnum getWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(WorkingStatusEnum workingStatus) {
        this.workingStatus = workingStatus;
    }

    public NSINsrlist requestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    /**
     * Last request (create/terminate) action uuid done by GTK related to this service.
     *
     * @return requestId
     **/
    @ApiModelProperty(value = "Last request (create/terminate) action uuid done by GTK related to this service.")

    @Pattern(regexp = "^[a-zA-Z0-9\\-_.]+$")
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public NSINsrlist nsrPlacement(List<NSINsrplacement> nsrPlacement) {
        this.nsrPlacement = nsrPlacement;
        return this;
    }

    public NSINsrlist addNsrPlacementItem(NSINsrplacement nsrPlacementItem) {
        if (this.nsrPlacement == null) {
            this.nsrPlacement = new ArrayList<NSINsrplacement>();
        }
        this.nsrPlacement.add(nsrPlacementItem);
        return this;
    }

    /**
     * List of NSD components and VIMs where they are deployed
     *
     * @return nsrPlacement
     **/
    @ApiModelProperty(value = "List of NSD components and VIMs where they are deployed")

    @Valid

    public List<NSINsrplacement> getNsrPlacement() {
        return nsrPlacement;
    }

    public void setNsrPlacement(List<NSINsrplacement> nsrPlacement) {
        this.nsrPlacement = nsrPlacement;
    }

    public NSINsrlist isshared(Boolean isshared) {
        this.isshared = isshared;
        return this;
    }

    /**
     * Boolean definning if a service instance is shared.
     *
     * @return isshared
     **/
    @ApiModelProperty(value = "Boolean definning if a service instance is shared.")


    public Boolean isIsshared() {
        return isshared;
    }

    public void setIsshared(Boolean isshared) {
        this.isshared = isshared;
    }

    public NSINsrlist slaName(String slaName) {
        this.slaName = slaName;
        return this;
    }

    /**
     * Associated SLA name (from NST unless user changes it with the instantiation parameters)
     *
     * @return slaName
     **/
    @ApiModelProperty(value = "Associated SLA name (from NST unless user changes it with the instantiation parameters)")


    public String getSlaName() {
        return slaName;
    }

    public void setSlaName(String slaName) {
        this.slaName = slaName;
    }

    public NSINsrlist slaRef(String slaRef) {
        this.slaRef = slaRef;
        return this;
    }

    /**
     * Associated SLA Uuid (from NST unless user changes it with the instantiation parameters)
     *
     * @return slaRef
     **/
    @ApiModelProperty(value = "Associated SLA Uuid (from NST unless user changes it with the instantiation parameters)")

    @Pattern(regexp = "^[a-zA-Z0-9\\-_.]+$")
    public String getSlaRef() {
        return slaRef;
    }

    public void setSlaRef(String slaRef) {
        this.slaRef = slaRef;
    }

    public NSINsrlist ingresses(List<NSIIngresses> ingresses) {
        this.ingresses = ingresses;
        return this;
    }

    public NSINsrlist addIngressesItem(NSIIngresses ingressesItem) {
        if (this.ingresses == null) {
            this.ingresses = new ArrayList<NSIIngresses>();
        }
        this.ingresses.add(ingressesItem);
        return this;
    }

    /**
     * List of ingress values
     *
     * @return ingresses
     **/
    @ApiModelProperty(value = "List of ingress values")

    @Valid

    public List<NSIIngresses> getIngresses() {
        return ingresses;
    }

    public void setIngresses(List<NSIIngresses> ingresses) {
        this.ingresses = ingresses;
    }

    public NSINsrlist egresses(List<NSIEgresses> egresses) {
        this.egresses = egresses;
        return this;
    }

    public NSINsrlist addEgressesItem(NSIEgresses egressesItem) {
        if (this.egresses == null) {
            this.egresses = new ArrayList<NSIEgresses>();
        }
        this.egresses.add(egressesItem);
        return this;
    }

    /**
     * List of egress values
     *
     * @return egresses
     **/
    @ApiModelProperty(value = "List of egress values")

    @Valid

    public List<NSIEgresses> getEgresses() {
        return egresses;
    }

    public void setEgresses(List<NSIEgresses> egresses) {
        this.egresses = egresses;
    }

    public NSINsrlist vld(List<NSIVld> vld) {
        this.vld = vld;
        return this;
    }

    public NSINsrlist addVldItem(NSIVld vldItem) {
        if (this.vld == null) {
            this.vld = new ArrayList<NSIVld>();
        }
        this.vld.add(vldItem);
        return this;
    }

    /**
     * The list of vld names where the nsr is conencted.
     *
     * @return vld
     **/
    @ApiModelProperty(value = "The list of vld names where the nsr is conencted.")

    @Valid

    public List<NSIVld> getVld() {
        return vld;
    }

    public void setVld(List<NSIVld> vld) {
        this.vld = vld;
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
        NSINsrlist nsINsrlist = (NSINsrlist) o;
        return Objects.equals(this.nsrName, nsINsrlist.nsrName) &&
                Objects.equals(this.nsrId, nsINsrlist.nsrId) &&
                Objects.equals(this.subnetRef, nsINsrlist.subnetRef) &&
                Objects.equals(this.subnetNsdIdRef, nsINsrlist.subnetNsdIdRef) &&
                Objects.equals(this.workingStatus, nsINsrlist.workingStatus) &&
                Objects.equals(this.requestId, nsINsrlist.requestId) &&
                Objects.equals(this.nsrPlacement, nsINsrlist.nsrPlacement) &&
                Objects.equals(this.isshared, nsINsrlist.isshared) &&
                Objects.equals(this.slaName, nsINsrlist.slaName) &&
                Objects.equals(this.slaRef, nsINsrlist.slaRef) &&
                Objects.equals(this.ingresses, nsINsrlist.ingresses) &&
                Objects.equals(this.egresses, nsINsrlist.egresses) &&
                Objects.equals(this.vld, nsINsrlist.vld);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nsrName, nsrId, subnetRef, subnetNsdIdRef, workingStatus, requestId, nsrPlacement, isshared, slaName, slaRef, ingresses, egresses, vld);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NSINsrlist {\n");

        sb.append("    nsrName: ").append(toIndentedString(nsrName)).append("\n");
        sb.append("    nsrId: ").append(toIndentedString(nsrId)).append("\n");
        sb.append("    subnetRef: ").append(toIndentedString(subnetRef)).append("\n");
        sb.append("    subnetNsdIdRef: ").append(toIndentedString(subnetNsdIdRef)).append("\n");
        sb.append("    workingStatus: ").append(toIndentedString(workingStatus)).append("\n");
        sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
        sb.append("    nsrPlacement: ").append(toIndentedString(nsrPlacement)).append("\n");
        sb.append("    isshared: ").append(toIndentedString(isshared)).append("\n");
        sb.append("    slaName: ").append(toIndentedString(slaName)).append("\n");
        sb.append("    slaRef: ").append(toIndentedString(slaRef)).append("\n");
        sb.append("    ingresses: ").append(toIndentedString(ingresses)).append("\n");
        sb.append("    egresses: ").append(toIndentedString(egresses)).append("\n");
        sb.append("    vld: ").append(toIndentedString(vld)).append("\n");
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

