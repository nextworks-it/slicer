package it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.translators;

import it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.SODriver;
import it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.elements.SoVnfInfoObject;
import it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.elements.SoVnfQueryResponse;
import it.nextworks.nfvmano.libs.common.enums.CpRole;
import it.nextworks.nfvmano.libs.common.enums.FlowPattern;
import it.nextworks.nfvmano.libs.common.enums.LayerProtocol;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.descriptors.capabilities.VirtualComputeCapability;
import it.nextworks.nfvmano.libs.descriptors.capabilities.VirtualComputeCapabilityProperties;
import it.nextworks.nfvmano.libs.descriptors.elements.*;
import it.nextworks.nfvmano.libs.descriptors.elements.VnfProfile;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NS.NSInterfaces;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NS.NSNode;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NS.NSProperties;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NS.NSRequirements;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NsVirtualLink.NsVirtualLinkNode;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NsVirtualLink.NsVirtualLinkProperties;
import it.nextworks.nfvmano.libs.descriptors.templates.*;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VDU.*;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF.VNFNode;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF.VNFProperties;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF.VNFRequirements;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VnfExtCp.VnfExtCpNode;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VnfExtCp.VnfExtCpProperties;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VnfExtCp.VnfExtCpRequirements;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.SwImageDesc;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualComputeDesc;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualLinkProfile;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualStorageDesc;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.*;
import it.nextworks.nfvmano.libs.ifa.descriptors.vnfd.Vdu;
import it.nextworks.nfvmano.libs.ifa.descriptors.vnfd.VnfDf;
import it.nextworks.nfvmano.libs.ifa.descriptors.vnfd.VnfExtCpd;
import it.nextworks.nfvmano.libs.ifa.descriptors.vnfd.Vnfd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class IfaToSolTranslator {

    private static final Logger log = LoggerFactory.getLogger(IfaToSolTranslator.class);

    public static DescriptorTemplate generateVnfDescriptor(Vnfd vnfdIfa) throws IllegalArgumentException {
        String id = vnfdIfa.getVnfdId();
        if(id == null)
            throw new IllegalArgumentException("Descriptor without ID");
        String version = vnfdIfa.getVnfdVersion();
        String description = vnfdIfa.getVnfProductInfoDescription();
        String resourceVendor = vnfdIfa.getVnfProvider();
        String name = vnfdIfa.getVnfProductName();
        if(name == null)
            throw new IllegalArgumentException("Descriptor without name");
        String resourceVendorRelease = vnfdIfa.getVnfSoftwareVersion();
        VnfDf df = vnfdIfa.getDeploymentFlavour().get(0); //Consider only one df

        LinkedHashMap<String, Node> nodeTemplates = new LinkedHashMap<>();

        log.debug("Creating VnfExtCpNode");
        //Creating VnfExtCpNode and VirtualinkRequirements for SubstitutionMappingsRequirements
        List<VirtualLinkPair> virtualLink = new ArrayList<>();
        boolean mgmtFound = false;
        String cpName = null;
        for(VnfExtCpd cp : vnfdIfa.getVnfExtCpd()){
            cpName = cp.getCpdId();
            virtualLink.add(new VirtualLinkPair(cpName, cpName));
            List<LayerProtocol> layerProtocols = new ArrayList<>();
            layerProtocols.add(LayerProtocol.IPV4);
            List<CpProtocolData> protocolData = new ArrayList<>();
            protocolData.add(new CpProtocolData(LayerProtocol.IPV4, null));
            List<VirtualNetworkInterfaceRequirements> virtualNetworkInterfaceRequirements = new ArrayList<>();
            HashMap<String, String> interfaceRequirements = new HashMap<>();
            it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.AddressData addressData = cp.getAddressData().get(0); //Consider only one address data
            if(addressData.isManagement()) {
                mgmtFound = true;
                interfaceRequirements.put("isManagement", "true");
            }
            VirtualNetworkInterfaceRequirements virtualNetworkInterfaceRequirement = new VirtualNetworkInterfaceRequirements(null, null, false, interfaceRequirements, null);
            virtualNetworkInterfaceRequirements.add(virtualNetworkInterfaceRequirement);
            VnfExtCpProperties cpProperties = new VnfExtCpProperties(null, layerProtocols, CpRole.LEAF, cp.getDescription(), protocolData, false, virtualNetworkInterfaceRequirements);
            List<String> externalVirtualLink = new ArrayList<>();
            externalVirtualLink.add(cpName);
            VnfExtCpRequirements cpRequirements = new  VnfExtCpRequirements(externalVirtualLink, null);
            nodeTemplates.put(cpName, new VnfExtCpNode(null, cpProperties, cpRequirements));//type: "tosca.nodes.nfv.VnfExtCp"
        }

        //select ta random cp as mgmt
        if(!mgmtFound){
            List<LayerProtocol> layerProtocols = new ArrayList<>();
            layerProtocols.add(LayerProtocol.IPV4);
            List<CpProtocolData> protocolData = new ArrayList<>();
            protocolData.add(new CpProtocolData(LayerProtocol.IPV4, null));
            List<VirtualNetworkInterfaceRequirements> virtualNetworkInterfaceRequirements = new ArrayList<>();
            HashMap<String, String> interfaceRequirements = new HashMap<>();
            interfaceRequirements.put("isManagement", "true");
            VirtualNetworkInterfaceRequirements virtualNetworkInterfaceRequirement = new VirtualNetworkInterfaceRequirements(null, null, false, interfaceRequirements, null);
            virtualNetworkInterfaceRequirements.add(virtualNetworkInterfaceRequirement);
            VnfExtCpProperties cpProperties = new VnfExtCpProperties(null, layerProtocols, CpRole.LEAF, cpName, protocolData, false, virtualNetworkInterfaceRequirements);
            List<String> externalVirtualLink = new ArrayList<>();
            externalVirtualLink.add(cpName);
            VnfExtCpRequirements cpRequirements = new  VnfExtCpRequirements(externalVirtualLink, null);
            nodeTemplates.replace(cpName, new VnfExtCpNode(null, cpProperties, cpRequirements));
        }

        List<Vdu> vduNodes = vnfdIfa.getVdu();
        for(Vdu vduNode : vduNodes) {
            SwImageDesc imageDesc = vduNode.getSwImageDesc();
            VirtualComputeDesc virtualComputeDesc = vnfdIfa.getVirtualComputeDesc().stream().filter(computeDesc -> vduNode.getVirtualComputeDesc().equals(computeDesc.getVirtualComputeDescId())).findAny().orElse(null);
            if(virtualComputeDesc == null)//VDU without virtualComputeDesc specified
                continue;
            List<VirtualStorageDesc> virtualStorageDescList = vnfdIfa.getVirtualStorageDesc().stream().filter(storageDesc -> vduNode.getVirtualStorageDesc().contains(storageDesc.getStorageId())).collect(Collectors.toList());
            String imageName = imageDesc.getSwImage();
            Integer vRam = virtualComputeDesc.getVirtualMemory().getVirtualMemSize();
            Integer vCpu = virtualComputeDesc.getVirtualCpu().getNumVirtualCpu();

            String vduId = vduNode.getVduId();
            log.debug("Creating VDUVirtualBlockStorageNode for vdu {}", vduId);
            for(VirtualStorageDesc virtualStorageDesc : virtualStorageDescList) {
                //Creating  VDUVirtualBlockStorageNode
                VirtualBlockStorageData virtualBlockStorageData = new VirtualBlockStorageData(virtualStorageDesc.getSizeOfStorage(), null, false);
                VDUVirtualBlockStorageProperties bsProperties = new VDUVirtualBlockStorageProperties(virtualBlockStorageData, null);
                nodeTemplates.put(virtualStorageDesc.getStorageId(), new VDUVirtualBlockStorageNode(null, bsProperties));//type: "tosca.nodes.nfv.Vdu.VirtualBlockStorage"
            }

            log.debug("Creating VDUComputeNode for vdu {}", vduId);
            //Creating VDUComputeNode
            it.nextworks.nfvmano.libs.ifa.descriptors.vnfd.VduProfile ifaVduProfile = df.getVduProfile().stream().filter(profile -> profile.getVduId().equals(vduId)).findAny().orElse(null);
            if(ifaVduProfile == null) //VDU profile not specified
                continue;
            VduProfile vduProfile = new VduProfile(ifaVduProfile.getMinNumberOfInstances(), ifaVduProfile.getMaxNumberOfInstances());
            SwImageData swImageData = new SwImageData(imageName, resourceVendorRelease, imageDesc.getChecksum(), null, null, imageDesc.getMinDisk(), imageDesc.getMinRam(), imageDesc.getSize(), imageDesc.getOperatingSystem(), null);
            VDUComputeProperties vduProperties = new VDUComputeProperties(vduId, null, null, null, null, null, null, vduProfile, swImageData);
            VirtualComputeCapabilityProperties vccProperties = new VirtualComputeCapabilityProperties(null, null, null, new VirtualMemory(vRam, null, null, false), new VirtualCpu(null, null, vCpu, null, null, null, null), null);
            VirtualComputeCapability virtualComputeCapability = new VirtualComputeCapability(vccProperties);
            VDUComputeCapabilities vduCapabilities = new VDUComputeCapabilities(virtualComputeCapability);
            List<String> storages = virtualStorageDescList.stream().map(VirtualStorageDesc::getStorageId).collect(Collectors.toList());
            VDUComputeRequirements vduRequirements = new VDUComputeRequirements(null, storages);
            nodeTemplates.put(vduId, new VDUComputeNode(null, vduProperties, vduCapabilities, vduRequirements));//type: "tosca.nodes.nfv.Vdu.Compute"
        }

        log.debug("Creating VNFNode");
        //Creating VNFNode
        String defaultIl = df.getDefaultInstantiationLevelId();
        if(defaultIl == null){
            it.nextworks.nfvmano.libs.ifa.descriptors.vnfd.InstantiationLevel il = df.getInstantiationLevel().get(0); //Take the first il as default
            if(il != null)
                defaultIl = il.getLevelId();
        }
        VnfProfile vnfProfile = new VnfProfile(defaultIl, null, null);
        VNFProperties vnfProperties = new VNFProperties(id, version, resourceVendor, name, resourceVendorRelease, name, description, null, null, null, null, null, null, null, df.getFlavourId(), df.getDescription(), vnfProfile);
        nodeTemplates.put(name, new VNFNode(null, name, vnfProperties, null, null, null));//type: "tosca.nodes.nfv.VNF"

        //Creating SubstitutionMappings
        SubstitutionMappingsRequirements requirements = new SubstitutionMappingsRequirements(null, virtualLink);
        SubstitutionMappings substitutionMappings = new SubstitutionMappings(null, "tosca.nodes.nfv.VNF", null, requirements, null);

        //Creating TopologyTemplate
        TopologyTemplate topologyTemplate = new TopologyTemplate(null, substitutionMappings, null, nodeTemplates, null, null);

        //Creating Metadata
        Metadata metadata = new Metadata(id, resourceVendor, version);

        //Creating DescriptorTemplate
        return new DescriptorTemplate("tosca_sol001_v0_10", null, description, metadata, null, null, null, topologyTemplate);
    }

    public static DescriptorTemplate generateNsDescriptor(Nsd nsdIfa, SODriver soClient) throws IllegalArgumentException {
        //No field for identifying the default df, select the first one
        NsDf defaultDf = nsdIfa.getNsDf().get(0);
        NsLevel defaultIl;
        try{
            defaultIl = defaultDf.getDefaultInstantiationLevel();
        }catch(NotExistingEntityException e){
            throw new IllegalArgumentException(e);
        }
        String description = defaultIl.getDescription();
        String nsDescriptorId = nsdIfa.getNsdIdentifier();
        String nsdName = nsdIfa.getNsdName();
        String nsdDesigner = nsdIfa.getDesigner();

        LinkedHashMap<String, Node> nodeTemplates = new LinkedHashMap<>();

        log.debug("Creating NsVirtualLinkNodes");
        List<String> virtualLinkProfileIds = new ArrayList<>();
        for (VirtualLinkToLevelMapping virtualLinkToLevelMapping : defaultIl.getVirtualLinkToLevelMapping() ) {
            virtualLinkProfileIds.add(virtualLinkToLevelMapping.getVirtualLinkProfileId());
        }
        Map<String, String> vlProfileToLinkDesc = new HashMap<>();
        for (String vlProfileId : virtualLinkProfileIds) {
            for (VirtualLinkProfile virtualLinkProfile : defaultDf.getVirtualLinkProfile()) {
                if (vlProfileId.equals(virtualLinkProfile.getVirtualLinkProfileId())) {
                    vlProfileToLinkDesc.put(vlProfileId, virtualLinkProfile.getVirtualLinkDescId());
                }
            }
        }

        for(Map.Entry<String, String> entry : vlProfileToLinkDesc.entrySet()) {
            String virtualLinkDescId = entry.getValue();
            NsVirtualLinkDesc virtualLinkDesc = nsdIfa.getVirtualLinkDesc().stream().filter(vlDesc -> vlDesc.getVirtualLinkDescId().equals(virtualLinkDescId)).findAny().orElse(null);
            if(virtualLinkDesc == null)//nsVirtualLinkDesc not specified
                continue;
            VlProfile vlProfile = new VlProfile(new LinkBitrateRequirements(1000000, 10000), new LinkBitrateRequirements(100000, 10000), null, null);
            List<LayerProtocol> layerProtocols = new ArrayList<>();
            layerProtocols.add(LayerProtocol.IPV4);
            ConnectivityType connectivityType = new ConnectivityType(layerProtocols, FlowPattern.LINE);
            NsVirtualLinkProperties vlProperties = new NsVirtualLinkProperties(null, virtualLinkDesc.getDescription(), vlProfile, connectivityType, null);
            vlProperties.setExternalNet(isVirtualLinkExternal(nsdIfa, virtualLinkDescId));
            vlProperties.setMgmtNet(isVirtualLinkManagement(nsdIfa, virtualLinkDescId));
            nodeTemplates.put(virtualLinkDescId, new NsVirtualLinkNode(null, vlProperties, null));
        }

        log.debug("Creating VNFNodes");
        //Creating VNFNodes only for VNFs belonging to the default il
        List<String> vnfProfileIds = new ArrayList<>();
        for (VnfToLevelMapping vnfToLevelMapping : defaultIl.getVnfToLevelMapping() ) {
            vnfProfileIds.add(vnfToLevelMapping.getVnfProfileId());
        }
        for (String vnfProfileId : vnfProfileIds) {
            for(it.nextworks.nfvmano.libs.ifa.descriptors.nsd.VnfProfile vnfProfile : defaultDf.getVnfProfile()) {
                if (vnfProfileId.equals(vnfProfile.getVnfProfileId())) {
                    String vnfdId = vnfProfile.getVnfdId();
                    String vnfdVersion = getVnfdVersion(vnfdId, soClient);
                    if(vnfdVersion == null)
                        continue; //cannot find the corresponding vnfd version
                    VNFProperties vnfProperties = new VNFProperties(vnfdId, vnfdVersion, nsdDesigner, vnfProfile.getVnfProfileId(), "1.0", null, null, null, null, null, null, null, null, null, vnfProfile.getFlavourId(), vnfProfile.getFlavourId() + " flavor", new VnfProfile(vnfProfile.getInstantiationLevel(), vnfProfile.getMinNumberOfInstances(), vnfProfile.getMaxNumberOfInstances()));
                    Map<String, String> vnfVirtualLink= new HashMap<>();
                    for (NsVirtualLinkConnectivity nsVirtualLinkConnectivity : vnfProfile.getNsVirtualLinkConnectivity()){
                        String vlDesc = vlProfileToLinkDesc.get(nsVirtualLinkConnectivity.getVirtualLinkProfileId());
                        for (String cpId : nsVirtualLinkConnectivity.getCpdId())
                            vnfVirtualLink.put(cpId, vlDesc);
                    }
                    VNFRequirements vnfRequirements = new VNFRequirements(vnfVirtualLink);
                    VNFNode oldVnfNode = (VNFNode)nodeTemplates.put(vnfdId, new VNFNode(null, null, vnfProperties, vnfRequirements, null, null));
                    if(oldVnfNode != null) {//same VNF present more than one
                        //Generate random string
                        Random random = new Random();
                        String generatedString = random.ints(97, 123)//97 letter 'a', 122 letter 'z'
                                .limit(3)
                                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                .toString();
                        nodeTemplates.put(vnfdId + "_" + generatedString, oldVnfNode);
                    }
                }
            }
        }

        log.debug("Creating NSNode");
        //Creating NSNode
        NSProperties nsProperties = new NSProperties(nsDescriptorId, nsdIfa.getDesigner(), nsdIfa.getVersion(), nsdName, nsdIfa.getNsdInvariantId());
        nodeTemplates.put(nsdName + "_ns", new NSNode(null, nsProperties, null));

        //Creating SubstitutionMappings
        List<VirtualLinkPair> virtualLink = new ArrayList<>();
        for (Sapd sapd : nsdIfa.getSapd()) {
            VirtualLinkPair virtualLinkPair = new VirtualLinkPair(sapd.getCpdId(), sapd.getNsVirtualLinkDescId());
            virtualLink.add(virtualLinkPair);
        }
        SubstitutionMappingsRequirements requirements = new SubstitutionMappingsRequirements(null, virtualLink);
        SubstitutionMappings substitutionMappings = new SubstitutionMappings(null, "tosca.nodes.nfv.NS", null, requirements, null);

        //Creating TopologyTemplate
        TopologyTemplate topologyTemplate = new TopologyTemplate(null, substitutionMappings, null, nodeTemplates, null, null);

        //Creating Metadata
        Metadata metadata = new Metadata(nsDescriptorId , nsdIfa.getDesigner(), nsdIfa.getVersion());

        //Creating DescriptorTemplate
        return new DescriptorTemplate("tosca_simple_yaml_1_2", null, description, metadata, topologyTemplate);
    }

    private static boolean isVirtualLinkManagement(Nsd nsd, String virtualLinkDescId){
        log.debug("Determining if link is management");
        List<Sapd> saps = nsd.getSapd();
        if(saps != null && !saps.isEmpty()){
            for(Sapd sap : saps){
                if(sap.getNsVirtualLinkDescId().equals(virtualLinkDescId))
                    for(it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.AddressData address: sap.getAddressData()){
                        if(address.isManagement())
                            return true;
                    }
            }
        }
        return false;
    }

    private static boolean isVirtualLinkExternal(Nsd nsd, String virtualLinkDescId ){
        log.debug("Determining if link is external");
        List<Sapd> saps = nsd.getSapd();
        if(saps != null && !saps.isEmpty()){
            for(Sapd sap : saps){
                if(sap.getNsVirtualLinkDescId().equals(virtualLinkDescId))
                    for(it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.AddressData address: sap.getAddressData()){
                        if(address.isFloatingIpActivated())
                            return true;
                    }
            }
        }
        return false;
    }

    private static String getVnfdVersion(String vnfdId, SODriver soClient) {
        String vnfdVersion = null;
        try {
            List<SoVnfInfoObject> vnfPkgs = soClient.queryVnfPackagesInfo().getQueryResult();
            for (SoVnfInfoObject vnfInfoObject : vnfPkgs) {
                if (vnfInfoObject.getVnfd().getVnfdId().equals(vnfdId))
                    vnfdVersion = vnfInfoObject.getVnfd().getVnfdVersion();
            }
            return vnfdVersion;
        }catch(FailedOperationException e){
            return null;
        }
    }
}
