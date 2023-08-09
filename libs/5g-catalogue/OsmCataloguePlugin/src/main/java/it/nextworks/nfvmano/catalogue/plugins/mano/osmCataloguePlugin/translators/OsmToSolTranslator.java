package it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.translators;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.elements.OsmTranslationInformation;
import it.nextworks.nfvmano.libs.common.enums.CpRole;
import it.nextworks.nfvmano.libs.common.enums.FlowPattern;
import it.nextworks.nfvmano.libs.common.enums.LayerProtocol;
import it.nextworks.nfvmano.libs.descriptors.capabilities.VirtualComputeCapability;
import it.nextworks.nfvmano.libs.descriptors.capabilities.VirtualComputeCapabilityProperties;
import it.nextworks.nfvmano.libs.descriptors.elements.*;
import it.nextworks.nfvmano.libs.descriptors.interfaces.LcmOperation;
import it.nextworks.nfvmano.libs.descriptors.interfaces.Vnflcm;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NS.NSNode;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NS.NSProperties;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NsVirtualLink.NsVirtualLinkNode;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NsVirtualLink.NsVirtualLinkProperties;
import it.nextworks.nfvmano.libs.descriptors.sol006.Nsd;
import it.nextworks.nfvmano.libs.descriptors.sol006.Vnfd;
import it.nextworks.nfvmano.libs.descriptors.templates.*;
import it.nextworks.nfvmano.libs.descriptors.templates.Node;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VDU.*;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF.VNFInterfaces;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF.VNFNode;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF.VNFProperties;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF.VNFRequirements;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VnfExtCp.VnfExtCpNode;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VnfExtCp.VnfExtCpProperties;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VnfExtCp.VnfExtCpRequirements;
import it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor.*;
import it.nextworks.nfvmano.libs.osmr4PlusDataModel.osmManagement.OsmInfoObject;
import it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor.*;
import it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor.ConnectionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class OsmToSolTranslator {

    public static class GenerateVnfOutput {

        private Vnfd vnfd;
        private Map<String, File> cloudInitMap;

        public GenerateVnfOutput(Vnfd vnfd, Map<String, File> cloudInitMap) {
            this.vnfd = vnfd;
            this.cloudInitMap = cloudInitMap;
        }

        public Vnfd getVnfd() { return vnfd; }

        public Map<String, File> getCloudInitMap() { return cloudInitMap; }
    }

    private static final Logger log = LoggerFactory.getLogger(OsmToSolTranslator.class);

    public static DescriptorTemplate generateVnfDescriptor(OsmVNFPackage vnfPkgOsm) throws IOException, IllegalArgumentException {
        if(vnfPkgOsm.getVnfdCatalog().getVnfd() == null || vnfPkgOsm.getVnfdCatalog().getVnfd().size() == 0)
            throw new IllegalArgumentException("No VNF defined");
        if(vnfPkgOsm.getVnfdCatalog().getVnfd().size() != 1)
            throw new IllegalArgumentException("Too many VNF defined");

        VNFDescriptor vnfdOsm = vnfPkgOsm.getVnfdCatalog().getVnfd().get(0);

        log.debug("Creating VnfExtCpNode");
        //Creating VnfExtCpNode and VirtualinkRequirements for SubstitutionMappingsRequirements
        LinkedHashMap<String, Node> nodeTemplates = new LinkedHashMap<>();
        List<VirtualLinkPair> virtualLink = new ArrayList<>();
        //TODO add this check in the data model validate?
        if(vnfdOsm.getConnectionPoints() == null || vnfdOsm.getConnectionPoints().size() == 0)
            throw new IllegalArgumentException("No Connection Points defined");
        for(ConnectionPoint cp : vnfdOsm.getConnectionPoints()){
            if(cp.getName() == null)
                throw new IllegalArgumentException("No Connection Point name defined");
            virtualLink.add(new VirtualLinkPair(cp.getName(), cp.getName()));
            List<LayerProtocol> layerProtocols = new ArrayList<>();
            layerProtocols.add(LayerProtocol.IPV4);
            List<CpProtocolData> protocolData = new ArrayList<>();
            protocolData.add(new CpProtocolData(LayerProtocol.IPV4, null));
            List<VirtualNetworkInterfaceRequirements> virtualNetworkInterfaceRequirements = new ArrayList<>();
            HashMap<String, String> interfaceRequirements = new HashMap<>();
            if(vnfdOsm.getManagementInterface().getCp().equalsIgnoreCase(cp.getName()))
                interfaceRequirements.put("isManagement", "true");
            VirtualNetworkInterfaceRequirements virtualNetworkInterfaceRequirement = new VirtualNetworkInterfaceRequirements(null, null, false, interfaceRequirements, null);
            virtualNetworkInterfaceRequirements.add(virtualNetworkInterfaceRequirement);
            VnfExtCpProperties cpProperties = new VnfExtCpProperties(null, layerProtocols, CpRole.LEAF, cp.getName(), protocolData, false, virtualNetworkInterfaceRequirements);
            List<String> externalVirtualLink = new ArrayList<>();
            externalVirtualLink.add(cp.getName());
            VnfExtCpRequirements cpRequirements = new  VnfExtCpRequirements(externalVirtualLink, null);
            nodeTemplates.put(cp.getName(), new VnfExtCpNode(null, cpProperties, cpRequirements));//type: "tosca.nodes.nfv.VnfExtCp"
        }

        if(vnfdOsm.getVduList() == null || vnfdOsm.getVduList().size() == 0)
            throw new IllegalArgumentException("No VDU defined");
        //For the moment consider only single VDU
        if(vnfdOsm.getVduList().size() != 1)
            throw new IllegalArgumentException("Multiple VDU are not currently supported");
        VDU vdu = vnfdOsm.getVduList().get(0);
        log.debug("Creating VDUVirtualBlockStorageNode");
        //Creating  VDUVirtualBlockStorageNode
        if(vdu.getVmFlavor() == null || vdu.getVmFlavor().getStorageGb() == null || vdu.getVmFlavor().getMemoryMb() == null || vdu.getVmFlavor().getVcpuCount() == null)
            throw new IllegalArgumentException("No VM Flavor defined");
        VirtualBlockStorageData virtualBlockStorageData = new VirtualBlockStorageData(vdu.getVmFlavor().getStorageGb(), null, false);
        if(vdu.getImage() == null)
            throw new IllegalArgumentException("No Image Data defined");
        SwImageData swImageData = new SwImageData(vdu.getImage(), "1.0", null, null, null, null, null, null, null, null);
        VDUVirtualBlockStorageProperties bsProperties = new VDUVirtualBlockStorageProperties(virtualBlockStorageData, swImageData);
        if(vnfdOsm.getName() == null && vnfdOsm.getShortName() == null)
            throw new IllegalArgumentException("No VNFD name defined");
        String vnfNodeName = vnfdOsm.getName();
        if(vnfNodeName == null)
            vnfNodeName = vnfdOsm.getShortName();
        nodeTemplates.put(vnfNodeName + "_storage", new VDUVirtualBlockStorageNode(null, bsProperties));//type: "tosca.nodes.nfv.Vdu.VirtualBlockStorage"

        log.debug("Creating VDUComputeNode");
        //Creating VDUComputeNode
        //For the moment consider only single scalingGroupDescriptor
        Integer minInstanceCount = (vdu.getCount() == null) ? 1 : vdu.getCount();
        Integer maxInstanceCount = minInstanceCount;
        if(vnfdOsm.getScalingGroupDescriptor() != null && vnfdOsm.getScalingGroupDescriptor().get(0).getMaxInstanceCount() != null)
            maxInstanceCount = vnfdOsm.getScalingGroupDescriptor().get(0).getMaxInstanceCount() + minInstanceCount;
        VduProfile vduProfile = new VduProfile(minInstanceCount, maxInstanceCount);
        if(vdu.getName() == null)
            throw new IllegalArgumentException("No VDU name defined");
        VDUComputeProperties vduProperties = new VDUComputeProperties(vdu.getName(), null, null, null, null, null, null, vduProfile, null);
        VirtualComputeCapabilityProperties vccProperties = new VirtualComputeCapabilityProperties(null, null, null, new VirtualMemory(vdu.getVmFlavor().getMemoryMb(), null, null, false), new VirtualCpu(null, null, vdu.getVmFlavor().getVcpuCount(), null, null, null, null), null);
        VirtualComputeCapability virtualComputeCapability = new VirtualComputeCapability(vccProperties);
        VDUComputeCapabilities vduCapabilities = new VDUComputeCapabilities(virtualComputeCapability);
        List<String> storages = new ArrayList<>();
        storages.add(vnfNodeName + "_storage");
        VDUComputeRequirements vduRequirements = new VDUComputeRequirements(null, storages);
        nodeTemplates.put(vdu.getName(), new VDUComputeNode(null, vduProperties, vduCapabilities, vduRequirements));//type: "tosca.nodes.nfv.Vdu.Compute"

        log.debug("Creating VNFNode");
        //Creating VNFNode
        if(vnfdOsm.getId() == null)
            throw new IllegalArgumentException("No VNFD ID defined");
        String vnfVersion = vnfdOsm.getVersion();
        if(vnfVersion == null)
            vnfVersion = "1.0";
        String vnfVendor = vnfdOsm.getVendor();
        if(vnfVendor == null)
            vnfVendor = "Undefined";
        VNFProperties vnfProperties = new VNFProperties(vnfdOsm.getId(), vnfVersion, vnfVendor, vnfNodeName, "1.0", vnfNodeName, vnfdOsm.getDescription(), null, null, null, null, null, null, null, vnfNodeName + "_flavor", vnfNodeName + " flavor", null);
        VNFInterfaces vnfInterfaces = null;
        if(vdu.getCloudInitFile() != null)
            vnfInterfaces = new VNFInterfaces(null, new Vnflcm(new LcmOperation(vdu.getCloudInitFile()), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
        nodeTemplates.put(vnfNodeName + "_VNF", new VNFNode(null, vnfNodeName, vnfProperties, null, null, vnfInterfaces));//type: "tosca.nodes.nfv.VNF"

        //Creating SubstitutionMappings
        SubstitutionMappingsRequirements requirements = new SubstitutionMappingsRequirements(null, virtualLink);
        SubstitutionMappings substitutionMappings = new SubstitutionMappings(null, "tosca.nodes.nfv.VNF", null, requirements, null);

        //Creating TopologyTemplate
        TopologyTemplate topologyTemplate = new TopologyTemplate(null, substitutionMappings, null, nodeTemplates, null, null);

        //Creating Metadata
        Metadata metadata = new Metadata(vnfdOsm.getId(), vnfVendor, vnfVersion);

        //Creating DescriptorTemplate
        DescriptorTemplate vnfd = new DescriptorTemplate("tosca_simple_yaml", null, "Descriptor generated by 5G Apps & Services Catalogue", metadata, null, null, null, topologyTemplate);

        return vnfd;
    }

    public static DescriptorTemplate generateNsDescriptor(OsmNSPackage nsPkgOsm, List<OsmInfoObject> vnfInfoList, List<OsmTranslationInformation> translationInformationList, Path rootDir) throws IOException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        if(nsPkgOsm.getNsdCatalog().getNsds() == null || nsPkgOsm.getNsdCatalog().getNsds().size() == 0)
            throw new IllegalArgumentException("No NS defined");
        if(nsPkgOsm.getNsdCatalog().getNsds().size() != 1)
            throw new IllegalArgumentException("Too many NS defined");
        NSDescriptor osmNsDescriptor = nsPkgOsm.getNsdCatalog().getNsds().get(0);
        LinkedHashMap<String, Node> nodeTemplates = new LinkedHashMap<>();

        log.debug("Creating NsVirtualLinkNodes");
        List<ConstituentVNFD> vnfdList = osmNsDescriptor.getConstituentVNFDs();
        List<VLD> vlds = osmNsDescriptor.getVldList();
        List<String> virtualLinkNames = new ArrayList<>();
        Map<String, Map<String, String>> virtualLinkRequirements = new HashMap<>();
        for(ConstituentVNFD vnfd : vnfdList)
            virtualLinkRequirements.put(vnfd.getVnfdIdentifierReference(), new HashMap<>());
        for(VLD vld : vlds) {
            if(vld.getId() == null)
                throw new IllegalArgumentException("No VLD ID defined");
            String vLinkName;
            if(vld.getVimNetworkName() != null)
                vLinkName = vld.getVimNetworkName();
            else {
                vLinkName = vld.getId();
            }
            List<LayerProtocol> layerProtocols = new ArrayList<>();
            layerProtocols.add(LayerProtocol.IPV4);
            ConnectivityType connectivityType = new ConnectivityType(layerProtocols, FlowPattern.LINE);
            VlProfile vlProfile = new VlProfile(new LinkBitrateRequirements(1000000, 10000), new LinkBitrateRequirements(100000, 10000), null, null);
            NsVirtualLinkProperties vLinkProperties = new NsVirtualLinkProperties(null, vLinkName, vlProfile, connectivityType, null);
            if(vld.isMgmtNetwork())
                vLinkProperties.setMgmtNet(true);
            nodeTemplates.put(vLinkName, new NsVirtualLinkNode(null, vLinkProperties, null));//type: "tosca.nodes.nfv.NsVirtualLink"
            virtualLinkNames.add(vLinkName);
            List<VNFDConnectionPointReference> vnfdConnectionPointReferenceList = vld.getVnfdConnectionPointReferences();
            for(VNFDConnectionPointReference vnfdConnectionPointReference : vnfdConnectionPointReferenceList){
                virtualLinkRequirements.get(vnfdConnectionPointReference.getVnfdIdReference()).put(vnfdConnectionPointReference.getVnfdConnectionPointReference(), vLinkName);//Assume TOSCA VNFD has virtual link requirements {cp: cp_name, vl: cp_name}
            }
        }

        log.debug("Creating VNFNodes");
        for(ConstituentVNFD vnfd : vnfdList){
            String osmVnfDescriptorPath = rootDir.toString();
            for(OsmInfoObject vnfObject : vnfInfoList){
                if(vnfObject.getDescriptorId().equals(vnfd.getVnfdIdentifierReference())) {
                    osmVnfDescriptorPath = osmVnfDescriptorPath.concat("/" + vnfObject.getAdmin().getStorage().getDescriptor());
                    break;
                }
            }
            OsmVNFPackage osmVnfPkg = mapper.readValue(new File(osmVnfDescriptorPath), OsmVNFPackage.class);
            VNFDescriptor osmVnfDescriptor = osmVnfPkg.getVnfdCatalog().getVnfd().get(0);//For the moment consider only one VNF present
            List<OsmTranslationInformation> filteredTranslationInformationList = translationInformationList.stream().filter(t -> t.getOsmDescriptorId().equals(osmVnfDescriptor.getId()) && t.getDescriptorVersion().equals(osmVnfDescriptor.getVersion())).collect(Collectors.toList());
            String catVnfDescriptorId = osmVnfDescriptor.getId();
            if(filteredTranslationInformationList.size() != 0)
                catVnfDescriptorId = filteredTranslationInformationList.get(0).getCatDescriptorId();
            String vnfNodeName = osmVnfDescriptor.getName();
            if(vnfNodeName == null)
                vnfNodeName = osmVnfDescriptor.getShortName();
            VNFProperties vnfProperties = new VNFProperties(catVnfDescriptorId, osmVnfDescriptor.getVersion(), osmVnfDescriptor.getVendor(), vnfNodeName, "1.0", vnfNodeName, osmVnfDescriptor.getDescription(), null, null, null, null, null, null, null, vnfNodeName + "_flavor", vnfNodeName + " flavor", null);
            VNFRequirements vnfRequirements = new VNFRequirements(virtualLinkRequirements.get(vnfd.getVnfdIdentifierReference()));
            VNFNode oldVnfNode = (VNFNode)nodeTemplates.put(vnfNodeName, new VNFNode(null, vnfNodeName, vnfProperties, vnfRequirements, null, null));//type: "tosca.nodes.nfv.VNF"
            if(oldVnfNode != null) {//same VNF present more than one
                //Generate random string
                Random random = new Random();
                String generatedString = random.ints(97, 123)//97 letter 'a', 122 letter 'z'
                        .limit(3)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
                nodeTemplates.put(vnfNodeName + "_" + generatedString, oldVnfNode);
            }
        }

        log.debug("Creating NSNode");
        //Creating NSNode
        if(osmNsDescriptor.getName() == null && osmNsDescriptor.getShortName() == null)
            throw new IllegalArgumentException("No NSD name defined");
        if(osmNsDescriptor.getId() == null)
            throw new IllegalArgumentException("No NSD ID defined");
        String nsdVersion = osmNsDescriptor.getVersion();
        if(nsdVersion == null)
            nsdVersion = "1.0";
        String nsdVendor = osmNsDescriptor.getVendor();
        if(nsdVendor == null)
            nsdVendor = "Undefined";
        //NSRequirements nsRequirements = new NSRequirements(virtualLinkNames);
        String nsNodeName = osmNsDescriptor.getName();
        if(nsNodeName == null)
            nsNodeName = osmNsDescriptor.getShortName();
        NSProperties nsProperties = new NSProperties(osmNsDescriptor.getId(), nsdVendor, nsdVersion, nsNodeName, osmNsDescriptor.getId());
        nodeTemplates.put(nsNodeName + "_NS", new NSNode(null, nsProperties, null));//type: "tosca.nodes.nfv.NS"

        //Creating SubstitutionMappings
        List<VirtualLinkPair> virtualLinkPairs = new ArrayList<>();
        SubstitutionMappingsRequirements requirements = new SubstitutionMappingsRequirements(null, virtualLinkPairs);
        SubstitutionMappings substitutionMappings = new SubstitutionMappings(null, "tosca.nodes.nfv.NS", null, requirements, null);

        //Creating TopologyTemplate
        TopologyTemplate topologyTemplate = new TopologyTemplate(null, substitutionMappings, null, nodeTemplates, null, null);

        //Creating Metadata
        Metadata metadata = new Metadata(osmNsDescriptor.getId(), nsdVendor, nsdVersion);

        //Creating DescriptorTemplate
        DescriptorTemplate nsd = new DescriptorTemplate("tosca_simple_yaml", null, "Descriptor generated by 5G Apps & Services Catalogue", metadata, null, null, null, topologyTemplate);

        return nsd;
    }

    public static GenerateVnfOutput generateVnfDescriptor(JsonNode osmVnfdWrapper,
                                                          OsmInfoObject vnfPackageInfo,
                                                          Path osmDirPath) throws IOException {

        JsonNode osmVnfd = osmVnfdWrapper.get("vnfd");

        Map<String, File> cloudInitMap = new HashMap<>();

        JsonNode vduNode = osmVnfd.get("vdu");
        if(vduNode != null) {
            ArrayNode vdus = (ArrayNode) vduNode;
            for (JsonNode vdu : vdus) {
                JsonNode cloudInitFilename = vdu.get("cloud-init-file");
                if (cloudInitFilename != null) {
                    File cloudInitFile = new File(osmDirPath.toString() + "/" +
                            vnfPackageInfo.getAdmin().getStorage().getPkgDir() + "/cloud_init/" + cloudInitFilename.asText());
                    cloudInitMap.put(vdu.get("id").asText(), cloudInitFile);
                }

                JsonNode monitoringParameterNode = vdu.get("monitoring-parameter");
                if (monitoringParameterNode != null) {
                    ArrayNode monitoringParameters = (ArrayNode) monitoringParameterNode;
                    for (JsonNode monitoringParameter : monitoringParameters)
                        ((ObjectNode) monitoringParameter)
                                .put("performance-metric", "AllOfvnfd_vduMonitoringParameterItems");
                }
            }
        }

        JsonNode dfNode = osmVnfd.get("df");
        if(dfNode != null) {
            ArrayNode dfs = (ArrayNode) dfNode;
            for(JsonNode df : dfs) {
                JsonNode monitoringParameterNode = df.get("monitoring-parameter");
                if(monitoringParameterNode != null) {
                    ArrayNode monitoringParameters = (ArrayNode) monitoringParameterNode;
                    for(JsonNode monitoringParameter : monitoringParameters)
                        ((ObjectNode) monitoringParameter)
                                .put("performance-metric", "AllOfvnfd_dfMonitoringParameterItems");
                }
            }
        }

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Vnfd vnf = mapper.convertValue(osmVnfd, Vnfd.class);

        return new GenerateVnfOutput(vnf, cloudInitMap);
    }

    public static Nsd generateNsDescriptor(JsonNode osmNsdWrapper,
                                           List<OsmInfoObject> vnfInfoList,
                                           List<OsmTranslationInformation> translationInformationList,
                                           Path osmDirPath) throws IOException {

        JsonNode osmNsdsSol006Wrapper = osmNsdWrapper.get("nsd");
        JsonNode osmNsdSol006ListNode = osmNsdsSol006Wrapper.get("nsd");
        ArrayNode osmNsdSol006List = (ArrayNode) osmNsdSol006ListNode;
        if(osmNsdSol006List.size() != 1)
            log.debug("NSD list contains more than one nsd object, taking only the first one.");
        JsonNode osmNsdSol006 = osmNsdSol006List.get(0);

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Nsd nsd = mapper.convertValue(osmNsdSol006, Nsd.class);

        String osmDirPath_string = osmDirPath.toString();
        List<String> catVnfdIds = new ArrayList<>();
        for(String vnfdId : nsd.getVnfdId()) {

            String osmVnfDescriptorPath = null;
            for (OsmInfoObject osmVnfInfoObject : vnfInfoList) {
                if(osmVnfInfoObject.getDescriptorId().equals(vnfdId)) {
                    osmVnfDescriptorPath = osmDirPath_string + "/" + osmVnfInfoObject.getAdmin().getStorage().getDescriptor();
                    break;
                }
            }

            JsonNode osmVnfd = mapper.readTree(new File(osmVnfDescriptorPath)).get("vnfd");
            String id = osmVnfd.get("id").asText();

            String catVnfdId;
            List<OsmTranslationInformation> filteredTranslationInformationList =
                    translationInformationList.stream()
                    .filter(t -> t.getOsmDescriptorId().equals(id)
                            && t.getDescriptorVersion().equals( osmVnfd.get("version").asText()))
                    .collect(Collectors.toList());
            if(filteredTranslationInformationList.size() != 0)
                catVnfdId = filteredTranslationInformationList.get(0).getCatDescriptorId();
            else
                catVnfdId = id;

            catVnfdIds.add(catVnfdId);
        }
        nsd.setVnfdId(catVnfdIds);

        return nsd;
    }
}
