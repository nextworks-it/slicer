package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A specification of the deltas in terms of number of instances of VNFCs and virtual link bit rates that correspond to the scaling steps of this aspect. A cardinality of zero indicates that this mapping has to be specified in a lifecycle management script or be otherwise known to the VNFM. The information in this attribute, if provided, shall be consistent with the information provided in the &#x27;InstantiationLevel&#x27; information element. If this attribute is provided, it shall be provided for all scaling aspects.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdAspectdeltadetails {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("step-deltas")
  private String stepDeltas = null;

  @JsonProperty("deltas")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_aspect_delta_details_fk", referencedColumnName = "uuid")
  private List<VnfdAspectdeltadetailsDeltas> deltas = null;

  public VnfdAspectdeltadetails stepDeltas(String stepDeltas) {
    this.stepDeltas = stepDeltas;
    return this;
  }

  /**
   * Identifiers of the individual scaling deltas to be applied for the subsequent scaling steps of this aspect. The first entry in the array shall correspond to the first scaling step (between scale levels 0 to 1) and the last entry in the array shall correspond to the last scaling step (between maxScaleLevel-1 and maxScaleLevel).  Each referenced scaling delta shall be declared in the 'deltas' attribute.
   * @return stepDeltas
   **/

  
    public String getStepDeltas() {
    return stepDeltas;
  }

  public void setStepDeltas(String stepDeltas) {
    this.stepDeltas = stepDeltas;
  }

  public VnfdAspectdeltadetails deltas(List<VnfdAspectdeltadetailsDeltas> deltas) {
    this.deltas = deltas;
    return this;
  }

  public VnfdAspectdeltadetails addDeltasItem(VnfdAspectdeltadetailsDeltas deltasItem) {
    if (this.deltas == null) {
      this.deltas = new ArrayList<VnfdAspectdeltadetailsDeltas>();
    }
    this.deltas.add(deltasItem);
    return this;
  }

  /**
   * Declares different scaling deltas, each of which is applied for one or more scaling steps of this aspect.
   * @return deltas
   **/
    public List<VnfdAspectdeltadetailsDeltas> getDeltas() {
    return deltas;
  }

  public void setDeltas(List<VnfdAspectdeltadetailsDeltas> deltas) {
    this.deltas = deltas;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdAspectdeltadetails vnfdAspectdeltadetails = (VnfdAspectdeltadetails) o;
    return Objects.equals(this.stepDeltas, vnfdAspectdeltadetails.stepDeltas) &&
        Objects.equals(this.deltas, vnfdAspectdeltadetails.deltas);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stepDeltas, deltas);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdAspectdeltadetails {\n");
    
    sb.append("    stepDeltas: ").append(toIndentedString(stepDeltas)).append("\n");
    sb.append("    deltas: ").append(toIndentedString(deltas)).append("\n");
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
