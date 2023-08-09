package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class VifQuota {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("limit")
    private String limit = null;

    @JsonProperty("reserve")
    private String reserve = null;

    @JsonProperty("shares")
    private String shares = null;

    public VifQuota limit(String limit) {
        this.limit = limit;
        return this;
    }

    public String getLimit() { return limit; }

    public void setLimit(String limit) { this.limit = limit; }

    public VifQuota reserve(String reserve) {
        this.reserve = reserve;
        return this;
    }

    public String getReserve() { return reserve; }

    public void setReserve(String reserve) { this.reserve = reserve; }

    public VifQuota shares(String shares) {
        this.shares = shares;
        return this;
    }

    public String getShares() { return shares; }

    public void setShares(String shares) { this.shares = shares; }

    @Override
    public boolean equals(Object o) {

        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        VifQuota vifQuota = (VifQuota) o;

        return Objects.equals(this.limit, vifQuota.limit) &&
                Objects.equals(this.reserve, vifQuota.reserve) &&
                Objects.equals(this.shares, vifQuota.shares);
    }

    @Override
    public int hashCode() { return Objects.hash(limit, reserve, shares); }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("class VifQuota {\n");
        sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
        sb.append("    reserve: ").append(toIndentedString(reserve)).append("\n");
        sb.append("    shares: ").append(toIndentedString(shares)).append("\n");
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
