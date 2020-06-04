package it.nextworks.nfvmano.sebastian.common;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.elements.NsdInfo;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.QueryNsdResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.QueryOnBoardedVnfPkgInfoResponse;
import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualComputeDesc;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualStorageDesc;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.*;
import it.nextworks.nfvmano.libs.ifa.descriptors.onboardedvnfpackage.OnboardedVnfPkgInfo;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VirtualResourceCalculatorService {


    private static final Logger log = LoggerFactory.getLogger(VirtualResourceCalculatorService.class);


    @Autowired
    private NfvoCatalogueService nfvoCatalogueService;

    private String nfvoCatalogueDriver;
    /**
     * This method computes the amount of virtual resources consumed by a network slice with the current or previous instantiation level.
     *
     * @param nsi ID of the network slice instance
     * @param current true if it refers to the current instantiation level
     * @return the amount of consumed virtual resources
     * @throws Exception
     */
    public VirtualResourceUsage computeVirtualResourceUsage(NetworkSliceInstance nsi, boolean current) throws Exception {
        int totalCpu=0;
        int totalRam=0;
        int totalDisk=0;
        for(int i=0; i<nsi.getNfvNsInstantiationInfoList().size(); i++){
            VirtualResourceUsage virtualResourceUsage = computeVirtualResourceUsage(nsi.getNfvNsInstantiationInfoList().get(i));
            totalCpu +=virtualResourceUsage.getvCPU();
            totalRam +=virtualResourceUsage.getMemoryRAM();
            totalDisk +=virtualResourceUsage.getDiskStorage();

        }
        return new VirtualResourceUsage(totalDisk, totalCpu, totalRam);

    }


    private void printJson(Object object){
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            log.info("Going to print JSON");
            json = mapper.writeValueAsString(object);
            log.info(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void setNfvoCatalogueDriver(String nfvoCatalogueDriver) {
        this.nfvoCatalogueDriver = nfvoCatalogueDriver;
    }

    private VirtualResourceUsage computeVirtualResourceUsageNmro(NsdInfo nsdInfo) throws MalformattedElementException, NotExistingEntityException, MethodNotImplementedException {
        List<String> vnfPackagesIds = nsdInfo.getOnboardedVnfPkgInfoId();
        int ram = 0;
        int vCPU = 0;
        int disk = 0;
        for(String vnfPackageId :vnfPackagesIds){
            QueryOnBoardedVnfPkgInfoResponse vnfPkg =
                    nfvoCatalogueService.queryVnfPackageInfo(new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildVnfPackageInfoFilterFromVnfdId(vnfPackageId), null));
            List<OnboardedVnfPkgInfo> OnboardedVnfPkgInfoList = vnfPkg.getQueryResult();
            for(OnboardedVnfPkgInfo onboardedVnfPkgInfo: OnboardedVnfPkgInfoList){
                int vnfInstancesNumber=1; //Because the instantiation level is supposed to be the defualt one
                Vnfd vnfd=onboardedVnfPkgInfo.getVnfd();
                int vnfRam = 0;
                int vnfVCpu = 0;
                int vnfDisk = 0;
                printJson(vnfd);
                String vnfdId = vnfd.getVnfdId();
                log.info("Number of VDU: "+vnfd.getVdu().size());
                for(Vdu vdu:vnfd.getVdu()){
                    int vduInstancesNumber = 1; //In the TIMEO is get from instantiation level. Here is supposed to be one because no insantiation level or default one is avaible.
                    String vduId = vdu.getVduId();
                    //String computeDescriptorId = vdu.getVirtualComputeDesc();
                    //VirtualComputeDesc vcd = vnfd.getVirtualComputeDescriptorFromId(computeDescriptorId);

                    VirtualComputeDesc vcd =vnfd.getVirtualComputeDesc().get(0);
                    int localRam = (vcd.getVirtualMemory().getVirtualMemSize()) * vduInstancesNumber;
                    int localVCpu = (vcd.getVirtualCpu().getNumVirtualCpu()) * vduInstancesNumber;
                    int localDisk = 0;
                    //List<String> virtualStorageDescId = vdu.getVirtualStorageDesc();
                    //for (String vsdId : virtualStorageDescId) {
                     //   VirtualStorageDesc vsd = vnfd.getVirtualStorageDescriptorFromId(vsdId);
                    //    localDisk += vsd.getSizeOfStorage();
                    //}
                    if(vnfd.getVirtualStorageDesc()==null || vnfd.getVirtualStorageDesc().size()==0){
                        log.warn("No Virtual Storage description found into vnfd. Assuming Zero local disk.");
                        localDisk += 0;
                    }
                    else{
                        VirtualStorageDesc vsd=vnfd.getVirtualStorageDesc().get(0);
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

                log.debug("Values for the whole NSD with ID " + nsdInfo.getNsdId() + "- vCPU: " + vCPU + "; RAM: " + ram + "; Disk: " + disk);


            }
        }
        return new VirtualResourceUsage(disk, vCPU, ram);
    }

    public  VirtualResourceUsage computeVirtualResourceUsage(NfvNsInstantiationInfo nsInstantiationInfo) throws Exception {
        log.debug("Computing the amount of resources associated to a NS instantiation.");
        String nsdId = nsInstantiationInfo.getNfvNsdId();
        String nsdVersion = nsInstantiationInfo.getNsdVersion();
        String deploymentFlavourId = nsInstantiationInfo.getDeploymentFlavourId();
        String instantiationLevelId = nsInstantiationInfo.getInstantiationLevelId();
        Nsd nsd;
        log.info("The NFVO catalogue type is : "+nfvoCatalogueDriver);
        int ram = 0;
        int vCPU = 0;
        int disk = 0;

        if(nfvoCatalogueDriver.equals("NMRO")){
            log.info("Going to query NSD by ID "+nsdId);
            Map<String, String> filterParams = new HashMap();
            filterParams.put("NSD_ID", nsdId);
            QueryNsdResponse nsdResp = nfvoCatalogueService.queryNsd(new GeneralizedQueryRequest(new Filter(filterParams), null));
            log.info("Query result size {}",nsdResp.getQueryResult().size());
            NsdInfo nsdInfo = nsdResp.getQueryResult().get(0);
            return computeVirtualResourceUsageNmro(nsdInfo);
        }

        QueryNsdResponse nsdResp = nfvoCatalogueService.queryNsd(new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildNsdFilter(nsdId, nsdVersion), null)); //For not NMRO
        nsd=nsdResp.getQueryResult().get(0).getNsd();

        //TODO: parse the MEC app data when available

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
