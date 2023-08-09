package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.MemPolicyEnum;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class NumaNodePolicy {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("node-cnt")
    private String nodeCnt = null;

    @JsonProperty("mem-policy")
    private MemPolicyEnum memPolicy = null;

    @JsonProperty("node")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "numa_node_policy_fk", referencedColumnName = "uuid")
    private List<NumaNodePolicyNode> node = null;

    public NumaNodePolicy nodeCnt(String nodeCnt) {
        this.nodeCnt = nodeCnt;
        return this;
    }

    public String getNodeCnt() { return nodeCnt; }

    public void setNodeCnt(String nodeCnt) { this.nodeCnt = nodeCnt; }

    public NumaNodePolicy memPolicy(MemPolicyEnum memPolicy) {
        this.memPolicy = memPolicy;
        return this;
    }

    public MemPolicyEnum getMemPolicy() { return memPolicy; }

    public void setMemPolicy(MemPolicyEnum memPolicy) { this.memPolicy = memPolicy; }

    public NumaNodePolicy node(List<NumaNodePolicyNode> node) {
        this.node = node;
        return this;
    }

    public NumaNodePolicy addNodeItem(NumaNodePolicyNode node) {
        if(this.node == null)
            this.node = new ArrayList<>();

        this.node.add(node);
        return this;
    }

    public List<NumaNodePolicyNode> getNode() { return node; }

    public void setNode(List<NumaNodePolicyNode> node) { this.node = node; }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NumaNodePolicy numaNodePolicy = (NumaNodePolicy) o;

        return Objects.equals(this.nodeCnt, numaNodePolicy.nodeCnt) &&
                Objects.equals(this.memPolicy, numaNodePolicy.memPolicy) &&
                Objects.equals(this.node, numaNodePolicy.node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeCnt, memPolicy, node);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class NumaNodePolicy {\n");
        sb.append("    nodeCnt: ").append(toIndentedString(nodeCnt)).append("\n");
        sb.append("    memPolicy: ").append(toIndentedString(memPolicy)).append("\n");
        sb.append("    node: ").append(toIndentedString(node)).append("\n");
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
