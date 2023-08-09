package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class KduResourceDelta {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("number-of-instances")
    private String numberOfInstances = null;

    public KduResourceDelta id(String id) {
        this.id = id;
        return this;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public KduResourceDelta numberOfInstances(String numberOfInstances) {
        this.numberOfInstances = numberOfInstances;
        return this;
    }

    public String getNumberOfInstances() { return numberOfInstances; }

    public void setNumberOfInstances(String numberOfInstances) { this.numberOfInstances = numberOfInstances; }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KduResourceDelta kduResourceDelta = (KduResourceDelta) o;

        return Objects.equals(this.id, kduResourceDelta.id) &&
                Objects.equals(this.numberOfInstances, kduResourceDelta.numberOfInstances);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfInstances);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class KduResourceDelta {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    numberOfInstances: ").append(toIndentedString(numberOfInstances)).append("\n");
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
