package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.CniEnum;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class K8sCluster {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("version")
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> version = null;

    @JsonProperty("cni")
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CniEnum> cni = null;

    @JsonProperty("nets")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "k8s_cluster_fk", referencedColumnName = "uuid")
    private List<K8sClusterNet> nets = null;

    public K8sCluster version(List<String> version) {
        this.version = version;
        return this;
    }

    public K8sCluster addVersionItem(String version) {
        if(this.version == null)
            this.version = new ArrayList<>();

        this.version.add(version);
        return this;
    }

    public List<String> getVersion() { return version; }

    public void setVersion(List<String> version) { this.version = version; }

    public K8sCluster cni(List<CniEnum> cni) {
        this.cni = cni;
        return this;
    }

    public K8sCluster addCniItem(CniEnum cni) {
        if(this.cni == null)
            this.cni = new ArrayList<>();

        this.cni.add(cni);
        return this;
    }

    public List<CniEnum> getCni() { return cni; }

    public void setCni(List<CniEnum> cni) { this.cni = cni; }

    public K8sCluster nets(List<K8sClusterNet> nets) {
        this.nets = nets;
        return this;
    }

    public K8sCluster addNetsItem(K8sClusterNet net) {
        if(this.nets == null)
            this.nets = new ArrayList<>();

        this.nets.add(net);
        return this;
    }

    public List<K8sClusterNet> getNets() { return nets; }

    public void setNets(List<K8sClusterNet> nets) { this.nets = nets; }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        K8sCluster k8sCluster = (K8sCluster) o;

        return Objects.equals(this.version, k8sCluster.version) &&
                Objects.equals(this.cni, k8sCluster.cni) &&
                Objects.equals(this.nets, k8sCluster.nets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, cni, nets);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class K8sCluster {\n");
        sb.append("    version: ").append(toIndentedString(version)).append("\n");
        sb.append("    cni: ").append(toIndentedString(cni)).append("\n");
        sb.append("    nets: ").append(toIndentedString(nets)).append("\n");
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
