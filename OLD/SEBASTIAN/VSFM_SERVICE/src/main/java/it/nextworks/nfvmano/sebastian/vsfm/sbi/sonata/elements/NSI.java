package it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.elements;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Results of the query (NsiInstance).
 */
@ApiModel(description = "Results of the query (NsiInstance).")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-24T10:30:36.627Z")

public class NSI {
    @JsonProperty("uuid")
    private String id = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("vendor")
    private String vendor = null;

    @JsonProperty("nst-ref")
    private String nstRef = null;

    @JsonProperty("nst-name")
    private String nstName = null;

    @JsonProperty("nst-version")
    private String nstVersion = null;

    @JsonProperty("created_at")
    private String createdAt;

    /**
     * State of the NSI (possible values INSTANTIATED, TERMINATED, ERROR).
     */
    public enum NsiStatusEnum {
        INSTANTIATING("INSTANTIATING"),

        INSTANTIATED("INSTANTIATED"),

        TERMINATING("TERMINATING"),

        TERMINATED("TERMINATED"),

        READY("READY"),

        ERROR("ERROR");

        private String value;

        NsiStatusEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static NsiStatusEnum fromValue(String text) {
            for (NsiStatusEnum b : NsiStatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("nsi-status")
    private NsiStatusEnum nsiStatus = null;

    @JsonProperty("errorLog")
    private String errorLog = null;

    @JsonProperty("datacenter")
    @Valid
    private List<String> datacenter = null;

    @JsonProperty("instantiateTime")
    private String instantiateTime = null;

    @JsonProperty("terminateTime")
    private String terminateTime = null;

    @JsonProperty("scaleTime")
    private String scaleTime = null;

    @JsonProperty("updateTime")
    private String updateTime = null;

    @JsonProperty("updated_at")
    private String updatedAt = null;

    @JsonProperty("sliceCallback")
    private String sliceCallback = null;

    @JsonProperty("nsr-list")
    @Valid
    private List<NSINsrlist> nsrList = null;

    @JsonProperty("vldr-list")
    @Valid
    private List<NSIVldrlist> vldrList = null;

    @JsonProperty("_wim-connections")
    @Valid
    private List<Object> wimConnections = null;

    @JsonProperty("_instantiation_params")
    @Valid
    private List<Object> instantiationParams = null;

    public NSI id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Identifies the Network Slice Instance (NSI)
     *
     * @return id
     **/
    @ApiModelProperty(value = "Identifies the Network Slice Instance (NSI)")

    @Pattern(regexp = "^[a-zA-Z0-9\\-_.]+$")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NSI name(String name) {
        this.name = name;
        return this;
    }

    /**
     * The name of the NSI
     *
     * @return name
     **/
    @ApiModelProperty(value = "The name of the NSI")


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NSI description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Description of the NSI functionality
     *
     * @return description
     **/
    @ApiModelProperty(value = "Description of the NSI functionality")


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NSI vendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    /**
     * Description of the NST vendor
     *
     * @return vendor
     **/
    @ApiModelProperty(value = "Description of the NST vendor")


    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public NSI nstRef(String nstRef) {
        this.nstRef = nstRef;
        return this;
    }

    /**
     * The uuid of the NST used to instantiate the NSI.
     *
     * @return nstRef
     **/
    @ApiModelProperty(value = "The uuid of the NST used to instantiate the NSI.")

    @Pattern(regexp = "^[a-zA-Z0-9\\-_.]+$")
    public String getNstRef() {
        return nstRef;
    }

    public void setNstRef(String nstRef) {
        this.nstRef = nstRef;
    }

    public NSI nstName(String nstName) {
        this.nstName = nstName;
        return this;
    }

    /**
     * The name of the NST used to instantiate the NSI.
     *
     * @return nstName
     **/
    @ApiModelProperty(value = "The name of the NST used to instantiate the NSI.")


    public String getNstName() {
        return nstName;
    }

    public void setNstName(String nstName) {
        this.nstName = nstName;
    }

    public NSI nstVersion(String nstVersion) {
        this.nstVersion = nstVersion;
        return this;
    }

    /**
     * The version of the NetSlice Template the instance is based on.
     *
     * @return nstVersion
     **/
    @ApiModelProperty(value = "The version of the NetSlice Template the instance is based on.")

    @Pattern(regexp = "^[a-zA-Z0-9\\-_.]+$")
    public String getNstVersion() {
        return nstVersion;
    }

    public void setNstVersion(String nstVersion) {
        this.nstVersion = nstVersion;
    }

    public NSI createdAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Creation Time of the NSI.
     *
     * @return createdAt
     **/
    @ApiModelProperty(value = "Creation Time of the NSI.")


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public NSI updatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    /**
     * Update Time of the NSI.
     *
     * @return updatedAt
     **/
    @ApiModelProperty(value = "Update Time of the NSI.")


    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public NSI nsiStatus(NsiStatusEnum nsiStatus) {
        this.nsiStatus = nsiStatus;
        return this;
    }

    /**
     * State of the NSI (possible values INSTANTIATED, TERMINATED, ERROR).
     *
     * @return nsiStatus
     **/
    @ApiModelProperty(value = "State of the NSI (possible values INSTANTIATED, TERMINATED, ERROR).")


    public NsiStatusEnum getNsiStatus() {
        return nsiStatus;
    }

    public void setNsiStatus(NsiStatusEnum nsiStatus) {
        this.nsiStatus = nsiStatus;
    }

    public NSI errorLog(String errorLog) {
        this.errorLog = errorLog;
        return this;
    }

    /**
     * Internal log msg with the error desription if status is ERROR.
     *
     * @return errorLog
     **/
    @ApiModelProperty(value = "Internal log msg with the error desription if status is ERROR.")


    public String getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
    }

    public NSI datacenter(List<String> datacenter) {
        this.datacenter = datacenter;
        return this;
    }

    public NSI addDatacenterItem(String datacenterItem) {
        if (this.datacenter == null) {
            this.datacenter = new ArrayList<String>();
        }
        this.datacenter.add(datacenterItem);
        return this;
    }

    /**
     * List of VIms where the diferent NSs composing the slice are deployed.
     *
     * @return datacenter
     **/
    @ApiModelProperty(value = "List of VIms where the diferent NSs composing the slice are deployed.")


    public List<String> getDatacenter() {
        return datacenter;
    }

    public void setDatacenter(List<String> datacenter) {
        this.datacenter = datacenter;
    }

    public NSI instantiateTime(String instantiateTime) {
        this.instantiateTime = instantiateTime;
        return this;
    }

    /**
     * Timestamp indicating when the instantiation was trigerred.
     *
     * @return instantiateTime
     **/
    @ApiModelProperty(value = "Timestamp indicating when the instantiation was trigerred.")


    public String getInstantiateTime() {
        return instantiateTime;
    }

    public void setInstantiateTime(String instantiateTime) {
        this.instantiateTime = instantiateTime;
    }

    public NSI terminateTime(String terminateTime) {
        this.terminateTime = terminateTime;
        return this;
    }

    /**
     * Timestamp indicating when the termination will be tigerred (0 means immediatly).
     *
     * @return terminateTime
     **/
    @ApiModelProperty(value = "Timestamp indicating when the termination will be tigerred (0 means immediatly).")


    public String getTerminateTime() {
        return terminateTime;
    }

    public void setTerminateTime(String terminateTime) {
        this.terminateTime = terminateTime;
    }

    public NSI scaleTime(String scaleTime) {
        this.scaleTime = scaleTime;
        return this;
    }

    /**
     * Timestamp indicating when the scale is tigerred.
     *
     * @return scaleTime
     **/
    @ApiModelProperty(value = "Timestamp indicating when the scale is tigerred.")


    public String getScaleTime() {
        return scaleTime;
    }

    public void setScaleTime(String scaleTime) {
        this.scaleTime = scaleTime;
    }

    public NSI updateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    /**
     * Timestamp indicating when the update is tigerred.
     *
     * @return updateTime
     **/
    @ApiModelProperty(value = "Timestamp indicating when the update is tigerred.")


    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public NSI sliceCallback(String sliceCallback) {
        this.sliceCallback = sliceCallback;
        return this;
    }

    /**
     * Contains the URL to call back the GK when slice instantiation/termination processes are done.
     *
     * @return sliceCallback
     **/
    @ApiModelProperty(value = "Contains the URL to call back the GK when slice instantiation/termination processes are done.")


    public String getSliceCallback() {
        return sliceCallback;
    }

    public void setSliceCallback(String sliceCallback) {
        this.sliceCallback = sliceCallback;
    }

    public NSI nsrList(List<NSINsrlist> nsrList) {
        this.nsrList = nsrList;
        return this;
    }

    public NSI addNsrListItem(NSINsrlist nsrListItem) {
        if (this.nsrList == null) {
            this.nsrList = new ArrayList<NSINsrlist>();
        }
        this.nsrList.add(nsrListItem);
        return this;
    }

    /**
     * Network Services records list generated when the NSI is requested.
     *
     * @return nsrList
     **/
    @ApiModelProperty(value = "Network Services records list generated when the NSI is requested.")

    @Valid
    @Size(min = 1)
    public List<NSINsrlist> getNsrList() {
        return nsrList;
    }

    public void setNsrList(List<NSINsrlist> nsrList) {
        this.nsrList = nsrList;
    }

    public NSI vldrList(List<NSIVldrlist> vldrList) {
        this.vldrList = vldrList;
        return this;
    }

    public NSI addVldrListItem(NSIVldrlist vldrListItem) {
        if (this.vldrList == null) {
            this.vldrList = new ArrayList<NSIVldrlist>();
        }
        this.vldrList.add(vldrListItem);
        return this;
    }

    /**
     * The list of VLD uuids generated due to the instantiation of the selected NST.
     *
     * @return vldrList
     **/
    @ApiModelProperty(value = "The list of VLD uuids generated due to the instantiation of the selected NST.")

    @Valid

    public List<NSIVldrlist> getVldrList() {
        return vldrList;
    }

    public void setVldrList(List<NSIVldrlist> vldrList) {
        this.vldrList = vldrList;
    }

    public NSI wimConnections(List<Object> wimConnections) {
        this.wimConnections = wimConnections;
        return this;
    }

    public NSI addWimConnectionsItem(Object wimConnectionsItem) {
        if (this.wimConnections == null) {
            this.wimConnections = new ArrayList<Object>();
        }
        this.wimConnections.add(wimConnectionsItem);
        return this;
    }

    /**
     * List of connections done to interconnect VIM belonging to the same WIM.
     *
     * @return wimConnections
     **/
    @ApiModelProperty(value = "List of connections done to interconnect VIM belonging to the same WIM.")


    public List<Object> getWimConnections() {
        return wimConnections;
    }

    public void setWimConnections(List<Object> wimConnections) {
        this.wimConnections = wimConnections;
    }

    public NSI instantiationParams(List<Object> instantiationParams) {
        this.instantiationParams = instantiationParams;
        return this;
    }

    public NSI addInstantiationParamsItem(Object instantiationParamsItem) {
        if (this.instantiationParams == null) {
            this.instantiationParams = new ArrayList<Object>();
        }
        this.instantiationParams.add(instantiationParamsItem);
        return this;
    }

    /**
     * List of instantiation parameters for each of the subnets (NS) composing the slice.
     *
     * @return instantiationParams
     **/
    @ApiModelProperty(value = "List of instantiation parameters for each of the subnets (NS) composing the slice.")


    public List<Object> getInstantiationParams() {
        return instantiationParams;
    }

    public void setInstantiationParams(List<Object> instantiationParams) {
        this.instantiationParams = instantiationParams;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NSI NSI = (NSI) o;
        return Objects.equals(this.id, NSI.id) &&
                Objects.equals(this.name, NSI.name) &&
                Objects.equals(this.description, NSI.description) &&
                Objects.equals(this.vendor, NSI.vendor) &&
                Objects.equals(this.nstRef, NSI.nstRef) &&
                Objects.equals(this.nstName, NSI.nstName) &&
                Objects.equals(this.nstVersion, NSI.nstVersion) &&
                Objects.equals(this.createdAt, NSI.createdAt) &&
                Objects.equals(this.nsiStatus, NSI.nsiStatus) &&
                Objects.equals(this.errorLog, NSI.errorLog) &&
                Objects.equals(this.datacenter, NSI.datacenter) &&
                Objects.equals(this.instantiateTime, NSI.instantiateTime) &&
                Objects.equals(this.terminateTime, NSI.terminateTime) &&
                Objects.equals(this.scaleTime, NSI.scaleTime) &&
                Objects.equals(this.updateTime, NSI.updateTime) &&
                Objects.equals(this.updatedAt, NSI.updatedAt) &&
                Objects.equals(this.sliceCallback, NSI.sliceCallback) &&
                Objects.equals(this.nsrList, NSI.nsrList) &&
                Objects.equals(this.vldrList, NSI.vldrList) &&
                Objects.equals(this.wimConnections, NSI.wimConnections) &&
                Objects.equals(this.instantiationParams, NSI.instantiationParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, vendor, nstRef, nstName, nstVersion, createdAt, nsiStatus, errorLog, datacenter, instantiateTime, terminateTime, scaleTime, updateTime, updatedAt, sliceCallback, nsrList, vldrList, wimConnections, instantiationParams);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NSI {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    vendor: ").append(toIndentedString(vendor)).append("\n");
        sb.append("    nstRef: ").append(toIndentedString(nstRef)).append("\n");
        sb.append("    nstName: ").append(toIndentedString(nstName)).append("\n");
        sb.append("    nstVersion: ").append(toIndentedString(nstVersion)).append("\n");
        sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
        sb.append("    nsiStatus: ").append(toIndentedString(nsiStatus)).append("\n");
        sb.append("    errorLog: ").append(toIndentedString(errorLog)).append("\n");
        sb.append("    datacenter: ").append(toIndentedString(datacenter)).append("\n");
        sb.append("    instantiateTime: ").append(toIndentedString(instantiateTime)).append("\n");
        sb.append("    terminateTime: ").append(toIndentedString(terminateTime)).append("\n");
        sb.append("    scaleTime: ").append(toIndentedString(scaleTime)).append("\n");
        sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
        sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
        sb.append("    sliceCallback: ").append(toIndentedString(sliceCallback)).append("\n");
        sb.append("    nsrList: ").append(toIndentedString(nsrList)).append("\n");
        sb.append("    vldrList: ").append(toIndentedString(vldrList)).append("\n");
        sb.append("    wimConnections: ").append(toIndentedString(wimConnections)).append("\n");
        sb.append("    instantiationParams: ").append(toIndentedString(instantiationParams)).append("\n");
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

