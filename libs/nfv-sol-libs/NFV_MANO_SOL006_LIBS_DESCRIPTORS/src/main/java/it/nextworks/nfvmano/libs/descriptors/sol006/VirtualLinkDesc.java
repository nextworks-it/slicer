package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.TestAccessEnum;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class VirtualLinkDesc {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("connectivity-type")
    @Embedded
    private ConnectivityTypeSchema connectivityTypeSchema = null;

    @JsonProperty("df")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "virtual_link_desc_fk", referencedColumnName = "uuid")
    private List<VirtualLinkDescDf> df = null;

    @JsonProperty("test-access")
    private TestAccessEnum testAccess = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("signature")
    private String signature = null;

    @JsonProperty("algorithm")
    private String algorithm = null;

    @JsonProperty("certificate")
    private String certificate = null;

    @JsonProperty("provider-network")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "provider_network_fk", referencedColumnName = "uuid")
    private ProviderNetwork providerNetwork = null;

    @JsonProperty("mgmt-network")
    private Boolean mgmtNetwork = null;

    @JsonProperty("vim-network-name")
    private String vimNetworkName = null;

    public VirtualLinkDesc id(String id) {
        this.id = id;
        return this;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public VirtualLinkDesc connectivityTypeSchema(ConnectivityTypeSchema connectivityTypeSchema) {
        this.connectivityTypeSchema = connectivityTypeSchema;
        return this;
    }

    public ConnectivityTypeSchema getConnectivityTypeSchema() { return connectivityTypeSchema; }

    public void setConnectivityTypeSchema(ConnectivityTypeSchema connectivityTypeSchema) {
        this.connectivityTypeSchema = connectivityTypeSchema;
    }

    public VirtualLinkDesc df(List<VirtualLinkDescDf> df) {
        this.df = df;
        return this;
    }

    public VirtualLinkDesc addDfItem(VirtualLinkDescDf item) {
        if(this.df == null)
            this.df = new ArrayList<>();

        this.df.add(item);
        return this;
    }

    public List<VirtualLinkDescDf> getDf() { return df; }

    public void setDf(List<VirtualLinkDescDf> df) { this.df = df; }

    public VirtualLinkDesc testAccess(TestAccessEnum testAccess) {
        this.testAccess = testAccess;
        return this;
    }

    public TestAccessEnum getTestAccess() { return testAccess; }

    public void setTestAccess(TestAccessEnum testAccess) { this.testAccess = testAccess; }

    public VirtualLinkDesc description(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public VirtualLinkDesc signature(String signature) {
        this.signature = signature;
        return this;
    }

    public String getSignature() { return signature; }

    public void setSignature(String signature) { this.signature = signature; }

    public VirtualLinkDesc algorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public String getAlgorithm() { return algorithm; }

    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }

    public VirtualLinkDesc certificate(String certificate) {
        this.certificate = certificate;
        return this;
    }

    public String getCertificate() { return certificate; }

    public void setCertificate(String certificate) { this.certificate = certificate; }

    public VirtualLinkDesc providerNetwork(ProviderNetwork providerNetwork) {
        this.providerNetwork = providerNetwork;
        return this;
    }

    public ProviderNetwork getProviderNetwork() { return providerNetwork; }

    public void setProviderNetwork(ProviderNetwork providerNetwork) { this.providerNetwork = providerNetwork; }

    public VirtualLinkDesc mgmtNetwork(Boolean mgmtNetwork) {
        this.mgmtNetwork = mgmtNetwork;
        return this;
    }

    public Boolean getMgmtNetwork() { return mgmtNetwork; }

    public void setMgmtNetwork(Boolean mgmtNetwork) { this.mgmtNetwork = mgmtNetwork; }

    public VirtualLinkDesc vimNetworkName(String vimNetworkName) {
        this.vimNetworkName = vimNetworkName;
        return this;
    }

    public String getVimNetworkName() { return vimNetworkName; }

    public void setVimNetworkName(String vimNetworkName) { this.vimNetworkName = vimNetworkName; }

    @Override
    public boolean equals(java.lang.Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        VirtualLinkDesc virtualLinkDesc = (VirtualLinkDesc) o;
        return Objects.equals(this.id, virtualLinkDesc.id) &&
                Objects.equals(this.connectivityTypeSchema, virtualLinkDesc.connectivityTypeSchema) &&
                Objects.equals(this.df, virtualLinkDesc.df) &&
                Objects.equals(this.testAccess, virtualLinkDesc.testAccess) &&
                Objects.equals(this.description, virtualLinkDesc.description) &&
                Objects.equals(this.signature, virtualLinkDesc.signature) &&
                Objects.equals(this.algorithm, virtualLinkDesc.algorithm) &&
                Objects.equals(this.certificate, virtualLinkDesc.certificate) &&
                Objects.equals(this.providerNetwork, virtualLinkDesc.providerNetwork) &&
                Objects.equals(this.mgmtNetwork, virtualLinkDesc.mgmtNetwork) &&
                Objects.equals(this.vimNetworkName, virtualLinkDesc.vimNetworkName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, connectivityTypeSchema, df, testAccess, description, signature,
                algorithm, certificate, providerNetwork, mgmtNetwork, vimNetworkName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class VirtualLinkDesc {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    connectivityTypeSchema: ").append(toIndentedString(connectivityTypeSchema)).append("\n");
        sb.append("    df: ").append(toIndentedString(df)).append("\n");
        sb.append("    testAccess: ").append(toIndentedString(testAccess)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
        sb.append("    algorithm: ").append(toIndentedString(algorithm)).append("\n");
        sb.append("    certificate: ").append(toIndentedString(certificate)).append("\n");
        sb.append("    providerNetwork: ").append(toIndentedString(providerNetwork)).append("\n");
        sb.append("    mgmtNetwork: ").append(toIndentedString(mgmtNetwork)).append("\n");
        sb.append("    vimNetworkName: ").append(toIndentedString(vimNetworkName)).append("\n");
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
