package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.RelationalOperationTypeEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class ScalingCriteria {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("scale-in-threshold")
    private Double scaleInThreshold = null;

    @JsonProperty("scale-in-relational-operation")
    private RelationalOperationTypeEnum scaleInRelationalOperation = null;

    @JsonProperty("scale-out-threshold")
    private Double scaleOutThreshold = null;

    @JsonProperty("scale-out-relational-operation")
    private RelationalOperationTypeEnum scaleOutRelationalOperation = null;

    @JsonProperty("vnf-monitoring-param-ref")
    private String vnfMonitoringParamRef = null;

    public ScalingCriteria name(String name) {
        this.name = name;
        return this;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public ScalingCriteria scaleInThreshold(Double scaleInThreshold) {
        this.scaleInThreshold = scaleInThreshold;
        return this;
    }

    public Double getScaleInThreshold() { return scaleInThreshold; }

    public void setScaleInThreshold(Double scaleInThreshold) { this.scaleInThreshold = scaleInThreshold; }

    public ScalingCriteria scaleInRelationalOperation(RelationalOperationTypeEnum scaleInRelationalOperation) {
        this.scaleInRelationalOperation = scaleInRelationalOperation;
        return this;
    }

    public RelationalOperationTypeEnum getScaleInRelationalOperation() { return scaleInRelationalOperation; }

    public void setScaleInRelationalOperation(RelationalOperationTypeEnum scaleInRelationalOperation) {
        this.scaleInRelationalOperation = scaleInRelationalOperation;
    }

    public ScalingCriteria scaleOutThreshold(Double scaleOutThreshold) {
        this.scaleOutThreshold = scaleOutThreshold;
        return this;
    }

    public Double getScaleOutThreshold() { return scaleOutThreshold; }

    public void setScaleOutThreshold(Double scaleOutThreshold) { this.scaleOutThreshold = scaleOutThreshold; }

    public ScalingCriteria scaleOutRelationalOperation(RelationalOperationTypeEnum scaleOutRelationalOperation) {
        this.scaleOutRelationalOperation = scaleOutRelationalOperation;
        return this;
    }

    public RelationalOperationTypeEnum getScaleOutRelationalOperation() { return scaleOutRelationalOperation; }

    public void setScaleOutRelationalOperation(RelationalOperationTypeEnum scaleOutRelationalOperation) {
        this.scaleOutRelationalOperation = scaleOutRelationalOperation;
    }

    public ScalingCriteria vnfMonitoringParamRef(String vnfMonitoringParamRef) {
        this.vnfMonitoringParamRef = vnfMonitoringParamRef;
        return this;
    }

    public String getVnfMonitoringParamRef() { return vnfMonitoringParamRef; }

    public void setVnfMonitoringParamRef(String vnfMonitoringParamRef) {
        this.vnfMonitoringParamRef = vnfMonitoringParamRef;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScalingCriteria scalingCriteria = (ScalingCriteria) o;

        return Objects.equals(this.name, scalingCriteria.name) &&
                Objects.equals(this.scaleInThreshold, scalingCriteria.scaleInThreshold) &&
                Objects.equals(this.scaleInRelationalOperation, scalingCriteria.scaleInRelationalOperation) &&
                Objects.equals(this.scaleOutThreshold, scalingCriteria.scaleOutThreshold) &&
                Objects.equals(this.scaleOutRelationalOperation, scalingCriteria.scaleOutRelationalOperation) &&
                Objects.equals(this.vnfMonitoringParamRef, scalingCriteria.vnfMonitoringParamRef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, scaleInThreshold, scaleInRelationalOperation, scaleOutThreshold,
                scaleOutRelationalOperation, vnfMonitoringParamRef);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class ScalingCriteria {\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    scaleInThreshold: ").append(toIndentedString(scaleInThreshold)).append("\n");
        sb.append("    scaleInRelationalOperation: ").append(toIndentedString(scaleInRelationalOperation)).append("\n");
        sb.append("    scaleOutThreshold: ").append(toIndentedString(scaleOutThreshold)).append("\n");
        sb.append("    scaleOutRelationalOperation: ").append(toIndentedString(scaleOutRelationalOperation)).append("\n");
        sb.append("    vnfMonitoringParamRef: ").append(toIndentedString(vnfMonitoringParamRef)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
