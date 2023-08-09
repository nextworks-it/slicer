package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.PortSecurityDisableStrategyEnum;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class ExtCpd {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("int-virtual-link-desc")
    private String intVirtualLinkDesc = null;

    @JsonProperty("int-cpd")
    @Embedded
    private IntCpdSchema intCpdSchema = null;

    @JsonProperty("k8s-cluster-net")
    private String k8sClusterNet = null;

    @JsonProperty("virtual-network-interface-requirement")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "ext_cpd_fk", referencedColumnName = "uuid")
    private List<VirtualNetworkInterfaceRequirementSchema> virtualNetworkInterfaceRequirementSchemas = null;

    @JsonProperty("nicio-requirements")
    private String nicIORequirements = null;

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("layer-protocol")
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> layerProtocols = null;

    @JsonProperty("role")
    private String role = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("protocol")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "ext_cpd_fk", referencedColumnName = "uuid")
    private List<ProtocolSchema> protocolSchemas = null;

    @JsonProperty("trunk-mode")
    private Boolean trunkMode = null;

    @JsonProperty("security-group-rule-id")
    private String securityGroupRuleId = null;

    @JsonProperty("port-security-enabled")
    private Boolean portSecurityEnabled = null;

    @JsonProperty("port-security-disable-strategy")
    private PortSecurityDisableStrategyEnum portSecurityDisableStrategy = null;

    public ExtCpd intVirtualLinkDesc(String intVirtualLinkDesc) {
        this.intVirtualLinkDesc = intVirtualLinkDesc;
        return this;
    }

    public String getIntVirtualLinkDesc() { return intVirtualLinkDesc; }

    public void setIntVirtualLinkDesc(String intVirtualLinkDesc) {
        this.intVirtualLinkDesc = intVirtualLinkDesc;
    }

    public ExtCpd intCpdSchema(IntCpdSchema intCpdSchema) {
        this.intCpdSchema = intCpdSchema;
        return this;
    }

    public IntCpdSchema getIntCpdSchema() { return intCpdSchema; }

    public void setIntCpdSchema(IntCpdSchema intCpdSchema) {
        this.intCpdSchema = intCpdSchema;
    }

    public ExtCpd virtualNetworkInterfaceRequirementSchemas(List<VirtualNetworkInterfaceRequirementSchema>
                                                                    virtualNetworkInterfaceRequirementSchemas) {
        this.virtualNetworkInterfaceRequirementSchemas = virtualNetworkInterfaceRequirementSchemas;
        return this;
    }

    public ExtCpd addVirtualNetworkInterfaceRequirementSchemaItem(VirtualNetworkInterfaceRequirementSchema item) {
        if(this.virtualNetworkInterfaceRequirementSchemas == null)
            this.virtualNetworkInterfaceRequirementSchemas = new ArrayList<>();

        this.virtualNetworkInterfaceRequirementSchemas.add(item);
        return this;
    }

    public List<VirtualNetworkInterfaceRequirementSchema> getVirtualNetworkInterfaceRequirementSchemas() {
        return virtualNetworkInterfaceRequirementSchemas;
    }

    public void setVirtualNetworkInterfaceRequirementSchemas(List<VirtualNetworkInterfaceRequirementSchema> virtualNetworkInterfaceRequirementSchemas) {
        this.virtualNetworkInterfaceRequirementSchemas = virtualNetworkInterfaceRequirementSchemas;
    }

    public ExtCpd nicIORequirements(String nicIORequirements) {
        this.nicIORequirements = nicIORequirements;
        return this;
    }

    public String getNicIORequirements() { return nicIORequirements; }

    public void setNicIORequirements(String nicIORequirements) { this.nicIORequirements = nicIORequirements; }

    public ExtCpd id(String id) {
        this.id = id;
        return this;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public ExtCpd layerProtocols(List<String> layerProtocols) {
        this.layerProtocols = layerProtocols;
        return this;
    }

    public ExtCpd addLayerProtocolItem(String item) {
        if(this.layerProtocols == null)
            this.layerProtocols = new ArrayList<>();

        this.layerProtocols.add(item);
        return this;
    }

    public List<String> getLayerProtocols() { return layerProtocols; }

    public void setLayerProtocols(List<String> layerProtocols) { this.layerProtocols = layerProtocols; }

    public ExtCpd role(String role) {
        this.role = role;
        return this;
    }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public ExtCpd description(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public ExtCpd protocolSchemas(List<ProtocolSchema> protocolSchemas) {
        this.protocolSchemas = protocolSchemas;
        return this;
    }

    public ExtCpd addProtocolSchemaItem(ProtocolSchema item) {
        if(this.protocolSchemas == null)
            this.protocolSchemas = new ArrayList<>();

        this.protocolSchemas.add(item);
        return this;
    }

    public List<ProtocolSchema> getProtocolSchemas() { return protocolSchemas; }

    public void setProtocolSchemas(List<ProtocolSchema> protocolSchemas) { this.protocolSchemas = protocolSchemas; }

    public ExtCpd trunkMode(Boolean trunkMode) {
        this.trunkMode = trunkMode;
        return this;
    }

    public Boolean getTrunkMode() { return trunkMode; }

    public void setTrunkMode(Boolean trunkMode) { this.trunkMode = trunkMode; }

    public ExtCpd securityGroupRuleId(String securityGroupRuleId) {
        this.securityGroupRuleId = securityGroupRuleId;
        return this;
    }

    public String getSecurityGroupRuleId() { return securityGroupRuleId; }

    public void setSecurityGroupRuleId(String securityGroupRuleId) { this.securityGroupRuleId = securityGroupRuleId; }

    public ExtCpd k8sClusterNet(String k8sClusterNet) {
        this.k8sClusterNet = k8sClusterNet;
        return this;
    }

    public String getK8sClusterNet() { return k8sClusterNet; }

    public void setK8sClusterNet(String k8sClusterNet) { this.k8sClusterNet = k8sClusterNet; }

    public ExtCpd portSecurityEnabled(Boolean portSecurityEnabled) {
        this.portSecurityEnabled = portSecurityEnabled;
        return this;
    }

    public Boolean getPortSecurityEnabled() { return portSecurityEnabled; }

    public void setPortSecurityEnabled(Boolean portSecurityEnabled) { this.portSecurityEnabled = portSecurityEnabled; }

    public ExtCpd portSecurityDisableStrategy(PortSecurityDisableStrategyEnum portSecurityDisableStrategy) {
        this.portSecurityDisableStrategy = portSecurityDisableStrategy;
        return this;
    }

    public PortSecurityDisableStrategyEnum getPortSecurityDisableStrategy() { return portSecurityDisableStrategy; }

    public void setPortSecurityDisableStrategy(PortSecurityDisableStrategyEnum portSecurityDisableStrategy) {
        this.portSecurityDisableStrategy = portSecurityDisableStrategy;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        ExtCpd extCpd = (ExtCpd) o;
        return Objects.equals(this.intVirtualLinkDesc, extCpd.intVirtualLinkDesc) &&
                Objects.equals(this.intCpdSchema, extCpd.intCpdSchema) &&
                Objects.equals(this.virtualNetworkInterfaceRequirementSchemas, extCpd.virtualNetworkInterfaceRequirementSchemas) &&
                Objects.equals(this.nicIORequirements, extCpd.nicIORequirements) &&
                Objects.equals(this.id, extCpd.id) &&
                Objects.equals(this.layerProtocols, extCpd.layerProtocols) &&
                Objects.equals(this.role, extCpd.role) &&
                Objects.equals(this.description, extCpd.description) &&
                Objects.equals(this.protocolSchemas, extCpd.protocolSchemas) &&
                Objects.equals(this.trunkMode, extCpd.trunkMode) &&
                Objects.equals(this.securityGroupRuleId, extCpd.securityGroupRuleId) &&
                Objects.equals(this.k8sClusterNet, extCpd.k8sClusterNet) &&
                Objects.equals(this.portSecurityEnabled, extCpd.portSecurityEnabled) &&
                Objects.equals(this.portSecurityDisableStrategy, extCpd.portSecurityDisableStrategy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(intVirtualLinkDesc, intCpdSchema, virtualNetworkInterfaceRequirementSchemas,
                nicIORequirements, id, layerProtocols, role, description, protocolSchemas, trunkMode,
                securityGroupRuleId, k8sClusterNet, portSecurityEnabled, portSecurityDisableStrategy);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class ExtCpd {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    intVirtualLinkDesc: ").append(toIndentedString(intVirtualLinkDesc)).append("\n");
        sb.append("    intCpdSchema: ").append(toIndentedString(intCpdSchema)).append("\n");
        sb.append("    virtualNetworkInterfaceRequirementSchemas: ")
                .append(toIndentedString(virtualNetworkInterfaceRequirementSchemas)).append("\n");
        sb.append("    nicIORequirements: ").append(toIndentedString(nicIORequirements)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    layerProtocols: ").append(toIndentedString(layerProtocols)).append("\n");
        sb.append("    role: ").append(toIndentedString(role)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    protocolSchemas: ").append(toIndentedString(protocolSchemas)).append("\n");
        sb.append("    trunkMode: ").append(toIndentedString(trunkMode)).append("\n");
        sb.append("    securityGroupRuleId: ").append(toIndentedString(securityGroupRuleId)).append("\n");
        sb.append("    k8sClusterNet: ").append(toIndentedString(k8sClusterNet)).append("\n");
        sb.append("    portSecurityEnabled: ").append(toIndentedString(portSecurityEnabled)).append("\n");
        sb.append("    portSecurityDisableStrategy: ").append(toIndentedString(portSecurityDisableStrategy)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if(o == null)
            return "null";

        return o.toString().replace("\n", "\n    ");
    }
}
