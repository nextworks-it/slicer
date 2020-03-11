package it.nextworks.nfvmano.sebastian.common;


import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.QueryNsdResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.QueryOnBoardedVnfPkgInfoResponse;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualComputeDesc;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualStorageDesc;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.libs.ifa.descriptors.vnfd.*;
import it.nextworks.nfvmano.sebastian.admin.elements.VirtualResourceUsage;
import it.nextworks.nfvmano.catalogue.blueprint.BlueprintCatalogueUtilities;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.catalogue.translator.NfvNsInstantiationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VirtualResourceCalculatorService {


    private static final Logger log = LoggerFactory.getLogger(VirtualResourceCalculatorService.class);


    @Autowired
    private NfvoCatalogueService nfvoCatalogueService;


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




    public  VirtualResourceUsage computeVirtualResourceUsageNew( NfvNsInstantiationInfo nsInstantiationInfo) throws Exception {
        log.debug("Computing the amount of resources associated to a NS instantiation.");

        //TODO: parse the MEC app data when available

        String nsdId = nsInstantiationInfo.getNfvNsdId();
        String nsdVersion = nsInstantiationInfo.getNsdVersion();
        String deploymentFlavourId = nsInstantiationInfo.getDeploymentFlavourId();
        String instantiationLevelId = nsInstantiationInfo.getInstantiationLevelId();

        log.info("deploymentFlavourId "+deploymentFlavourId);
        log.info("instantiationLevelId "+instantiationLevelId);

        int ram = 0;
        int vCPU = 0;
        int disk = 0;

        QueryNsdResponse nsdResp = nfvoCatalogueService.queryNsd(new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildNsdFilter(nsdId, nsdVersion), null));
        Nsd nsd = nsdResp.getQueryResult().get(0).getNsd();

        //return a map with key = VNFD_ID and value a map with keys = [VNFD_ID, VNF_DF_ID, VNF_INSTANCES, VNF_INSTANTIATION_LEVEL]
        Map<String, Map<String, String>> vnfData = nsd.getVnfdDataFromFlavour(deploymentFlavourId, instantiationLevelId);


        for (Map.Entry<String, Map<String, String>> e : vnfData.entrySet()) {

            String vnfdId = e.getKey();


            Map<String, String> vnfCharacteristics = e.getValue();
            String vnfDfId = vnfCharacteristics.get("VNF_DF_ID");
            int vnfInstancesNumber= Integer.parseInt(vnfCharacteristics.get("VNF_INSTANCES"));
            String vnfInstantiationLevel = vnfCharacteristics.get("VNF_INSTANTIATION_LEVEL");

            int vnfRam = 0;
            int vnfVCpu = 0;
            int vnfDisk = 0;

            log.info("vnfdId "+vnfdId);
            log.info("vnfInstancesNumber "+vnfInstancesNumber);
            log.info("vnfInstantiationLevel "+vnfInstantiationLevel);

            QueryOnBoardedVnfPkgInfoResponse vnfPkg = nfvoCatalogueService.queryVnfPackageInfo(new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildVnfPackageInfoFilterFromVnfdId(vnfdId), null));
            log.info("vnfPkg count "+vnfPkg.getQueryResult().size());
            log.info("getVnfdId "+vnfPkg.getQueryResult().get(0).getVnfdId());
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

    public  VirtualResourceUsage computeVirtualResourceUsage( NfvNsInstantiationInfo nsInstantiationInfo) throws Exception {
        log.debug("Computing the amount of resources associated to a NS instantiation.");

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
            int vnfInstancesNumber= Integer.parseInt(vnfCharacteristics.get("VNF_INSTANCES"));
            String vnfInstantiationLevel = vnfCharacteristics.get("VNF_INSTANTIATION_LEVEL");

            int vnfRam = 0;
            int vnfVCpu = 0;
            int vnfDisk = 0;

            QueryOnBoardedVnfPkgInfoResponse vnfPkg = nfvoCatalogueService.queryVnfPackageInfo(new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildVnfPackageInfoFilterFromVnfdId(vnfdId), null));
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


}
