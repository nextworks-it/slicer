package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.common.enums.SourceEnum;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

/**
 * VnfdIndicator
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdIndicator {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("indicator-value")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> indicatorValue = null;

  @JsonProperty("source")
  private SourceEnum source = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  public VnfdIndicator indicatorValue(List<String> indicatorValue) {
    this.indicatorValue = indicatorValue;
    return this;
  }

  public VnfdIndicator addIndicatorValueItem(String indicatorValueItem) {
    if (this.indicatorValue == null) {
      this.indicatorValue = new ArrayList<String>();
    }
    this.indicatorValue.add(indicatorValueItem);
    return this;
  }

  /**
   * Get indicatorValue
   * @return indicatorValue
   **/
  //@Schema(description = "")
  
    public List<String> getIndicatorValue() {
    return indicatorValue;
  }

  public void setIndicatorValue(List<String> indicatorValue) {
    this.indicatorValue = indicatorValue;
  }

  public VnfdIndicator source(SourceEnum source) {
    this.source = source;
    return this;
  }

  /**
   * Describe the source of the indicator. The possible values are: • VNF. • EM. • Both. This tells the consumer where to send the subscription request.
   * @return source
   **/
  //@Schema(description = "Describe the source of the indicator. The possible values are: • VNF. • EM. • Both. This tells the consumer where to send the subscription request.")
  
    public SourceEnum getSource() {
    return source;
  }

  public void setSource(SourceEnum source) {
    this.source = source;
  }

  public VnfdIndicator id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier
   * @return id
   **/
  //@Schema(description = "Unique identifier")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public VnfdIndicator name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The human readable name of the VnfIndicator.
   * @return name
   **/
  //@Schema(description = "The human readable name of the VnfIndicator.")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdIndicator vnfdIndicator = (VnfdIndicator) o;
    return Objects.equals(this.indicatorValue, vnfdIndicator.indicatorValue) &&
        Objects.equals(this.source, vnfdIndicator.source) &&
        Objects.equals(this.id, vnfdIndicator.id) &&
        Objects.equals(this.name, vnfdIndicator.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(indicatorValue, source, id, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdIndicator {\n");
    
    sb.append("    indicatorValue: ").append(toIndentedString(indicatorValue)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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
