package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.InterfaceTypeEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class VnirsVirtualInterface {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("type")
    private InterfaceTypeEnum type;

    @JsonProperty("vpci")
    private String vpci;

    @JsonProperty("bandwidth")
    private String bandwidth;

    public VnirsVirtualInterface type(InterfaceTypeEnum type) {
        this.type = type;
        return this;
    }

    public InterfaceTypeEnum getType() { return type; }

    public void setType(InterfaceTypeEnum type) { this.type = type; }

    public VnirsVirtualInterface vpci(String vpci) {
        this.vpci = vpci;
        return this;
    }

    public String getVpci() { return vpci; }

    public void setVpci(String vpci) { this.vpci = vpci; }

    public VnirsVirtualInterface bandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
        return this;
    }

    public String getBandwidth() { return bandwidth; }

    public void setBandwidth(String bandwidth) { this.bandwidth = bandwidth; }

    @Override
    public boolean equals(Object o) {

        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        VnirsVirtualInterface vnirsVirtualInterface = (VnirsVirtualInterface) o;

        return Objects.equals(this.type, vnirsVirtualInterface.type) &&
                Objects.equals(this.vpci, vnirsVirtualInterface.vpci) &&
                Objects.equals(this.bandwidth, vnirsVirtualInterface.bandwidth);
    }

    @Override
    public int hashCode() { return Objects.hash(type, vpci, bandwidth); }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("class VirtualInterface {\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    vpci: ").append(toIndentedString(vpci)).append("\n");
        sb.append("    bandwidth: ").append(toIndentedString(bandwidth)).append("\n");
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
