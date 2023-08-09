package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.ContainerFormatEnum;
import it.nextworks.nfvmano.libs.common.enums.DiskFormatEnum;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * VnfdSwimagedesc
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdSwimagedesc {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("checksum")
  @Embedded
  private VnfdChecksum checksum = null;

  @JsonProperty("image")
  private String image = null;

  @JsonProperty("version")
  private String version = null;

  @JsonProperty("operating-system")
  private String operatingSystem = null;

  @JsonProperty("min-disk")
  private String minDisk = null;

  @JsonProperty("disk-format")
  private DiskFormatEnum diskFormat = null;

  @JsonProperty("container-format")
  private ContainerFormatEnum containerFormat = null;

  @JsonProperty("min-ram")
  private Double minRam = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("supported-virtualization-environment")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> supportedVirtualizationEnvironment = null;

  @JsonProperty("size")
  private String size = null;

  @JsonProperty("vim-type")
  private String vimType = null;

  public VnfdSwimagedesc name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of this software image.
   * @return name
   **/
  //@Schema(description = "The name of this software image.")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public VnfdSwimagedesc checksum(VnfdChecksum checksum) {
    this.checksum = checksum;
    return this;
  }

  /**
   * Get checksum
   * @return checksum
   **/
  //@Schema(description = "")
  

    public VnfdChecksum getChecksum() {
    return checksum;
  }

  public void setChecksum(VnfdChecksum checksum) {
    this.checksum = checksum;
  }

  public VnfdSwimagedesc image(String image) {
    this.image = image;
    return this;
  }

  /**
   * This is a reference to the actual software image. The reference can be relative to the root of the VNF Package or can be a URL
   * @return image
   **/
  //@Schema(description = "This is a reference to the actual software image. The reference can be relative to the root of the VNF Package or can be a URL")
  
    public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public VnfdSwimagedesc version(String version) {
    this.version = version;
    return this;
  }

  /**
   * The version of this software image.
   * @return version
   **/
  //@Schema(description = "The version of this software image.")
  
    public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public VnfdSwimagedesc operatingSystem(String operatingSystem) {
    this.operatingSystem = operatingSystem;
    return this;
  }

  /**
   * Identifies the operating system used in the software image. This attribute may also identify if a 32 bit or 64 bit software image is used.
   * @return operatingSystem
   **/
  //@Schema(description = "Identifies the operating system used in the software image. This attribute may also identify if a 32 bit or 64 bit software image is used.")
  
    public String getOperatingSystem() {
    return operatingSystem;
  }

  public void setOperatingSystem(String operatingSystem) {
    this.operatingSystem = operatingSystem;
  }

  public VnfdSwimagedesc minDisk(String minDisk) {
    this.minDisk = minDisk;
    return this;
  }

  /**
   * The minimal disk size requirement for this software image. The value of the 'size of storage' attribute of the VirtualStorageDesc referencing this SwImageDesc shall not be smaller than the value of minDisk.
   * @return minDisk
   **/
  //@Schema(description = "The minimal disk size requirement for this software image. The value of the 'size of storage' attribute of the VirtualStorageDesc referencing this SwImageDesc shall not be smaller than the value of minDisk.")
  
    public String getMinDisk() {
    return minDisk;
  }

  public void setMinDisk(String minDisk) {
    this.minDisk = minDisk;
  }

  public VnfdSwimagedesc diskFormat(DiskFormatEnum diskFormat) {
    this.diskFormat = diskFormat;
    return this;
  }

  /**
   * The disk format of a software image is the format of the underlying disk image.
   * @return diskFormat
   **/
  //@Schema(description = "The disk format of a software image is the format of the underlying disk image.")
  
    public DiskFormatEnum getDiskFormat() {
    return diskFormat;
  }

  public void setDiskFormat(DiskFormatEnum diskFormat) {
    this.diskFormat = diskFormat;
  }

  public VnfdSwimagedesc containerFormat(ContainerFormatEnum containerFormat) {
    this.containerFormat = containerFormat;
    return this;
  }

  /**
   * The container format describes the container file format in which software image is provided.
   * @return containerFormat
   **/
  //@Schema(description = "The container format describes the container file format in which software image is provided.")
  
    public ContainerFormatEnum getContainerFormat() {
    return containerFormat;
  }

  public void setContainerFormat(ContainerFormatEnum containerFormat) {
    this.containerFormat = containerFormat;
  }

  public VnfdSwimagedesc minRam(Double minRam) {
    this.minRam = minRam;
    return this;
  }

  /**
   * The minimal RAM requirement for this software image. The value of the 'size' attribute of VirtualMemoryData of the Vdu referencing this SwImageDesc shall not be smaller than the value of minRam.
   * @return minRam
   **/
  //@Schema(description = "The minimal RAM requirement for this software image. The value of the 'size' attribute of VirtualMemoryData of the Vdu referencing this SwImageDesc shall not be smaller than the value of minRam.")
  
    public Double getMinRam() {
    return minRam;
  }

  public void setMinRam(Double minRam) {
    this.minRam = minRam;
  }

  public VnfdSwimagedesc id(String id) {
    this.id = id;
    return this;
  }

  /**
   * The identifier of this software image.
   * @return id
   **/
  //@Schema(description = "The identifier of this software image.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public VnfdSwimagedesc supportedVirtualizationEnvironment(List<String> supportedVirtualizationEnvironment) {
    this.supportedVirtualizationEnvironment = supportedVirtualizationEnvironment;
    return this;
  }

  public VnfdSwimagedesc addSupportedVirtualizationEnvironmentItem(String supportedVirtualizationEnvironmentItem) {
    if (this.supportedVirtualizationEnvironment == null) {
      this.supportedVirtualizationEnvironment = new ArrayList<String>();
    }
    this.supportedVirtualizationEnvironment.add(supportedVirtualizationEnvironmentItem);
    return this;
  }

  /**
   * Get supportedVirtualizationEnvironment
   * @return supportedVirtualizationEnvironment
   **/
  //@Schema(description = "")
  
    public List<String> getSupportedVirtualizationEnvironment() {
    return supportedVirtualizationEnvironment;
  }

  public void setSupportedVirtualizationEnvironment(List<String> supportedVirtualizationEnvironment) {
    this.supportedVirtualizationEnvironment = supportedVirtualizationEnvironment;
  }

  public VnfdSwimagedesc size(String size) {
    this.size = size;
    return this;
  }

  /**
   * The size of this software image.
   * @return size
   **/
  //@Schema(description = "The size of this software image.")
  
    public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public VnfdSwimagedesc vimType(String vimType) {
      this.vimType = vimType;
      return this;
  }

  public String getVimType() { return vimType; }

  public void setVimType(String vimType) { this.vimType = vimType; }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdSwimagedesc vnfdSwimagedesc = (VnfdSwimagedesc) o;
    return Objects.equals(this.name, vnfdSwimagedesc.name) &&
            Objects.equals(this.checksum, vnfdSwimagedesc.checksum) &&
            Objects.equals(this.image, vnfdSwimagedesc.image) &&
            Objects.equals(this.version, vnfdSwimagedesc.version) &&
            Objects.equals(this.operatingSystem, vnfdSwimagedesc.operatingSystem) &&
            Objects.equals(this.minDisk, vnfdSwimagedesc.minDisk) &&
            Objects.equals(this.diskFormat, vnfdSwimagedesc.diskFormat) &&
            Objects.equals(this.containerFormat, vnfdSwimagedesc.containerFormat) &&
            Objects.equals(this.minRam, vnfdSwimagedesc.minRam) &&
            Objects.equals(this.id, vnfdSwimagedesc.id) &&
            Objects.equals(this.supportedVirtualizationEnvironment, vnfdSwimagedesc.supportedVirtualizationEnvironment) &&
            Objects.equals(this.size, vnfdSwimagedesc.size) &&
            Objects.equals(this.vimType, vnfdSwimagedesc.vimType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, checksum, image, version, operatingSystem, minDisk, diskFormat, containerFormat, minRam,
            id, supportedVirtualizationEnvironment, size, vimType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdSwimagedesc {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    checksum: ").append(toIndentedString(checksum)).append("\n");
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    operatingSystem: ").append(toIndentedString(operatingSystem)).append("\n");
    sb.append("    minDisk: ").append(toIndentedString(minDisk)).append("\n");
    sb.append("    diskFormat: ").append(toIndentedString(diskFormat)).append("\n");
    sb.append("    containerFormat: ").append(toIndentedString(containerFormat)).append("\n");
    sb.append("    minRam: ").append(toIndentedString(minRam)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    supportedVirtualizationEnvironment: ").append(toIndentedString(supportedVirtualizationEnvironment)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    vimType: ").append(toIndentedString(vimType)).append("\n");
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
