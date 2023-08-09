package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class ProviderNetwork {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("physical-network")
    private String physicalNetwork = null;

    @JsonProperty("segmentation_id")
    private String segmentationId = null;

    public ProviderNetwork physicalNetwork(String physicalNetwork) {
        this.physicalNetwork = physicalNetwork;
        return this;
    }

    public String getPhysicalNetwork() { return physicalNetwork; }

    public void setPhysicalNetwork(String physicalNetwork) { this.physicalNetwork = physicalNetwork; }

    public ProviderNetwork segmentationId(String segmentationId) {
        this.segmentationId = segmentationId;
        return this;
    }

    public String getSegmentationId() { return segmentationId; }

    public void setSegmentationId(String segmentationId) { this.segmentationId = segmentationId; }

    @Override
    public boolean equals(Object o) {

        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        ProviderNetwork providerNetwork = (ProviderNetwork) o;

        return Objects.equals(this.physicalNetwork, providerNetwork.physicalNetwork) &&
                Objects.equals(this.segmentationId, providerNetwork.segmentationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(physicalNetwork, segmentationId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class ProviderNetwork {\n");
        sb.append("    physicalNetwork: ").append(toIndentedString(physicalNetwork)).append("\n");
        sb.append("    segmentationId: ").append(toIndentedString(segmentationId)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if(o == null)
            return "null";

        return o.toString().replace("\n", "\n    ");
    }
}
