package it.nextworks.nfvmano.sebastian.common;

import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.VnfPackageManagementProviderInterface;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.NsdManagementProviderInterface;
import it.nextworks.nfvmano.catalogues.template.services.NsTemplateCatalogueService;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.QueryNsdResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.QueryOnBoardedVnfPkgInfoResponse;
import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualComputeDesc;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualStorageDesc;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.libs.ifa.descriptors.vnfd.*;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import it.nextworks.nfvmano.sebastian.admin.elements.VirtualResourceUsage;
import it.nextworks.nfvmano.catalogue.blueprint.BlueprintCatalogueUtilities;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.catalogue.translator.NfvNsInstantiationInfo;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceSubnetInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VirtualResourceCalculatorService {


    private static final Logger log = LoggerFactory.getLogger(VirtualResourceCalculatorService.class);



    private NsdManagementProviderInterface nfvoCatalogueService=null;


    private VnfPackageManagementProviderInterface vnfPackageManagementProviderInterface=null;

    @Autowired
    private NsTemplateCatalogueService nsTemplateCatalogueService;

    /**
     * This method computes the amount of virtual resources consumed by a network slice with the current or previous instantiation level.
     *
     * @param nsi ID of the network slice instance
     * @param current true if it refers to the current instantiation level
     * @return the amount of consumed virtual resources
     * @throws Exception
     */
    public VirtualResourceUsage computeVirtualResourceUsage(NetworkSliceInstance nsi, boolean current) throws Exception {
        return computeVirtualResourceUsage(nsi.getNsInstantiationInfo(current));
    }

    public VirtualResourceUsage computeVirtualResourceUsage(NetworkSliceSubnetInstance nssi) throws Exception {
        return computeVirtualResourceUsage(nssi.getNsstId());
    }


    public VirtualResourceUsage computeVirtualResourceUsage(NfvNsInstantiationInfo nsInstantiationInfo) throws Exception {
        log.debug("Computing the amount of resources associated to a NS instantiation.");

        if(nfvoCatalogueService== null || vnfPackageManagementProviderInterface==null){
            log.debug("No nfvo catalogue  or vnf catalogue service set. returning 0 virtual resources ");
            return new VirtualResourceUsage(0,0,0);
        }
        //TODO: parse the MEC app data when available

        String nsdId = nsInstantiationInfo.getNfvNsdId();
        String nsdVersion = nsInstantiationInfo.getNsdVersion();
        String deploymentFlavourId = nsInstantiationInfo.getDeploymentFlavourId();
        String instantiationLevelId = nsInstantiationInfo.getInstantiationLevelId();

        int ram = 0;
        int vCPU = 0;
        int disk = 0;

        QueryNsdResponse nsdRep = nfvoCatalogueService.queryNsd(new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildNsdFilter(nsdId, nsdVersion), null));
        Nsd nsd = nsdRep.getQueryResult().get(0).getNsd();

        //return a map with key = VNFD_ID and value a map with keys = [VNFD_ID, VNF_DF_ID, VNF_INSTANCES, VNF_INSTANTIATION_LEVEL]
        Map<String, Map<String, String>> vnfData = nsd.getVnfdDataFromFlavour(deploymentFlavourId, instantiationLevelId);

        for (Map.Entry<String, Map<String, String>> e : vnfData.entrySet()) {
            String vnfdId = e.getKey();
            Map<String, String> vnfCharacteristics = e.getValue();
            String vnfDfId = vnfCharacteristics.get("VNF_DF_ID");
            int vnfInstancesNumber = Integer.parseInt(vnfCharacteristics.get("VNF_INSTANCES"));
            String vnfInstantiationLevel = vnfCharacteristics.get("VNF_INSTANTIATION_LEVEL");

            int vnfRam = 0;
            int vnfVCpu = 0;
            int vnfDisk = 0;

            QueryOnBoardedVnfPkgInfoResponse vnfPkg = vnfPackageManagementProviderInterface.queryVnfPackageInfo(new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildVnfPackageInfoFilterFromVnfdId(vnfdId), null));
            Vnfd vnfd = vnfPkg.getQueryResult().get(0).getVnfd();

            VnfDf df = vnfd.getVnfDf(vnfDfId);
            InstantiationLevel il = df.getInstantiationLevel(vnfInstantiationLevel);
            List<VduLevel> vduLevel = il.getVduLevel();
            for (VduLevel vdul : vduLevel) {
                int vduInstancesNumber = vdul.getNumberOfInstances();
                String vduId = vdul.getVduId();
                Vdu vdu = vnfd.getVduFromId(vduId);
                String computeDescriptorId = vdu.getVirtualComputeDesc();
                VirtualComputeDesc vcd = vnfd.getVirtualComputeDescriptorFromId(computeDescriptorId);
                int localRam = (vcd.getVirtualMemory().getVirtualMemSize()) * vduInstancesNumber;
                int localVCpu = (vcd.getVirtualCpu().getNumVirtualCpu()) * vduInstancesNumber;
                int localDisk = 0;
                List<String> virtualStorageDescId = vdu.getVirtualStorageDesc();
                for (String vsdId : virtualStorageDescId) {
                    VirtualStorageDesc vsd = vnfd.getVirtualStorageDescriptorFromId(vsdId);
                    localDisk += vsd.getSizeOfStorage();
                }
                localDisk = localDisk * vduInstancesNumber;

                //update data for all the VDUs with a given ID in the single VNF
                vnfRam += localRam;
                vnfVCpu += localVCpu;
                vnfDisk += localDisk;

                log.debug("Values for all the VDUs with ID " + vduId + " - vCPU: " + localVCpu + "; RAM: " + localRam + "; Disk: " + localDisk);
            }

            //compute data for all the VNFs with a given Id
            vnfRam = vnfRam * vnfInstancesNumber;
            vnfVCpu = vnfVCpu * vnfInstancesNumber;
            vnfDisk = vnfDisk * vnfInstancesNumber;

            log.debug("Values for all the VNFs with ID " + vnfdId + " - vCPU: " + vnfVCpu + "; RAM: " + vnfRam + "; Disk: " + vnfDisk);

            //update data for the entire NSD
            ram += vnfRam;
            vCPU += vnfVCpu;
            disk += vnfDisk;
        }

        log.debug("Values for the whole NSD with ID " + nsdId + ", DF " + deploymentFlavourId + ", IL " + instantiationLevelId + "- vCPU: " + vCPU + "; RAM: " + ram + "; Disk: " + disk);

        return new VirtualResourceUsage(disk, vCPU, ram);
    }

    public VirtualResourceUsage computeVirtualResourceUsage(String nsstId) throws Exception {
        log.debug("Computing the amount of resources associated to a NS instantiation.");

        //TODO: get needed info about NSD
        Map<String, String> parameters = new HashMap<>();
        parameters.put("NST_ID", nsstId);
        Filter nsstFilter = new Filter(parameters);
        GeneralizedQueryRequest nsstRequest = new GeneralizedQueryRequest(nsstFilter, null);
        NST nsst = nsTemplateCatalogueService.queryNsTemplate(nsstRequest).getNsTemplateInfos().get(0).getNST();

        String nsst_nfvNsdId = nsst.getNsdId();
        String nsst_nsdVersion = nsst.getNsdVersion();

        // TODO: for the time being in 5GCroCo nested NSD are not considered
        // TODO: we are assuming only one InstantiationLevel and only one DeploymentFlavour
        QueryNsdResponse response = nfvoCatalogueService.queryNsd(new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildNsdFilter(nsst_nfvNsdId, nsst_nsdVersion),null));
        Nsd nsd = response.getQueryResult().get(0).getNsd();
        String nsst_nsdInstantiationLevel = nsd.getNsDf().get(0).getDefaultNsInstantiationLevelId();
        String nsst_nsdDeploymentFlavour = nsd.getNsDf().get(0).getNsDfId();

        int ram = 0;
        int vCPU = 0;
        int disk = 0;

        //TODO: adjust method

        //return a map with key = VNFD_ID and value a map with keys = [VNFD_ID, VNF_DF_ID, VNF_INSTANCES, VNF_INSTANTIATION_LEVEL]
        Map<String, Map<String, String>> vnfData = nsd.getVnfdDataFromFlavour(nsst_nsdDeploymentFlavour, nsst_nsdInstantiationLevel);

        for (Map.Entry<String, Map<String, String>> e : vnfData.entrySet()) {
            String vnfdId = e.getKey();
            Map<String, String> vnfCharacteristics = e.getValue();
            String vnfDfId = vnfCharacteristics.get("VNF_DF_ID");
            int vnfInstancesNumber = Integer.parseInt(vnfCharacteristics.get("VNF_INSTANCES"));
            String vnfInstantiationLevel = vnfCharacteristics.get("VNF_INSTANTIATION_LEVEL");

            int vnfRam = 0;
            int vnfVCpu = 0;
            int vnfDisk = 0;

            QueryOnBoardedVnfPkgInfoResponse vnfPkg = vnfPackageManagementProviderInterface.queryVnfPackageInfo(new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildVnfPackageInfoFilterFromVnfdId(vnfdId), null));
            Vnfd vnfd = vnfPkg.getQueryResult().get(0).getVnfd();

            VnfDf df = vnfd.getVnfDf(vnfDfId);
            InstantiationLevel il = df.getInstantiationLevel(vnfInstantiationLevel);
            List<VduLevel> vduLevel = il.getVduLevel();
            for (VduLevel vdul : vduLevel) {
                int vduInstancesNumber = vdul.getNumberOfInstances();
                String vduId = vdul.getVduId();
                Vdu vdu = vnfd.getVduFromId(vduId);
                String computeDescriptorId = vdu.getVirtualComputeDesc();
                VirtualComputeDesc vcd = vnfd.getVirtualComputeDescriptorFromId(computeDescriptorId);
                int localRam = (vcd.getVirtualMemory().getVirtualMemSize()) * vduInstancesNumber;
                int localVCpu = (vcd.getVirtualCpu().getNumVirtualCpu()) * vduInstancesNumber;
                int localDisk = 0;
                List<String> virtualStorageDescId = vdu.getVirtualStorageDesc();
                for (String vsdId : virtualStorageDescId) {
                    VirtualStorageDesc vsd = vnfd.getVirtualStorageDescriptorFromId(vsdId);
                    localDisk += vsd.getSizeOfStorage();
                }
                localDisk = localDisk * vduInstancesNumber;

                //update data for all the VDUs with a given ID in the single VNF
                vnfRam += localRam;
                vnfVCpu += localVCpu;
                vnfDisk += localDisk;

                log.debug("Values for all the VDUs with ID " + vduId + " - vCPU: " + localVCpu + "; RAM: " + localRam + "; Disk: " + localDisk);
            }

            //compute data for all the VNFs with a given Id
            vnfRam = vnfRam * vnfInstancesNumber;
            vnfVCpu = vnfVCpu * vnfInstancesNumber;
            vnfDisk = vnfDisk * vnfInstancesNumber;

            log.debug("Values for all the VNFs with ID " + vnfdId + " - vCPU: " + vnfVCpu + "; RAM: " + vnfRam + "; Disk: " + vnfDisk);

            //update data for the entire NSD
            ram += vnfRam;
            vCPU += vnfVCpu;
            disk += vnfDisk;
        }

        log.debug("Values for the whole NSD with ID " + nsst_nfvNsdId + ", DF " + nsst_nsdDeploymentFlavour + ", IL " + nsst_nsdInstantiationLevel + "- vCPU: " + vCPU + "; RAM: " + ram + "; Disk: " + disk);

        return new VirtualResourceUsage(disk, vCPU, ram);
    }


    public void setNfvoCatalogueService(NsdManagementProviderInterface nfvoCatalogueService) {
        this.nfvoCatalogueService = nfvoCatalogueService;
    }

    public void setVnfPackageManagementProviderInterface(VnfPackageManagementProviderInterface vnfPackageManagementProviderInterface) {
        this.vnfPackageManagementProviderInterface = vnfPackageManagementProviderInterface;
    }
}
