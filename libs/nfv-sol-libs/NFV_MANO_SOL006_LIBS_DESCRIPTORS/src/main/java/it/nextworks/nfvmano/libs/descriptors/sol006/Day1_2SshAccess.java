package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Day1_2SshAccess {

    @JsonProperty("required")
    private Boolean required = null;

    @JsonProperty("default-user")
    private String defaultUser = null;

    public Day1_2SshAccess required(Boolean required) {
        this.required = required;
        return this;
    }

    public Boolean getRequired() { return required; }

    public void setRequired(Boolean required) { this.required = required; }

    public Day1_2SshAccess defaultUser(String defaultUser) {
        this.defaultUser = defaultUser;
        return this;
    }

    public String getDefaultUser() { return defaultUser; }

    public void setDefaultUser(String defaultUser) { this.defaultUser = defaultUser; }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Day1_2SshAccess day1_2SshAccess = (Day1_2SshAccess) o;

        return Objects.equals(this.required, day1_2SshAccess.required) &&
                Objects.equals(this.defaultUser, day1_2SshAccess.defaultUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(required, defaultUser);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class Day1_2SshAccess {\n");
        sb.append("    required: ").append(toIndentedString(required)).append("\n");
        sb.append("    defaultUser: ").append(toIndentedString(defaultUser)).append("\n");
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
