package it.nextworks.nfvmano;

import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnBoardVnfPackageRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnBoardVnfPackageResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnboardNsdRequest;
import it.nextworks.nfvmano.libs.ifa.common.elements.AffinityRule;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.AffinityOrAntiAffinityGroup;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.LifeCycleManagementScript;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualLinkProfile;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.*;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

//Class used only for SS-O NMR-O integration purposes.
//It on boards an existing NSD available on OSM.
// Please consider to use only for this purpose.
// It will be deleted once The catalogue interface to OSM (via NMR-O) will be implemented.

@Service
public class NsdOnboarder {

    @Autowired
    private NfvoCatalogueService nfvoCatalogueService;

    private Nsd nsd;
    private static final Logger log = LoggerFactory.getLogger(NsdOnboarder.class);

    public void onBoardCustomNsd(){

        OnBoardVnfPackageRequest request=
                new OnBoardVnfPackageRequest("vnfPackageName",
                "vnfPackageVersion", "vnfPackageProvider",
                        "vnfPackageCheckSum", new HashMap<>(),
                        "http://10.0.2.180/vnfd/vnfd_vVS.tar");

        try {
            OnBoardVnfPackageResponse onBoardVnfPackageResponse=nfvoCatalogueService.onBoardVnfPackage(request);
            String vnfPkgInfoId = onBoardVnfPackageResponse.getOnboardedVnfPkgInfoId();
        } catch (MethodNotImplementedException e) {
            e.printStackTrace();
        } catch (AlreadyExistingEntityException e) {
            e.printStackTrace();
        } catch (FailedOperationException e) {
            e.printStackTrace();
        } catch (MalformattedElementException e) {
            e.printStackTrace();
        }

        VnfProfile vnfProfile = new VnfProfile(null,
                "vnfProfileId",
                "vVS",   "vVS_df",
                "vVS_il",   1,    1,
        new ArrayList<AffinityRule > (),
                new ArrayList<String> (),
                new ArrayList<NsVirtualLinkConnectivity> ());
        ArrayList<VnfProfile> vnfProfiles=new ArrayList<VnfProfile>();
        vnfProfiles.add(vnfProfile);

        VnfToLevelMapping vnfToLevelMapping = new VnfToLevelMapping("vnfProfileId",1);
        ArrayList<VnfToLevelMapping> vnfToLevelMappings=new ArrayList<VnfToLevelMapping> ();
        vnfToLevelMappings.add(vnfToLevelMapping);

        NsLevel nsLevel = new NsLevel((NsDf) null, "nsLevelId", "descr", vnfToLevelMappings, new ArrayList<NsToLevelMapping> ());
        ArrayList<NsLevel> nsLevels= new ArrayList<NsLevel> ();
        nsLevels.add(nsLevel);

        NsDf nsDf = new NsDf(null,
                "nsdDfId", "flavourKey",
                vnfProfiles,
                new ArrayList< PnfProfile>(),
                new ArrayList< VirtualLinkProfile > (),
                new ArrayList<NsScalingAspect> (),
                new ArrayList< AffinityOrAntiAffinityGroup > (),
                nsLevels,
                null,
                new ArrayList<NsProfile> (),
                new ArrayList<Dependencies> ());

        ArrayList<NsDf> nsdfs= new ArrayList<NsDf> ();
        nsdfs.add(nsDf);

        nsd = new Nsd("09f0f070-11a8-4fc3-8a47-253508673a99",
                "SSSA-NXW",
                "1.0", "nsName", "09f0f070-11a8-4fc3-8a47-253508673a99",
                new ArrayList<String> (), new ArrayList<String> ()/*maybe to change*/,
                new ArrayList<String> (), new ArrayList<Sapd> (),
                new ArrayList<NsVirtualLinkDesc> (), new ArrayList<Vnffgd> (),
                new ArrayList<MonitoredData> (), new ArrayList<NsAutoscalingRule> (),
                new ArrayList< LifeCycleManagementScript > (), nsdfs, new SecurityParameters());
        try {
            String nsdId=nfvoCatalogueService.onboardNsd(new OnboardNsdRequest(nsd, new HashMap<>()));
            log.info("nsd UIID generated "+nsdId);
        } catch (MethodNotImplementedException e) {
            e.printStackTrace();
        } catch (MalformattedElementException e) {
            e.printStackTrace();
        } catch (AlreadyExistingEntityException e) {
            e.printStackTrace();
        } catch (FailedOperationException e) {
            e.printStackTrace();
        }
    }
}
