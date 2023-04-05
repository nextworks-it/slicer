/*
 * OSM NB API featuring ETSI NFV SOL005
 * This is Open Source MANO Northbound API featuring ETSI NFV SOL005. For more information on OSM, you can visit [http://osm.etsi.org](http://osm.etsi.org). You can send us your comments and questions to OSM_TECH@list.etsi.org or join the [OpenSourceMANO Slack Workplace](https://join.slack.com/t/opensourcemano/shared_invite/enQtMzQ3MzYzNTQ0NDIyLWVkNTE4ZjZjNWI0ZTQyN2VhOTI1MjViMzU1NWYwMWM3ODI4NTQyY2VlODA2ZjczMWIyYTFkZWNiZmFkM2M2ZDk) 
 *
 * OpenAPI spec version: 1.0.0
 * Contact: OSM_TECH@list.etsi.org
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package it.nextworks.nfvmano.sbi.nfvo.osm.rest.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * InstantiateNsRequestVdu
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-03-04T13:48:18.253Z[GMT]")
public class InstantiateNsRequestVdu {
  @SerializedName("id")
  private String id = null;

  @SerializedName("volume")
  private List<InstantiateNsRequestVolume> volume = null;

  @SerializedName("interface")
  private List<InstantiateNsRequestInterface> _interface = null;

  public InstantiateNsRequestVdu id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @Schema(required = true, description = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public InstantiateNsRequestVdu volume(List<InstantiateNsRequestVolume> volume) {
    this.volume = volume;
    return this;
  }

  public InstantiateNsRequestVdu addVolumeItem(InstantiateNsRequestVolume volumeItem) {
    if (this.volume == null) {
      this.volume = new ArrayList<InstantiateNsRequestVolume>();
    }
    this.volume.add(volumeItem);
    return this;
  }

   /**
   * Get volume
   * @return volume
  **/
  @Schema(description = "")
  public List<InstantiateNsRequestVolume> getVolume() {
    return volume;
  }

  public void setVolume(List<InstantiateNsRequestVolume> volume) {
    this.volume = volume;
  }

  public InstantiateNsRequestVdu _interface(List<InstantiateNsRequestInterface> _interface) {
    this._interface = _interface;
    return this;
  }

  public InstantiateNsRequestVdu addInterfaceItem(InstantiateNsRequestInterface _interfaceItem) {
    if (this._interface == null) {
      this._interface = new ArrayList<InstantiateNsRequestInterface>();
    }
    this._interface.add(_interfaceItem);
    return this;
  }

   /**
   * Get _interface
   * @return _interface
  **/
  @Schema(description = "")
  public List<InstantiateNsRequestInterface> getInterface() {
    return _interface;
  }

  public void setInterface(List<InstantiateNsRequestInterface> _interface) {
    this._interface = _interface;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InstantiateNsRequestVdu instantiateNsRequestVdu = (InstantiateNsRequestVdu) o;
    return Objects.equals(this.id, instantiateNsRequestVdu.id) &&
        Objects.equals(this.volume, instantiateNsRequestVdu.volume) &&
        Objects.equals(this._interface, instantiateNsRequestVdu._interface);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, volume, _interface);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InstantiateNsRequestVdu {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    volume: ").append(toIndentedString(volume)).append("\n");
    sb.append("    _interface: ").append(toIndentedString(_interface)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
