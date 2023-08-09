package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Day1_2ConfigAccess {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("ssh-access")
    @Embedded
    private Day1_2SshAccess sshAccess = null;

    public Day1_2ConfigAccess sshAccess(Day1_2SshAccess sshAccess) {
        this.sshAccess = sshAccess;
        return this;
    }

    public Day1_2SshAccess getSshAccess() { return sshAccess; }

    public void setSshAccess(Day1_2SshAccess sshAccess) { this.sshAccess = sshAccess; }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Day1_2ConfigAccess day1_2ConfigAccess = (Day1_2ConfigAccess) o;

        return Objects.equals(this.sshAccess, day1_2ConfigAccess.sshAccess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sshAccess);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class Day1_2ConfigAccess {\n");
        sb.append("    sshAccess: ").append(toIndentedString(sshAccess)).append("\n");
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
