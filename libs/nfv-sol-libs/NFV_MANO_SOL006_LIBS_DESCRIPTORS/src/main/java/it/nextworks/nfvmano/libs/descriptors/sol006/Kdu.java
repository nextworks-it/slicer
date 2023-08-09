package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.HelmVersionEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Kdu {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("helm-chart")
    private String helmChart = null;

    @JsonProperty("helm-version")
    private HelmVersionEnum helmVersion = null;

    @JsonProperty("juju-bundle")
    private String jujuBundle = null;

    public Kdu name(String name) {
        this.name = name;
        return this;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Kdu description(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Kdu helmChart(String helmChart) {
        this.helmChart = helmChart;
        return this;
    }

    public String getHelmChart() { return helmChart; }

    public void setHelmChart(String helmChart) { this.helmChart = helmChart; }

    public Kdu helmVersion(HelmVersionEnum helmVersion) {
        this.helmVersion = helmVersion;
        return this;
    }

    public HelmVersionEnum getHelmVersion() { return helmVersion; }

    public void setHelmVersion(HelmVersionEnum helmVersion) { this.helmVersion = helmVersion; }

    public Kdu jujuBundle(String jujuBundle) {
        this.jujuBundle = jujuBundle;
        return this;
    }

    public String getJujuBundle() { return jujuBundle; }

    public void setJujuBundle(String jujuBundle) { this.jujuBundle = jujuBundle; }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Kdu kdu = (Kdu) o;

        return Objects.equals(this.name, kdu.name) &&
                Objects.equals(this.description, kdu.description) &&
                Objects.equals(this.helmChart, kdu.helmChart) &&
                Objects.equals(this.helmVersion, kdu.helmVersion) &&
                Objects.equals(this.jujuBundle, kdu.jujuBundle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, helmChart, helmVersion, jujuBundle);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class Kdu {\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    helmChart: ").append(toIndentedString(helmChart)).append("\n");
        sb.append("    helmVersion: ").append(toIndentedString(helmVersion)).append("\n");
        sb.append("    jujuBundle: ").append(toIndentedString(jujuBundle)).append("\n");
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
