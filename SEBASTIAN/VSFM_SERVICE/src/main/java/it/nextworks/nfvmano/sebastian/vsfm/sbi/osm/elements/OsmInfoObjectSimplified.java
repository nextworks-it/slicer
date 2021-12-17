package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class OsmInfoObjectSimplified {

    @Id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("_id")
    private String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("name")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("id")
    private String descriptorId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String version;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("netslice-subnet")
    private List<OsmNetSliceSubnetSimplified> netSliceSubnets;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptorId() {
        return descriptorId;
    }

    public void setDescriptorId(String descriptorId) {
        this.descriptorId = descriptorId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<OsmNetSliceSubnetSimplified> getNetSliceSubnets() {
        return netSliceSubnets;
    }

    public void setNetSliceSubnets(List<OsmNetSliceSubnetSimplified> netSliceSubnets) {
        this.netSliceSubnets = netSliceSubnets;
    }

    @Override
    public String toString() {
        return "OsmInfoObject{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", descriptorId='" + descriptorId + '\'' +
                ", descriptorVersion='" + version + '\'' +
                ", netSliceSubnets=" + netSliceSubnets +
                '}';
    }
}
