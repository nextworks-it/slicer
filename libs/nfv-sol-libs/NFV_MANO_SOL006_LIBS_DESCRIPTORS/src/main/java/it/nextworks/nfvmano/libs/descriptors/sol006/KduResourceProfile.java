package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class KduResourceProfile {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("min-number-of-instances")
    private String minNumberOfInstances = null;

    @JsonProperty("max-number-of-instances")
    private String maxNumberOfInstances = null;

    @JsonProperty("resource-name")
    private String resourceName = null;

    @JsonProperty("kdu-name")
    private String kduName = null;

    public KduResourceProfile id(String id) {
        this.id = id;
        return this;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public KduResourceProfile minNumberOfInstances(String minNumberOfInstances) {
        this.minNumberOfInstances = minNumberOfInstances;
        return this;
    }

    public String getMinNumberOfInstances() { return minNumberOfInstances; }

    public void setMinNumberOfInstances(String minNumberOfInstances) { this.minNumberOfInstances = minNumberOfInstances; }

    public KduResourceProfile maxNumberOfInstances(String maxNumberOfInstances) {
        this.maxNumberOfInstances = maxNumberOfInstances;
        return this;
    }

    public String getMaxNumberOfInstances() { return maxNumberOfInstances; }

    public void setMaxNumberOfInstances(String maxNumberOfInstances) {this.maxNumberOfInstances = maxNumberOfInstances; }

    public KduResourceProfile resourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public String getResourceName() { return resourceName; }

    public void setResourceName(String resourceName) { this.resourceName = resourceName; }

    public KduResourceProfile kduName(String kduName) {
        this.kduName = kduName;
        return this;
    }

    public String getKduName() { return kduName; }

    public void setKduName(String kduName) { this.kduName = kduName; }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KduResourceProfile kduResourceProfile = (KduResourceProfile) o;

        return Objects.equals(this.id, kduResourceProfile.id) &&
                Objects.equals(this.minNumberOfInstances, kduResourceProfile.minNumberOfInstances) &&
                Objects.equals(this.maxNumberOfInstances, kduResourceProfile.maxNumberOfInstances) &&
                Objects.equals(this.resourceName, kduResourceProfile.resourceName) &&
                Objects.equals(this.kduName, kduResourceProfile.kduName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, minNumberOfInstances, maxNumberOfInstances, resourceName, kduName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class KduResourceProfile {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    minNumberOfInstances: ").append(toIndentedString(minNumberOfInstances)).append("\n");
        sb.append("    maxNumberOfInstances: ").append(toIndentedString(maxNumberOfInstances)).append("\n");
        sb.append("    resourceName: ").append(toIndentedString(resourceName)).append("\n");
        sb.append("    kduName: ").append(toIndentedString(kduName)).append("\n");
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
