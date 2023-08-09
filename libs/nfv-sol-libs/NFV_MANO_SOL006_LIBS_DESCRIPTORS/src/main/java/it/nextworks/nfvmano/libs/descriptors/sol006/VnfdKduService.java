package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class VnfdKduService {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("mgmt-service")
    private Boolean mgmtService = null;

    @JsonProperty("external-connection-point-ref")
    private String externalConnectionPointRef = null;

    public VnfdKduService name(String name) {
        this.name = name;
        return this;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public VnfdKduService mgmtService(Boolean mgmtService) {
        this.mgmtService = mgmtService;
        return this;
    }

    public Boolean getMgmtService() { return mgmtService; }

    public void setMgmtService(Boolean mgmtService) { this.mgmtService = mgmtService; }

    public VnfdKduService externalConnectionPointRef(String externalConnectionPointRef) {
        this.externalConnectionPointRef = externalConnectionPointRef;
        return this;
    }

    public String getExternalConnectionPointRef() { return externalConnectionPointRef; }

    public void setExternalConnectionPointRef(String externalConnectionPointRef) {
        this.externalConnectionPointRef = externalConnectionPointRef;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VnfdKduService vnfdKduService = (VnfdKduService) o;

        return Objects.equals(this.name, vnfdKduService.name) &&
                Objects.equals(this.mgmtService, vnfdKduService.mgmtService) &&
                Objects.equals(this.externalConnectionPointRef, vnfdKduService.externalConnectionPointRef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mgmtService, externalConnectionPointRef);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class VnfdKduService {\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    mgmtService: ").append(toIndentedString(mgmtService)).append("\n");
        sb.append("    externalConnectionPointRef: ").append(toIndentedString(externalConnectionPointRef)).append("\n");
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
