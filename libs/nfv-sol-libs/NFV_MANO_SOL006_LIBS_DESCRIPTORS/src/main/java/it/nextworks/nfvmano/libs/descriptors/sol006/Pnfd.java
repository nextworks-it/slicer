package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Pnfd information element is a deployment template enabling on-boarding PNFs and referencing them from an NSD. It focuses on connectivity aspects only
 */
//@Schema(description = "The Pnfd information element is a deployment template enabling on-boarding PNFs and referencing them from an NSD. It focuses on connectivity aspects only")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class Pnfd extends SecurityGroupRule {

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("ext-cpd")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "pnfd_fk", referencedColumnName = "uuid")
  private List<Cpd> extCpd = null;

  @JsonProperty("invariant-id")
  private String invariantId = null;

  @JsonProperty("version")
  private String version = null;

  @JsonProperty("function-description")
  private String functionDescription = null;

  @JsonProperty("provider")
  private String provider = null;

  @JsonProperty("security")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "pnfd_fk", referencedColumnName = "uuid")
  private List<SecurityParameters> security = null;

  @JsonProperty("id")
  @Column(unique = true)
  private String id = null;

  @JsonProperty("geographical-location-info")
  private String geographicalLocationInfo = null;

  public Pnfd name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Provides the human readable name of the PNFD.
   * @return name
   **/
  //@Schema(description = "Provides the human readable name of the PNFD.")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Pnfd extCpd(List<Cpd> extCpd) {
    this.extCpd = extCpd;
    return this;
  }

  public Pnfd addExtCpdItem(Cpd extCpdItem) {
    if (this.extCpd == null) {
      this.extCpd = new ArrayList<Cpd>();
    }
    this.extCpd.add(extCpdItem);
    return this;
  }

  /**
   * Specifies the characteristics of one or more connection points where to connect the PNF to a VL.
   * @return extCpd
   **/
  //@Schema(description = "Specifies the characteristics of one or more connection points where to connect the PNF to a VL.")

    public List<Cpd> getExtCpd() {
    return extCpd;
  }

  public void setExtCpd(List<Cpd> extCpd) {
    this.extCpd = extCpd;
  }

  public Pnfd invariantId(String invariantId) {
    this.invariantId = invariantId;
    return this;
  }

  /**
   * Identifies a PNFD in a version independent manner. This attribute is invariant across versions of PNFD.
   * @return invariantId
   **/
  //@Schema(description = "Identifies a PNFD in a version independent manner. This attribute is invariant across versions of PNFD.")
  
    public String getInvariantId() {
    return invariantId;
  }

  public void setInvariantId(String invariantId) {
    this.invariantId = invariantId;
  }

  public Pnfd version(String version) {
    this.version = version;
    return this;
  }

  /**
   * Identifies the version of the PNFD.
   * @return version
   **/
  //@Schema(description = "Identifies the version of the PNFD.")
  
    public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public Pnfd functionDescription(String functionDescription) {
    this.functionDescription = functionDescription;
    return this;
  }

  /**
   * Describes the PNF function.
   * @return functionDescription
   **/
  //@Schema(description = "Describes the PNF function.")
  
    public String getFunctionDescription() {
    return functionDescription;
  }

  public void setFunctionDescription(String functionDescription) {
    this.functionDescription = functionDescription;
  }

  public Pnfd provider(String provider) {
    this.provider = provider;
    return this;
  }

  /**
   * Identifies the provider of the PNFD.
   * @return provider
   **/
  //@Schema(description = "Identifies the provider of the PNFD.")
  
    public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

  public Pnfd security(List<SecurityParameters> security) {
    this.security = security;
    return this;
  }

  public Pnfd addSecurityItem(SecurityParameters securityItem) {
    if (this.security == null) {
      this.security = new ArrayList<SecurityParameters>();
    }
    this.security.add(securityItem);
    return this;
  }

  /**
   * Provides a signature to prevent tampering. Editor's Note: While IFA014 does specify that the PNFD includes a security parameter. SOL001 does not have one. We need to harmonize SOL001 & SOL006 on this point.
   * @return security
   **/
  //@Schema(description = "Provides a signature to prevent tampering. Editor's Note: While IFA014 does specify that the PNFD includes a security parameter. SOL001 does not have one. We need to harmonize SOL001 & SOL006 on this point.")

    public List<SecurityParameters> getSecurity() {
    return security;
  }

  public void setSecurity(List<SecurityParameters> security) {
    this.security = security;
  }

  public Pnfd id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of this Pnfd information element. It uniquely identifies the PNFD.
   * @return id
   **/
  //@Schema(description = "Identifier of this Pnfd information element. It uniquely identifies the PNFD.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Pnfd geographicalLocationInfo(String geographicalLocationInfo) {
    this.geographicalLocationInfo = geographicalLocationInfo;
    return this;
  }

  /**
   * It provides information about the geographical location (e.g. geographic coordinates or address of the building, etc.) of the PNF. The cardinality 0 is used when the location is unknown. Editor's Note: The type is TBD in SOL001. We need to make a common SOL001/SOL006 decision.
   * @return geographicalLocationInfo
   **/
  //@Schema(description = "It provides information about the geographical location (e.g. geographic coordinates or address of the building, etc.) of the PNF. The cardinality 0 is used when the location is unknown. Editor's Note: The type is TBD in SOL001. We need to make a common SOL001/SOL006 decision.")
  
    public String getGeographicalLocationInfo() {
    return geographicalLocationInfo;
  }

  public void setGeographicalLocationInfo(String geographicalLocationInfo) {
    this.geographicalLocationInfo = geographicalLocationInfo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Pnfd pnfd = (Pnfd) o;
    return Objects.equals(this.name, pnfd.name) &&
        Objects.equals(this.extCpd, pnfd.extCpd) &&
        Objects.equals(this.invariantId, pnfd.invariantId) &&
        Objects.equals(this.version, pnfd.version) &&
        Objects.equals(this.functionDescription, pnfd.functionDescription) &&
        Objects.equals(this.provider, pnfd.provider) &&
        Objects.equals(this.security, pnfd.security) &&
        Objects.equals(this.id, pnfd.id) &&
        Objects.equals(this.geographicalLocationInfo, pnfd.geographicalLocationInfo) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, extCpd, invariantId, version, functionDescription, provider, security, id, geographicalLocationInfo, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Pnfd {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    extCpd: ").append(toIndentedString(extCpd)).append("\n");
    sb.append("    invariantId: ").append(toIndentedString(invariantId)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    functionDescription: ").append(toIndentedString(functionDescription)).append("\n");
    sb.append("    provider: ").append(toIndentedString(provider)).append("\n");
    sb.append("    security: ").append(toIndentedString(security)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    geographicalLocationInfo: ").append(toIndentedString(geographicalLocationInfo)).append("\n");
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
