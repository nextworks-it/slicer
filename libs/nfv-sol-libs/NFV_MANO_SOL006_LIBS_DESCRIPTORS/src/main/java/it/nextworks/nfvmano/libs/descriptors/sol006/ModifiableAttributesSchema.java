package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;



/**
 * Describes the modifiable attributes of the VNF.
 */
//@Schema(description = "Describes the modifiable attributes of the VNF.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class ModifiableAttributesSchema   {
  @JsonProperty("extension")

  private List<String> extension = null;

  @JsonProperty("metadata")

  private List<String> metadata = null;

  public ModifiableAttributesSchema extension(List<String> extension) {
    this.extension = extension;
    return this;
  }

  public ModifiableAttributesSchema addExtensionItem(String extensionItem) {
    if (this.extension == null) {
      this.extension = new ArrayList<String>();
    }
    this.extension.add(extensionItem);
    return this;
  }

  /**
   * Get extension
   * @return extension
   **/
  //@Schema(description = "")
  
    public List<String> getExtension() {
    return extension;
  }

  public void setExtension(List<String> extension) {
    this.extension = extension;
  }

  public ModifiableAttributesSchema metadata(List<String> metadata) {
    this.metadata = metadata;
    return this;
  }

  public ModifiableAttributesSchema addMetadataItem(String metadataItem) {
    if (this.metadata == null) {
      this.metadata = new ArrayList<String>();
    }
    this.metadata.add(metadataItem);
    return this;
  }

  /**
   * Get metadata
   * @return metadata
   **/
  //@Schema(description = "")
  
    public List<String> getMetadata() {
    return metadata;
  }

  public void setMetadata(List<String> metadata) {
    this.metadata = metadata;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModifiableAttributesSchema modifiableAttributesSchema = (ModifiableAttributesSchema) o;
    return Objects.equals(this.extension, modifiableAttributesSchema.extension) &&
        Objects.equals(this.metadata, modifiableAttributesSchema.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(extension, metadata);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModifiableAttributesSchema {\n");
    
    sb.append("    extension: ").append(toIndentedString(extension)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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
