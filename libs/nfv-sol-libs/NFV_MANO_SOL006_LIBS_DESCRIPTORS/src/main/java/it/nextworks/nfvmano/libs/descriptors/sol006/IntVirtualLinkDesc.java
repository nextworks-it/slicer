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
public class IntVirtualLinkDesc {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("flavour")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "int_virtual_link_desc_fk", referencedColumnName = "uuid")
    private List<FlavourSchema> flavourSchemas = null;

    @JsonProperty("connectivity-type")
    @Embedded
    private ConnectivityTypeSchema connectivityTypeSchema = null;

    @JsonProperty("test-access")
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> testAccess = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("monitoring-parameters")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "int_virtual_link_desc_fk", referencedColumnName = "uuid")
    private List<MonitoringParameterSchema> monitoringParameters = null;

    @JsonProperty("provider-network")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "provider_network_fk", referencedColumnName = "uuid")
    private ProviderNetwork providerNetwork = null;

    public IntVirtualLinkDesc id(String id) {
        this.id = id;
        return this;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public IntVirtualLinkDesc flavourSchemas(List<FlavourSchema> flavourSchemas) {
        this.flavourSchemas = flavourSchemas;
        return this;
    }

    public IntVirtualLinkDesc addFlavourSchemaItem(FlavourSchema item) {
        if(this.flavourSchemas == null)
            this.flavourSchemas = new ArrayList<>();

        this.flavourSchemas.add(item);
        return this;
    }

    public List<FlavourSchema> getFlavourSchemas() { return flavourSchemas; }

    public void setFlavourSchemas(List<FlavourSchema> flavourSchemas) { this.flavourSchemas = flavourSchemas; }

    public IntVirtualLinkDesc connectivityTypeSchema(ConnectivityTypeSchema connectivityTypeSchema) {
        this.connectivityTypeSchema = connectivityTypeSchema;
        return this;
    }

    public ConnectivityTypeSchema getConnectivityTypeSchema() { return connectivityTypeSchema; }

    public void setConnectivityTypeSchema(ConnectivityTypeSchema connectivityTypeSchema) {
        this.connectivityTypeSchema = connectivityTypeSchema;
    }

    public IntVirtualLinkDesc testAccess(List<String> testAccess) {
        this.testAccess = testAccess;
        return this;
    }

    public IntVirtualLinkDesc addTestAccessItem(String item) {
        if(this.testAccess == null)
            this.testAccess = new ArrayList<>();

        this.testAccess.add(item);
        return this;
    }

    public List<String> getTestAccess() { return testAccess; }

    public void setTestAccess(List<String> testAccess) { this.testAccess = testAccess; }

    public IntVirtualLinkDesc description(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public IntVirtualLinkDesc monitoringParameters(List<MonitoringParameterSchema> monitoringParameters) {
        this.monitoringParameters = monitoringParameters;
        return this;
    }

    public IntVirtualLinkDesc addMonitoringParameterItem(MonitoringParameterSchema item) {
        if(this.monitoringParameters == null)
            this.monitoringParameters = new ArrayList<>();

        this.monitoringParameters.add(item);
        return this;
    }

    public List<MonitoringParameterSchema> getMonitoringParameters() { return monitoringParameters; }

    public void setMonitoringParameters(List<MonitoringParameterSchema> monitoringParameters) {
        this.monitoringParameters = monitoringParameters;
    }

    public IntVirtualLinkDesc providerNetwork(ProviderNetwork providerNetwork) {
        this.providerNetwork = providerNetwork;
        return this;
    }

    public ProviderNetwork getProviderNetwork() { return providerNetwork; }

    public void setProviderNetwork(ProviderNetwork providerNetwork) { this.providerNetwork = providerNetwork; }

    @Override
    public boolean equals(java.lang.Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        IntVirtualLinkDesc intVirtualLinkDesc = (IntVirtualLinkDesc) o;
        return Objects.equals(this.id, intVirtualLinkDesc.id) &&
                Objects.equals(this.flavourSchemas, intVirtualLinkDesc.flavourSchemas) &&
                Objects.equals(this.connectivityTypeSchema, intVirtualLinkDesc.connectivityTypeSchema) &&
                Objects.equals(this.testAccess, intVirtualLinkDesc.testAccess) &&
                Objects.equals(this.description, intVirtualLinkDesc.description) &&
                Objects.equals(this.monitoringParameters, intVirtualLinkDesc.monitoringParameters) &&
                Objects.equals(this.providerNetwork, intVirtualLinkDesc.providerNetwork);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flavourSchemas, connectivityTypeSchema, testAccess, description,
                monitoringParameters, providerNetwork);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class IntVirtualLinkDesc {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    flavourSchemas: ").append(toIndentedString(flavourSchemas)).append("\n");
        sb.append("    connectivityTypeSchema: ").append(toIndentedString(connectivityTypeSchema)).append("\n");
        sb.append("    testAccess: ").append(toIndentedString(testAccess)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    monitoringParameters: ").append(toIndentedString(monitoringParameters)).append("\n");
        sb.append("    providerNetwork: ").append(toIndentedString(providerNetwork)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if(o == null)
            return "null";

        return o.toString().replace("\n", "\n    ");
    }
}
