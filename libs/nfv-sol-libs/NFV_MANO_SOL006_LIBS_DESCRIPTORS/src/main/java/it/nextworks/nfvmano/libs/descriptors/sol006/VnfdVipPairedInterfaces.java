package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class VnfdVipPairedInterfaces {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("vdu-id-ref")
    private String vduIdRef = null;

    @JsonProperty("interface-ref")
    private String interfaceRef = null;

    public VnfdVipPairedInterfaces vduIdRef(String vduIdRef) {
        this.vduIdRef = vduIdRef;
        return this;
    }

    public String getVduIdRef() { return vduIdRef; }

    public void setVduIdRef(String vduIdRef) { this.vduIdRef = vduIdRef; }

    public VnfdVipPairedInterfaces interfaceRef(String interfaceRef) {
        this.interfaceRef = interfaceRef;
        return this;
    }

    public String getInterfaceRef() { return interfaceRef; }

    public void setInterfaceRef(String interfaceRef) { this.interfaceRef = interfaceRef; }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VnfdVipPairedInterfaces vnfdVipPairedInterfaces = (VnfdVipPairedInterfaces) o;

        return Objects.equals(this.vduIdRef, vnfdVipPairedInterfaces.vduIdRef) &&
                Objects.equals(this.interfaceRef, vnfdVipPairedInterfaces.interfaceRef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vduIdRef, interfaceRef);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class VnfdVipPairedInterfaces {\n");
        sb.append("    vduIdRef: ").append(toIndentedString(vduIdRef)).append("\n");
        sb.append("    interfaceRef: ").append(toIndentedString(interfaceRef)).append("\n");
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
