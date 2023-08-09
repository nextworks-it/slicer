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
public class NumaNodePolicyNode {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("vcpu")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "numa_node_policy_node_fk", referencedColumnName = "uuid")
    private List<NumaNodePolicyNodeVcpu> vcpu = null;

    @JsonProperty("memory-mb")
    private String memoryMb = null;

    @JsonProperty("num-cores")
    private String numCores = null;

    @JsonProperty("paired-threads")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "paired_threads_fk", referencedColumnName = "uuid")
    private NumaNodePolicyNodePairedThreads pairedThreads = null;

    @JsonProperty("num-threads")
    private String numThreads = null;

    public NumaNodePolicyNode id(String id) {
        this.id = id;
        return this;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public NumaNodePolicyNode vcpu(List<NumaNodePolicyNodeVcpu> vcpu) {
        this.vcpu = vcpu;
        return this;
    }

    public NumaNodePolicyNode addVcpuItem(NumaNodePolicyNodeVcpu vcpu) {
        if(this.vcpu == null)
            this.vcpu = new ArrayList<>();

        this.vcpu.add(vcpu);
        return this;
    }

    public List<NumaNodePolicyNodeVcpu> getVcpu() { return vcpu; }

    public void setVcpu(List<NumaNodePolicyNodeVcpu> vcpu) { this.vcpu = vcpu; }

    public NumaNodePolicyNode memoryMb(String memoryMb) {
        this.memoryMb = memoryMb;
        return this;
    }

    public String getMemoryMb() { return memoryMb; }

    public void setMemoryMb(String memoryMb) { this.memoryMb = memoryMb; }

    public NumaNodePolicyNode numCores(String numCores) {
        this.numCores = numCores;
        return this;
    }

    public String getNumCores() { return numCores; }

    public void setNumCores(String numCores) { this.numCores = numCores; }

    public NumaNodePolicyNode pairedThreads(NumaNodePolicyNodePairedThreads pairedThreads) {
        this.pairedThreads = pairedThreads;
        return this;
    }

    public NumaNodePolicyNodePairedThreads getPairedThreads() { return pairedThreads; }

    public void setPairedThreads(NumaNodePolicyNodePairedThreads pairedThreads) { this.pairedThreads = pairedThreads; }

    public NumaNodePolicyNode numThreads(String numThreads) {
        this.numThreads = numThreads;
        return this;
    }

    public String getNumThreads() { return numThreads; }

    public void setNumThreads(String numThreads) { this.numThreads = numThreads; }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NumaNodePolicyNode numaNodePolicyNode = (NumaNodePolicyNode) o;

        return Objects.equals(this.id, numaNodePolicyNode.id) &&
                Objects.equals(this.vcpu, numaNodePolicyNode.vcpu) &&
                Objects.equals(this.memoryMb, numaNodePolicyNode.memoryMb) &&
                Objects.equals(this.numCores, numaNodePolicyNode.numCores) &&
                Objects.equals(this.pairedThreads, numaNodePolicyNode.pairedThreads) &&
                Objects.equals(this.numThreads, numaNodePolicyNode.numThreads);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vcpu, memoryMb, numCores, pairedThreads, numThreads);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class NumaNodePolicyNode {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    vcpu: ").append(toIndentedString(vcpu)).append("\n");
        sb.append("    memoryMb: ").append(toIndentedString(memoryMb)).append("\n");
        sb.append("    numCores: ").append(toIndentedString(numCores)).append("\n");
        sb.append("    pairedThreads: ").append(toIndentedString(pairedThreads)).append("\n");
        sb.append("    numThreads: ").append(toIndentedString(numThreads)).append("\n");
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
