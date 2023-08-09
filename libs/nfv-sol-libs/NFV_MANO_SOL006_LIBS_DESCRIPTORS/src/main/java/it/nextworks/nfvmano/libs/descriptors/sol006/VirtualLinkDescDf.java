package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.ServiceAvailabilityLevelEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class VirtualLinkDescDf {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("qos")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "qos_fk", referencedColumnName = "uuid")
    private QosSchema qos = null;

    @JsonProperty("service-availability-level")
    private ServiceAvailabilityLevelEnum serviceAvailabilityLevel = null;

    public VirtualLinkDescDf id(String id) {
        this.id = id;
        return this;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public VirtualLinkDescDf qos(QosSchema qos) {
        this.qos = qos;
        return this;
    }

    public QosSchema getQos() { return qos; }

    public void setQos(QosSchema qos) { this.qos = qos; }

    @Override
    public boolean equals(java.lang.Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        VirtualLinkDescDf virtualLinkDescDf = (VirtualLinkDescDf) o;
        return Objects.equals(this.id, virtualLinkDescDf.id) &&
                Objects.equals(this.qos, virtualLinkDescDf.qos) &&
                Objects.equals(this.serviceAvailabilityLevel, virtualLinkDescDf.serviceAvailabilityLevel);
    }

    @Override
    public int hashCode() { return Objects.hash(id, qos, serviceAvailabilityLevel); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class VirtualLinkDescDf {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    qos: ").append(toIndentedString(qos)).append("\n");
        sb.append("    serviceAvailabilityLevel: ").append(toIndentedString(serviceAvailabilityLevel)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
