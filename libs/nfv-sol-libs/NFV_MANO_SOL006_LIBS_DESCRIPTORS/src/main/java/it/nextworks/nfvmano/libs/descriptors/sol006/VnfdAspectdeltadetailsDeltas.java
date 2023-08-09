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
 * VnfdAspectdeltadetailsDeltas
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdAspectdeltadetailsDeltas {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("virtual-link-bit-rate-delta")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_aspect_delta_details_deltas_fk", referencedColumnName = "uuid")
  private List<VnfdAspectdeltadetailsVirtuallinkbitratedelta> virtualLinkBitRateDelta = null;

  @JsonProperty("vdu-delta")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_aspect_delta_details_deltas_fk", referencedColumnName = "uuid")
  private List<VnfdAspectdeltadetailsVdudelta> vduDelta = null;

  @JsonProperty("kdu-resource-delta")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_aspect_delta_details_deltas_fk", referencedColumnName = "uuid")
  private List<KduResourceDelta> kduResourceDelta = null;

  @JsonProperty("id")
  private String id = null;

  public VnfdAspectdeltadetailsDeltas virtualLinkBitRateDelta(List<VnfdAspectdeltadetailsVirtuallinkbitratedelta> virtualLinkBitRateDelta) {
    this.virtualLinkBitRateDelta = virtualLinkBitRateDelta;
    return this;
  }

  public VnfdAspectdeltadetailsDeltas addVirtualLinkBitRateDeltaItem(VnfdAspectdeltadetailsVirtuallinkbitratedelta virtualLinkBitRateDeltaItem) {
    if (this.virtualLinkBitRateDelta == null) {
      this.virtualLinkBitRateDelta = new ArrayList<VnfdAspectdeltadetailsVirtuallinkbitratedelta>();
    }
    this.virtualLinkBitRateDelta.add(virtualLinkBitRateDeltaItem);
    return this;
  }

  /**
   * The bitrate to be added or removed to virtual links created from particular virtual link descriptors.
   * @return virtualLinkBitRateDelta
   **/
  //@Schema(description = "The bitrate to be added or removed to virtual links created from particular virtual link descriptors.")

    public List<VnfdAspectdeltadetailsVirtuallinkbitratedelta> getVirtualLinkBitRateDelta() {
    return virtualLinkBitRateDelta;
  }

  public void setVirtualLinkBitRateDelta(List<VnfdAspectdeltadetailsVirtuallinkbitratedelta> virtualLinkBitRateDelta) {
    this.virtualLinkBitRateDelta = virtualLinkBitRateDelta;
  }

  public VnfdAspectdeltadetailsDeltas vduDelta(List<VnfdAspectdeltadetailsVdudelta> vduDelta) {
    this.vduDelta = vduDelta;
    return this;
  }

  public VnfdAspectdeltadetailsDeltas addVduDeltaItem(VnfdAspectdeltadetailsVdudelta vduDeltaItem) {
    if (this.vduDelta == null) {
      this.vduDelta = new ArrayList<VnfdAspectdeltadetailsVdudelta>();
    }
    this.vduDelta.add(vduDeltaItem);
    return this;
  }

  /**
   * The number of VNFC instances based on particular VDUs to be created or removed.
   * @return vduDelta
   **/
  //@Schema(description = "The number of VNFC instances based on particular VDUs to be created or removed.")

    public List<VnfdAspectdeltadetailsVdudelta> getVduDelta() {
    return vduDelta;
  }

  public void setVduDelta(List<VnfdAspectdeltadetailsVdudelta> vduDelta) {
    this.vduDelta = vduDelta;
  }

  public VnfdAspectdeltadetailsDeltas id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of this scaling delta.
   * @return id
   **/
  //@Schema(description = "Identifier of this scaling delta.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public VnfdAspectdeltadetailsDeltas kduResourceDelta(List<KduResourceDelta> kduResourceDelta) {
      this.kduResourceDelta = kduResourceDelta;
      return this;
  }

  public VnfdAspectdeltadetailsDeltas addKduResourceDeltaItem(KduResourceDelta kduResourceDelta) {
      if(this.kduResourceDelta == null)
        this.kduResourceDelta = new ArrayList<>();

      this.kduResourceDelta.add(kduResourceDelta);
      return this;
  }

  public List<KduResourceDelta> getKduResourceDelta() { return kduResourceDelta; }

  public void setKduResourceDelta(List<KduResourceDelta> kduResourceDelta) { this.kduResourceDelta = kduResourceDelta; }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdAspectdeltadetailsDeltas vnfdAspectdeltadetailsDeltas = (VnfdAspectdeltadetailsDeltas) o;
    return Objects.equals(this.virtualLinkBitRateDelta, vnfdAspectdeltadetailsDeltas.virtualLinkBitRateDelta) &&
            Objects.equals(this.vduDelta, vnfdAspectdeltadetailsDeltas.vduDelta) &&
            Objects.equals(this.id, vnfdAspectdeltadetailsDeltas.id) &&
            Objects.equals(this.kduResourceDelta, vnfdAspectdeltadetailsDeltas.kduResourceDelta);
  }

  @Override
  public int hashCode() {
    return Objects.hash(virtualLinkBitRateDelta, vduDelta, id, kduResourceDelta);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdAspectdeltadetailsDeltas {\n");
    
    sb.append("    virtualLinkBitRateDelta: ").append(toIndentedString(virtualLinkBitRateDelta)).append("\n");
    sb.append("    vduDelta: ").append(toIndentedString(vduDelta)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    kduResourceDelta: ").append(toIndentedString(kduResourceDelta)).append("\n");
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
