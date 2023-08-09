package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.ScalingCriteriaOperationEnum;
import it.nextworks.nfvmano.libs.common.enums.ScalingPolicyTypeEnum;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class ScalingPolicy {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("scaling-type")
    private ScalingPolicyTypeEnum scalingType = null;

    @JsonProperty("enabled")
    private Boolean enabled = null;

    @JsonProperty("scale-in-operation-type")
    private ScalingCriteriaOperationEnum scaleInOperationType = null;

    @JsonProperty("scale-out-operation-type")
    private ScalingCriteriaOperationEnum scaleOutOperationType = null;

    @JsonProperty("threshold-time")
    private String thresholdTime = null;

    @JsonProperty("cooldown-time")
    private String cooldownTime = null;

    @JsonProperty("scaling-criteria")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "scaling_policy_fk", referencedColumnName = "uuid")
    private List<ScalingCriteria> scalingCriteria = null;

    public ScalingPolicy name(String name) {
        this.name = name;
        return this;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public ScalingPolicy scalingType(ScalingPolicyTypeEnum scalingType) {
        this.scalingType = scalingType;
        return this;
    }

    public ScalingPolicyTypeEnum getScalingType() { return scalingType; }

    public void setScalingType(ScalingPolicyTypeEnum scalingType) { this.scalingType = scalingType; }

    public ScalingPolicy enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Boolean getEnabled() { return enabled; }

    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

    public ScalingPolicy scaleInOperationType(ScalingCriteriaOperationEnum scaleInOperationType) {
        this.scaleInOperationType = scaleInOperationType;
        return this;
    }

    public ScalingCriteriaOperationEnum getScaleInOperationType() { return scaleInOperationType; }

    public void setScaleInOperationType(ScalingCriteriaOperationEnum scaleInOperationType) {
        this.scaleInOperationType = scaleInOperationType;
    }

    public ScalingPolicy scaleOutOperationType(ScalingCriteriaOperationEnum scaleOutOperationType) {
        this.scaleOutOperationType = scaleOutOperationType;
        return this;
    }

    public ScalingCriteriaOperationEnum getScaleOutOperationType() { return scaleOutOperationType; }

    public void setScaleOutOperationType(ScalingCriteriaOperationEnum scaleOutOperationType) {
        this.scaleOutOperationType = scaleOutOperationType;
    }

    public ScalingPolicy thresholdTime(String thresholdTime) {
        this.thresholdTime = thresholdTime;
        return this;
    }

    public String getThresholdTime() { return thresholdTime; }

    public void setThresholdTime(String thresholdTime) { this.thresholdTime = thresholdTime; }

    public ScalingPolicy cooldownTime(String cooldownTime) {
        this.cooldownTime = cooldownTime;
        return this;
    }

    public String getCooldownTime() { return cooldownTime; }

    public void setCooldownTime(String cooldownTime) { this.cooldownTime = cooldownTime; }

    public ScalingPolicy scalingCriteria(List<ScalingCriteria> scalingCriteria) {
        this.scalingCriteria = scalingCriteria;
        return this;
    }

    public ScalingPolicy addScalingCriteriaItem(ScalingCriteria scalingCriteria) {
        if(this.scalingCriteria == null)
            this.scalingCriteria = new ArrayList<>();

        this.scalingCriteria.add(scalingCriteria);
        return this;
    }

    public List<ScalingCriteria> getScalingCriteria() { return scalingCriteria; }

    public void setScalingCriteria(List<ScalingCriteria> scalingCriteria) { this.scalingCriteria = scalingCriteria; }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScalingPolicy scalingPolicy = (ScalingPolicy) o;

        return Objects.equals(this.name, scalingPolicy.name) &&
                Objects.equals(this.scalingType, scalingPolicy.scalingType) &&
                Objects.equals(this.enabled, scalingPolicy.enabled) &&
                Objects.equals(this.scaleInOperationType, scalingPolicy.scaleInOperationType) &&
                Objects.equals(this.scaleOutOperationType, scalingPolicy.scaleOutOperationType) &&
                Objects.equals(this.thresholdTime, scalingPolicy.thresholdTime) &&
                Objects.equals(this.cooldownTime, scalingPolicy.cooldownTime) &&
                Objects.equals(this.scalingCriteria, scalingPolicy.scalingCriteria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, scalingType, enabled, scaleInOperationType, scaleOutOperationType, thresholdTime,
                cooldownTime, scalingCriteria);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class ScalingPolicy {\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    scalingType: ").append(toIndentedString(scalingType)).append("\n");
        sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
        sb.append("    scaleInOperationType: ").append(toIndentedString(scaleInOperationType)).append("\n");
        sb.append("    scaleOutOperationType: ").append(toIndentedString(scaleOutOperationType)).append("\n");
        sb.append("    thresholdTime: ").append(toIndentedString(thresholdTime)).append("\n");
        sb.append("    cooldownTime: ").append(toIndentedString(cooldownTime)).append("\n");
        sb.append("    scalingCriteria: ").append(toIndentedString(scalingCriteria)).append("\n");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
