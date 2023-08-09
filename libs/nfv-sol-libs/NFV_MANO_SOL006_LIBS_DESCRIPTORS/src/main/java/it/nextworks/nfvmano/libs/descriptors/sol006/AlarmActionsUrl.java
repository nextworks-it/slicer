package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class AlarmActionsUrl {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("url")
    private String url = null;

    public AlarmActionsUrl url(String url) {
        this.url = url;
        return this;
    }

    public String getUrl() { return url; }

    public void setUuid(String uuid) { this.uuid = uuid; }

    @Override
    public boolean equals(Object o) {

        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        AlarmActionsUrl alarmActionsUrl = (AlarmActionsUrl) o;

        return Objects.equals(this.url, alarmActionsUrl.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class AlarmActionsUrl {\n");
        sb.append("    url: ").append(toIndentedString(url)).append("\n");
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
