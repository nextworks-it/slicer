/*
 * Copyright 2018 Nextworks s.r.l.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.translators;

import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NS.NSNode;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NS.NSProperties;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NsVirtualLink.NsVirtualLinkNode;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NsVirtualLink.NsVirtualLinkProperties;
import it.nextworks.nfvmano.libs.descriptors.templates.DescriptorTemplate;
import it.nextworks.nfvmano.libs.descriptors.templates.SubstitutionMappingsRequirements;
import it.nextworks.nfvmano.libs.descriptors.templates.VirtualLinkPair;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF.VNFNode;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF.VNFProperties;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF.VNFRequirements;
import it.nextworks.nfvmano.libs.ifa.common.elements.QoS;
import it.nextworks.nfvmano.libs.ifa.common.enums.*;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.*;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;


public class SolToIfaTranslator {

    private static final Logger log = LoggerFactory.getLogger(SolToIfaTranslator.class);
    private DescriptorTemplate dt;

    public SolToIfaTranslator() { }

    public static Nsd generateNsDescriptor(DescriptorTemplate nsdSol) throws MalformattedElementException {
        if (!(nsdSol.getTopologyTemplate().getNSNodes().size() == 1)) {
            throw new IllegalArgumentException("Too many Nsds");
        }

        NSNode nsNode = nsdSol.getTopologyTemplate().getNSNodes().values().iterator().next();
        NSProperties nsNodeProperties = nsNode.getProperties();

        Map<String, VNFNode> vnfNodes = nsdSol.getTopologyTemplate().getVNFNodes();
        List<VNFProperties> vnfNodeProperties = vnfNodes.values().stream().map(VNFNode::getProperties).collect(Collectors.toList());
        List<String> vnfdIds = vnfNodeProperties.stream().map(VNFProperties::getDescriptorId).collect(Collectors.toList());

        /*
        Map<String, PNFNode> pnfNodes = dt.getTopologyTemplate().getPNFNodes();
        List<PNFProperties> pnfNodeProperties = pnfNodes.values().stream().map(PNFNode::getProperties).collect(Collectors.toList());
        List<String> pnfdIds = pnfNodeProperties.stream().map(PNFProperties::getDescriptorId).collect(Collectors.toList());
        */

        Map<String, NsVirtualLinkNode> vlNodes = nsdSol.getTopologyTemplate().getNsVirtualLinkNodes();

        List<Sapd> sapdList = new ArrayList<>();
        SubstitutionMappingsRequirements substitutionMappingsRequirements = nsdSol.getTopologyTemplate().getSubstituitionMappings().getRequirements();
        List<VirtualLinkPair> sapdVirtualLinkPairs = new ArrayList<>();
        if(substitutionMappingsRequirements != null)
            sapdVirtualLinkPairs.addAll(substitutionMappingsRequirements.getVirtualLink());

        for(VirtualLinkPair vlp : sapdVirtualLinkPairs){
            boolean isMgmt = false;
            boolean isExt = false;
            NsVirtualLinkNode vlNode = vlNodes.get(vlp.getVl());
            if(vlNode.getProperties() != null && vlNode.getProperties().isMgmtNet())
                isMgmt = true;
            if(vlNode.getProperties() != null && vlNode.getProperties().isExternalNet())
                isExt = true;
            List<AddressData> addressDataList = new ArrayList<>();
            AddressData addressData = new AddressData(AddressType.IP_ADDRESS, false, isExt, isMgmt, IpVersion.IPv4, 1);
            addressDataList.add(addressData);
            Sapd sapd = new Sapd(null, vlp.getCp(), LayerProtocol.IPV4, CpRole.ROOT, vlp.getCp(), addressDataList, false, vlp.getVl(), null);
            sapdList.add(sapd);
        }

        List<NsVirtualLinkDesc> virtualLinkDescList = new ArrayList<>();
        List<VirtualLinkProfile> vlProfileList = new ArrayList<>();
        for(Map.Entry<String, NsVirtualLinkNode> vlNode : vlNodes.entrySet()){
            NsVirtualLinkProperties vlProperties = vlNode.getValue().getProperties();
            ConnectivityType connectivityType = new ConnectivityType(LayerProtocol.IPV4, null);
            NsVirtualLinkDesc virtualLinkDesc = new NsVirtualLinkDesc(null, vlNode.getKey(), nsNodeProperties.getDesigner(), "1.0", connectivityType, null, vlProperties.getDescription(), null);
            VirtualLinkDf virtualLinkDf = new VirtualLinkDf((NsVirtualLinkDesc) null, "df_" + vlNode.getKey(), new QoS(), ServiceAvailabilityLevel.LEVEL_1);
            virtualLinkDesc.addDeploymentFlavour(virtualLinkDf);
            virtualLinkDescList.add(virtualLinkDesc);

            VirtualLinkProfile virtualLinkProfile = new VirtualLinkProfile((NsDf) null, "vlp_" + vlNode.getKey(), vlNode.getKey(), "df_" + vlNode.getKey(), null, null, new LinkBitrateRequirements("1", "1"), new LinkBitrateRequirements("1", "1"));
            vlProfileList.add(virtualLinkProfile);
        }

        List<NsDf> nsDfList = new ArrayList<>();
        List<VnfProfile> vnfProfileList = new ArrayList<>();
        for(Map.Entry<String, VNFNode> vnfNode : vnfNodes.entrySet()){
            VNFProperties vnfProperties = vnfNode.getValue().getProperties();
            it.nextworks.nfvmano.libs.descriptors.elements.VnfProfile solVnfProfile = vnfProperties.getVnfProfile();
            String flavorId = "default_fl";
            if(vnfProperties.getFlavourId() != null)
                flavorId = vnfProperties.getFlavourId();
            String instantiation_level = "default_il";
            int minNumberOfInstances = 1;
            int maxNumberOfInstances = 1;
            if(solVnfProfile != null){
                if(solVnfProfile.getInstantiationLevel() != null)
                    instantiation_level = solVnfProfile.getInstantiationLevel();
                if(solVnfProfile.getMinNumberOfInstances() != null)
                    minNumberOfInstances = solVnfProfile.getMinNumberOfInstances();
                if(solVnfProfile.getMaxNumberOfInstances() != null)
                    maxNumberOfInstances = solVnfProfile.getMaxNumberOfInstances();
            }
            List<NsVirtualLinkConnectivity> nsVirtualLinkConnectivityList = new ArrayList<>();
            VNFRequirements vnfRequirements = vnfNode.getValue().getRequirements();
            Map<String, String> vlRequirements = vnfRequirements.getVirtualLink();
            for(Map.Entry<String, String> vlRequirement : vlRequirements.entrySet()){
                List<String> cpIds = new ArrayList<>();
                cpIds.add(vlRequirement.getKey());
                NsVirtualLinkConnectivity nsVirtualLinkConnectivity = new NsVirtualLinkConnectivity((NsProfile) null, "vlp_" + vlRequirement.getValue(), cpIds);
                nsVirtualLinkConnectivityList.add(nsVirtualLinkConnectivity);
            }
            VnfProfile vnfProfile = new VnfProfile(null, "vnfp_" + vnfNode.getKey(), vnfProperties.getDescriptorId(), flavorId, instantiation_level, minNumberOfInstances, maxNumberOfInstances, null, null, nsVirtualLinkConnectivityList);
            vnfProfileList.add(vnfProfile);
        }

        List<NsLevel> nsInstantiationLevelList = new ArrayList<>();
        List<VnfToLevelMapping> vnfToLevelMappingList = new ArrayList<>();
        for(VnfProfile vnfProfile : vnfProfileList){
            VnfToLevelMapping vnfToLevelMapping = new VnfToLevelMapping( vnfProfile.getVnfProfileId(), 1);
            vnfToLevelMappingList.add(vnfToLevelMapping);
        }

        NsLevel nsIl = new NsLevel((NsDf) null, nsNodeProperties.getName() + "_il", nsdSol.getDescription(), vnfToLevelMappingList, null);

        for(VirtualLinkProfile virtualLinkProfile : vlProfileList){
            VirtualLinkToLevelMapping virtualLinkToLevelMapping = new VirtualLinkToLevelMapping(null, virtualLinkProfile.getVirtualLinkProfileId(), new LinkBitrateRequirements("1", "1"));
            nsIl.addVirtualLinkToLevelMapping(virtualLinkToLevelMapping);
        }

        nsInstantiationLevelList.add(nsIl);
        NsDf nsDf = new NsDf(null, nsNodeProperties.getName() + "_df", nsNodeProperties.getName() + "_fk", vnfProfileList, null, vlProfileList, null, null, nsInstantiationLevelList, nsNodeProperties.getName() + "_il", null, null);
        nsDfList.add(nsDf);

        return new Nsd(nsNodeProperties.getDescriptorId(), nsNodeProperties.getDesigner(), nsNodeProperties.getVersion(), nsNodeProperties.getName(), nsNodeProperties.getInvariantId(), null, vnfdIds, null, sapdList, virtualLinkDescList, null, null, null, null, nsDfList, null);
    }
}
