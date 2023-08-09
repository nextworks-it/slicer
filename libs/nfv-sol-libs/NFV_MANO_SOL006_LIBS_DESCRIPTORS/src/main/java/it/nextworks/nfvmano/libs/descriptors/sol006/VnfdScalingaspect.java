package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

/**
 * VnfdScalingaspect
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdScalingaspect {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("aspect-delta-details")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "vnfd_aspect_delta_details_fk", referencedColumnName = "uuid")
  private VnfdAspectdeltadetails aspectDeltaDetails = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("max-scale-level")
  private String maxScaleLevel = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("scaling-policy")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_scaling_aspect_fk", referencedColumnName = "uuid")
  private List<ScalingPolicy> scalingPolicy = null;

  @JsonProperty("scaling-config-action")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_scaling_aspect_fk", referencedColumnName = "uuid")
  private List<ScalingConfigAction> scalingConfigAction = null;

  public VnfdScalingaspect aspectDeltaDetails(VnfdAspectdeltadetails aspectDeltaDetails) {
    this.aspectDeltaDetails = aspectDeltaDetails;
    return this;
  }

  /**
   * Get aspectDeltaDetails
   * @return aspectDeltaDetails
   **/
  //@Schema(description = "")
  

    public VnfdAspectdeltadetails getAspectDeltaDetails() {
    return aspectDeltaDetails;
  }

  public void setAspectDeltaDetails(VnfdAspectdeltadetails aspectDeltaDetails) {
    this.aspectDeltaDetails = aspectDeltaDetails;
  }

  public VnfdScalingaspect id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of this aspect in the VNFD.
   * @return id
   **/
  //@Schema(description = "Unique identifier of this aspect in the VNFD.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public VnfdScalingaspect maxScaleLevel(String maxScaleLevel) {
    this.maxScaleLevel = maxScaleLevel;
    return this;
  }

  /**
   * The maximum scaleLevel for total number of scaling steps that can be applied w.r.t. this aspect. The value of this attribute corresponds to the number of scaling steps can be applied to this aspect when scaling it from the minimum scale level (i.e. 0) to the maximum scale level defined by this attribute.
   * @return maxScaleLevel
   **/
  //@Schema(description = "The maximum scaleLevel for total number of scaling steps that can be applied w.r.t. this aspect. The value of this attribute corresponds to the number of scaling steps can be applied to this aspect when scaling it from the minimum scale level (i.e. 0) to the maximum scale level defined by this attribute.")
  
    public String getMaxScaleLevel() {
    return maxScaleLevel;
  }

  public void setMaxScaleLevel(String maxScaleLevel) {
    this.maxScaleLevel = maxScaleLevel;
  }

  public VnfdScalingaspect name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Human readable name of the aspect.
   * @return name
   **/
  //@Schema(description = "Human readable name of the aspect.")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public VnfdScalingaspect description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Human readable description of the aspect.
   * @return description
   **/
  //@Schema(description = "Human readable description of the aspect.")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public VnfdScalingaspect scalingPolicy(List<ScalingPolicy> scalingPolicy) {
      this.scalingPolicy = scalingPolicy;
      return this;
  }

  public VnfdScalingaspect addScalingPolicyItem(ScalingPolicy scalingPolicy) {
      if(this.scalingPolicy == null)
        this.scalingPolicy = new ArrayList<>();

      this.scalingPolicy.add(scalingPolicy);
      return this;
  }

  public List<ScalingPolicy> getScalingPolicy() { return scalingPolicy; }

  public void setScalingPolicy(List<ScalingPolicy> scalingPolicy) { this.scalingPolicy = scalingPolicy; }

  public VnfdScalingaspect scalingConfigAction(List<ScalingConfigAction> scalingConfigAction) {
      this.scalingConfigAction = scalingConfigAction;
      return this;
  }

  public VnfdScalingaspect addScalingConfigActionItem(ScalingConfigAction scalingConfigAction) {
      if(this.scalingConfigAction == null)
        this.scalingConfigAction = new ArrayList<>();

      this.scalingConfigAction.add(scalingConfigAction);
      return this;
  }

  public List<ScalingConfigAction> getScalingConfigAction() { return scalingConfigAction; }

  public void setScalingConfigAction(List<ScalingConfigAction> scalingConfigAction) {
    this.scalingConfigAction = scalingConfigAction;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdScalingaspect vnfdScalingaspect = (VnfdScalingaspect) o;
    return Objects.equals(this.aspectDeltaDetails, vnfdScalingaspect.aspectDeltaDetails) &&
            Objects.equals(this.id, vnfdScalingaspect.id) &&
            Objects.equals(this.maxScaleLevel, vnfdScalingaspect.maxScaleLevel) &&
            Objects.equals(this.name, vnfdScalingaspect.name) &&
            Objects.equals(this.description, vnfdScalingaspect.description) &&
            Objects.equals(this.scalingPolicy, vnfdScalingaspect.scalingPolicy) &&
            Objects.equals(this.scalingConfigAction, vnfdScalingaspect.scalingConfigAction);
  }

  @Override
  public int hashCode() {
    return Objects.hash(aspectDeltaDetails, id, maxScaleLevel, name, description, scalingPolicy, scalingConfigAction);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdScalingaspect {\n");
    
    sb.append("    aspectDeltaDetails: ").append(toIndentedString(aspectDeltaDetails)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    maxScaleLevel: ").append(toIndentedString(maxScaleLevel)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    scalingPolicy: ").append(toIndentedString(scalingPolicy)).append("\n");
    sb.append("    scalingConfigAction: ").append(toIndentedString(scalingConfigAction)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
