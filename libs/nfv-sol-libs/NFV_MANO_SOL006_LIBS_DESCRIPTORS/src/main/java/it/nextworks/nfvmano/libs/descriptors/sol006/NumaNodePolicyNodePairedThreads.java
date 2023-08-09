package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class NumaNodePolicyNodePairedThreads {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("num-paired-threads")
    private String numPairedThreads = null;

    @JsonProperty("paired-thread-ids")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "numa_node_policy_node_paired_threads_fk", referencedColumnName = "uuid")
    private List<NumaNodePolicyNodePairedThreadsIds> pairedThreadIds = null;

    public NumaNodePolicyNodePairedThreads numPairedThreads(String numPairedThreads) {
        this.numPairedThreads = numPairedThreads;
        return this;
    }

    public String getNumPairedThreads() { return numPairedThreads; }

    public void setNumPairedThreads(String numPairedThreads) { this.numPairedThreads = numPairedThreads; }

    public NumaNodePolicyNodePairedThreads pairedThreadIds(List<NumaNodePolicyNodePairedThreadsIds> pairedThreadIds) {
        this.pairedThreadIds = pairedThreadIds;
        return this;
    }

    public NumaNodePolicyNodePairedThreads addPairedThreadIdsItem(NumaNodePolicyNodePairedThreadsIds numaNodePolicyNodePairedThreadsIds) {
        if(this.pairedThreadIds == null)
            this.pairedThreadIds = new ArrayList<>();

        this.pairedThreadIds.add(numaNodePolicyNodePairedThreadsIds);
        return this;
    }

    public List<NumaNodePolicyNodePairedThreadsIds> getPairedThreadIds() { return pairedThreadIds; }

    public void setPairedThreadIds(List<NumaNodePolicyNodePairedThreadsIds> pairedThreadIds) {
        this.pairedThreadIds = pairedThreadIds;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NumaNodePolicyNodePairedThreads numaNodePolicyNodePairedThreads = (NumaNodePolicyNodePairedThreads) o;

        return Objects.equals(this.numPairedThreads, numaNodePolicyNodePairedThreads.numPairedThreads) &&
                Objects.equals(this.pairedThreadIds, numaNodePolicyNodePairedThreads.pairedThreadIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numPairedThreads, pairedThreadIds);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class NumaNodePolicyNodePairedThreads {\n");
        sb.append("    numPairedThreads: ").append(toIndentedString(numPairedThreads)).append("\n");
        sb.append("    pairedThreadIds: ").append(toIndentedString(pairedThreadIds)).append("\n");
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
