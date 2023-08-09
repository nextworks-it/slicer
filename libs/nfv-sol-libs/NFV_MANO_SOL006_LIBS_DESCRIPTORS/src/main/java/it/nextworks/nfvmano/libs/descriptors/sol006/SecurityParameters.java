package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * SecurityParameters
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class SecurityParameters {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("certificate")
  private String certificate = null;

  @JsonProperty("algorithm")
  private String algorithm = null;

  @JsonProperty("signature")
  private String signature = null;

  public SecurityParameters certificate(String certificate) {
    this.certificate = certificate;
    return this;
  }

  /**
   * Provides a certificate or a reference to a certificate to validate the signature.
   * @return certificate
   **/
  //@Schema(description = "Provides a certificate or a reference to a certificate to validate the signature.")
  
    public String getCertificate() {
    return certificate;
  }

  public void setCertificate(String certificate) {
    this.certificate = certificate;
  }

  public SecurityParameters algorithm(String algorithm) {
    this.algorithm = algorithm;
    return this;
  }

  /**
   * Identifies the algorithm used to compute the signature.
   * @return algorithm
   **/
  //@Schema(description = "Identifies the algorithm used to compute the signature.")
  
    public String getAlgorithm() {
    return algorithm;
  }

  public void setAlgorithm(String algorithm) {
    this.algorithm = algorithm;
  }

  public SecurityParameters signature(String signature) {
    this.signature = signature;
    return this;
  }

  /**
   * Provides the signature of the signed part of the descriptor.
   * @return signature
   **/
  //@Schema(description = "Provides the signature of the signed part of the descriptor.")
  
    public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SecurityParameters securityParameters = (SecurityParameters) o;
    return Objects.equals(this.certificate, securityParameters.certificate) &&
        Objects.equals(this.algorithm, securityParameters.algorithm) &&
        Objects.equals(this.signature, securityParameters.signature);
  }

  @Override
  public int hashCode() {
    return Objects.hash(certificate, algorithm, signature);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SecurityParameters {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    certificate: ").append(toIndentedString(certificate)).append("\n");
    sb.append("    algorithm: ").append(toIndentedString(algorithm)).append("\n");
    sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
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
