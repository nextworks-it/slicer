package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class RelationEntities {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("endpoint")
    private String endpoint = null;

    public RelationEntities id(String id) {
        this.id = id;
        return this;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public RelationEntities endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public String getEndpoint() { return endpoint; }

    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RelationEntities relationEntities = (RelationEntities) o;

        return Objects.equals(this.id, relationEntities.id) &&
                Objects.equals(this.endpoint, relationEntities.endpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, endpoint);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class RelationEntities {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    endpoint: ").append(toIndentedString(endpoint)).append("\n");
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
