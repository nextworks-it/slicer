package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class NumaNodePolicyNodePairedThreadsIds {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("thread-a")
    private String threadA = null;

    @JsonProperty("thread-b")
    private String threadB = null;

    public NumaNodePolicyNodePairedThreadsIds threadA(String threadA) {
        this.threadA = threadA;
        return this;
    }

    public String getThreadA() { return threadA; }

    public void setThreadA(String threadA) { this.threadA = threadA; }

    public NumaNodePolicyNodePairedThreadsIds threadB(String threadB) {
        this.threadB = threadB;
        return this;
    }

    public String getThreadB() { return threadB; }

    public void setThreadB(String threadB) { this.threadB = threadB; }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NumaNodePolicyNodePairedThreadsIds numaNodePolicyNodePairedThreadsIds = (NumaNodePolicyNodePairedThreadsIds) o;

        return Objects.equals(this.threadA, numaNodePolicyNodePairedThreadsIds.threadA) &&
                Objects.equals(this.threadB, numaNodePolicyNodePairedThreadsIds.threadB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(threadA, threadB);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class NumaNodePolicyNodePairedThreadsIds {\n");
        sb.append("    threadA: ").append(toIndentedString(threadA)).append("\n");
        sb.append("    threadB: ").append(toIndentedString(threadB)).append("\n");
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
