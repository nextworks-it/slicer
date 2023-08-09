package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

/**
 * The checksum of the software image file.
 */
//@Schema(description = "The checksum of the software image file.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class VnfdChecksum {

  @JsonProperty("hash")
  private String hash = null;

  @JsonProperty("algorithm")
  private String algorithm = null;

  public VnfdChecksum hash(String hash) {
    this.hash = hash;
    return this;
  }

  /**
   * Contains the result of applying the algorithm indicated by the algorithm attribute to the data to which this ChecksumData refers.
   * @return hash
   **/
  //@Schema(description = "Contains the result of applying the algorithm indicated by the algorithm attribute to the data to which this ChecksumData refers.")
  
    public String getHash() {
    return hash;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  public VnfdChecksum algorithm(String algorithm) {
    this.algorithm = algorithm;
    return this;
  }

  /**
   * Species the algorithm used to obtain the checksum value.
   * @return algorithm
   **/
  //@Schema(description = "Species the algorithm used to obtain the checksum value.")
  
    public String getAlgorithm() {
    return algorithm;
  }

  public void setAlgorithm(String algorithm) {
    this.algorithm = algorithm;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdChecksum vnfdChecksum = (VnfdChecksum) o;
    return Objects.equals(this.hash, vnfdChecksum.hash) &&
        Objects.equals(this.algorithm, vnfdChecksum.algorithm);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hash, algorithm);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdChecksum {\n");
    
    sb.append("    hash: ").append(toIndentedString(hash)).append("\n");
    sb.append("    algorithm: ").append(toIndentedString(algorithm)).append("\n");
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
