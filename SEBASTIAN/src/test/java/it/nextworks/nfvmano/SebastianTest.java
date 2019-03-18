package it.nextworks.nfvmano;

import it.nextworks.nfvmano.libs.catalogues.interfaces.elements.NsdInfo;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.QueryNsdResponse;
import it.nextworks.nfvmano.libs.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.libs.descriptors.nsd.Sapd;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.QueryNsResponse;
import it.nextworks.nfvmano.libs.records.nsinfo.NsInfo;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.admin.elements.Sla;
import it.nextworks.nfvmano.sebastian.admin.elements.SlaVirtualResourceConstraint;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.admin.elements.VirtualResourceUsage;
import it.nextworks.nfvmano.sebastian.catalogue.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsDescriptor;
import it.nextworks.nfvmano.sebastian.catalogue.repo.VsDescriptorRepository;
import it.nextworks.nfvmano.sebastian.engine.Engine;
import it.nextworks.nfvmano.sebastian.engine.messages.NsStatusChange;
import it.nextworks.nfvmano.sebastian.nfvodriver.NfvoService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.translator.NfvNsInstantiationInfo;
import it.nextworks.nfvmano.sebastian.translator.TranslatorService;
import it.nextworks.nfvmano.sebastian.vsnbi.VsLcmService;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.InstantiateVsRequest;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.PurgeVsRequest;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.TerminateVsRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SebastianTest {

    @MockBean
    private NfvoService nfvoService;

    @MockBean
    private VsDescriptorCatalogueService vsDescriptorCatalogueService;

    @MockBean
    private AdminService adminService;

    @MockBean
    private TranslatorService translatorService;

    @MockBean
    private VsDescriptorRepository vsDescriptorRepository;

    @Autowired
    VsLcmService vsLcmService;

    @Autowired
    Engine engine;

    /**
     * This test cover the workflow: createChildVsi -> createParentVsi -> terminateParentVsi -> purgeParentVsi
     * NOTE: parentVsi will reuse childVsi as nested Vsi
     * @throws Exception
     */
    @Test
    public void vsLcTest() throws Exception {
        /**
         * Local Mocks
         */

        VsDescriptor vsdMock = mock(VsDescriptor.class);
        Nsd nsdMock = new TestNsds().makeEMonNsd();
        Tenant tenantMock = mock(Tenant.class);
        Sla tenantSlaMock = mock(Sla.class);
        SlaVirtualResourceConstraint scMock = mock(SlaVirtualResourceConstraint.class);
        NsdInfo nsdInfoMock = mock(NsdInfo.class);
        QueryNsdResponse queryNsdResponseMock = mock(QueryNsdResponse.class);
        Sapd sapdMock = mock(Sapd.class);
        QueryNsResponse queryNsResponseMock = mock(QueryNsResponse.class);
        NsInfo nsInfoMock = mock(NsInfo.class);

        /**
         * Instantiation Request
         * NsInstance info after translation process
         */
        InstantiateVsRequest instantiateVsRequest = new InstantiateVsRequest(
                "vsName",
                "vsDescription",
                "vsdId",
                "tenantId",
                "NotificationUrl",
                null );
        NfvNsInstantiationInfo nsInstantiationInfo = new NfvNsInstantiationInfo(
                nsdMock.getNsdIdentifier(),
                nsdMock.getVersion(),
                "deploymentFlavour",
                "instatiationLevelId");
        /**
         * VsLcmService
         */
        when(vsDescriptorCatalogueService.getVsd("vsdId")).thenReturn(vsdMock);
        when(vsdMock.getTenantId()).thenReturn("tenantId");

        /**
         * VsLcmManager
         */
        when(translatorService.translateVsd(Collections.singletonList("vsdId"))).thenReturn(Collections.singletonMap("vsdId", nsInstantiationInfo));
        when(vsDescriptorRepository.findByVsDescriptorId("vsdId")).thenReturn(Optional.of(vsdMock));
        /**
         * Arbitrator (Basic Arbitrator)
         */
        when(nfvoService.queryNsdAssumingOne(nsdMock.getNsdIdentifier(), nsdMock.getVersion())).thenReturn(nsdMock);
        //when(nsdMock.getNestedNsdId()).thenReturn(Collections.emptyList());
        when(nfvoService.computeVirtualResourceUsage(any(NfvNsInstantiationInfo.class))).thenReturn(new VirtualResourceUsage(10, 10, 10));
        when(adminService.getTenant("tenantId")).thenReturn(tenantMock);
        when(tenantMock.getActiveSla()).thenReturn(tenantSlaMock);
        when(tenantSlaMock.getGlobalConstraint()).thenReturn(scMock);
        when(scMock.getMaxResourceLimit()).thenReturn(new VirtualResourceUsage(50, 50, 50));
        when(tenantMock.getAllocatedResources()).thenReturn(new VirtualResourceUsage()); // 0, 0, 0

        /**
         * NsLcmManager
         */
        when(nfvoService.queryNsd(any())).thenReturn(queryNsdResponseMock);
        when(queryNsdResponseMock.getQueryResult()).thenReturn(Collections.singletonList(nsdInfoMock));
        when(nsdInfoMock.getNsdInfoId()).thenReturn("nsdInfoId");
        when(nsdInfoMock.getNsd()).thenReturn(nsdMock);
        when(nfvoService.createNsIdentifier(any())).thenReturn("nfvNsId");
        //when(nsdMock.getSapd()).thenReturn(Collections.singletonList(sapdMock));
        //when(sapdMock.getCpdId()).thenReturn("cpdId");
        when(nfvoService.instantiateNs(any())).thenReturn("INSTANTIATE_NS");

        /**
         * INSTANTIATE VS
         */
        vsLcmService.instantiateVs(instantiateVsRequest);
        Thread.sleep(5000);  // Simulate NFVO response waiting

        /**
         * After NFV NS_CREATED
         * NSLcmManager
         */
        when(nfvoService.queryNs(any())).thenReturn(queryNsResponseMock);
        when(queryNsResponseMock.getQueryNsResult()).thenReturn(Collections.singletonList(nsInfoMock));
        when(nsInfoMock.getNestedNsInfoId()).thenReturn(Collections.emptyList());

        /**
         * VsLcmManager
         */
        when(nfvoService.computeVirtualResourceUsage(any(NetworkSliceInstance.class))).thenReturn(new VirtualResourceUsage(10,10,10));

        /**
         * Triggering NFVO NS_CREATED Notification
         */
        engine.notifyNfvNsStatusChange("nfvNsId", NsStatusChange.NS_CREATED, true);
        Thread.sleep(5000); //

        /**
         * NEW VS Instantiation starts here
         * tenantId, nsdVersion, deploymentFlavour and instatiationLevelId don't change
         */

        nsdMock = new TestNsds().makeHealtEmergencyNsd();

        instantiateVsRequest = new InstantiateVsRequest(
                "vsParentName",
                "vsParentDescription",
                "vsdParentId",
                "tenantId",
                "ParentNotificationUrl",
                null );
        nsInstantiationInfo = new NfvNsInstantiationInfo(
                nsdMock.getNsdIdentifier(),
                nsdMock.getVersion(),
                "deploymentFlavour",
                "instatiationLevelId");

        /**
         * VsLcmService
         */
        when(vsDescriptorCatalogueService.getVsd("vsdParentId")).thenReturn(vsdMock);
        when(vsdMock.getTenantId()).thenReturn("tenantId");

        /**
         * VsLcmManager
         */
        when(translatorService.translateVsd(any())).thenReturn(Collections.singletonMap("vsdParentId", nsInstantiationInfo));
        when(vsDescriptorRepository.findByVsDescriptorId("vsdParentId")).thenReturn(Optional.of(vsdMock));

        /**
         * Arbitrator (Basic Arbitrator)
         */
        when(nfvoService.queryNsdAssumingOne(nsdMock.getNsdIdentifier(), nsdMock.getVersion())).thenReturn(nsdMock);
        //when(nsdMock.getNestedNsdId()).thenReturn(Collections.singletonList("nfvNsdId")); //returning the existing ID (previously created)
        when(nfvoService.computeVirtualResourceUsage(any(NfvNsInstantiationInfo.class))).thenReturn(new VirtualResourceUsage(15, 5, 5));
        when(adminService.getTenant("tenantId")).thenReturn(tenantMock);
        when(tenantMock.getActiveSla()).thenReturn(tenantSlaMock);
        when(tenantSlaMock.getGlobalConstraint()).thenReturn(scMock);
        when(scMock.getMaxResourceLimit()).thenReturn(new VirtualResourceUsage(50, 50, 50));
        when(tenantMock.getAllocatedResources()).thenReturn(new VirtualResourceUsage(10, 10, 10)); // Previously allocated

        /**
         * NsLcmManager
         */
        when(nfvoService.queryNsd(any())).thenReturn(queryNsdResponseMock);
        when(queryNsdResponseMock.getQueryResult()).thenReturn(Collections.singletonList(nsdInfoMock));
        when(nsdInfoMock.getNsdInfoId()).thenReturn("nsdInfoParentId");
        when(nsdInfoMock.getNsd()).thenReturn(nsdMock);
        when(nfvoService.createNsIdentifier(any())).thenReturn("nfvNsParentId");
        //when(nsdMock.getSapd()).thenReturn(Collections.singletonList(sapdMock));
        //when(sapdMock.getCpdId()).thenReturn("cpdParentId");
        when(nfvoService.instantiateNs(any())).thenReturn("INSTANTIATE_PARENT_NS");

        /**
         * INSTANTIATE PARENT VS
         */
        vsLcmService.instantiateVs(instantiateVsRequest);
        Thread.sleep(5000);  // Simulate NFVO response waiting

        /**
         * After NFV NS_CREATED (parent)
         * NSLcmManager
         */
        when(nfvoService.queryNs(any())).thenReturn(queryNsResponseMock);
        when(queryNsResponseMock.getQueryNsResult()).thenReturn(Collections.singletonList(nsInfoMock));
        when(nsInfoMock.getNestedNsInfoId()).thenReturn(Collections.singletonList("partentSoCreatedNs"));

        /**
         * VsLcmManager
         */
        when(nfvoService.computeVirtualResourceUsage(any(NetworkSliceInstance.class))).thenReturn(new VirtualResourceUsage(25,15,15));

        /**
         * Triggering NFVO NS_CREATED Notification
         */
        engine.notifyNfvNsStatusChange("nfvNsParentId", NsStatusChange.NS_CREATED, true);
        Thread.sleep(5000); //

        /************************************************** TERMINATION ***********************************************/

        /**
         * TERMINATE VS
         * termination message
         */
        TerminateVsRequest terminateVsRequest = new TerminateVsRequest(
                "3",
                "tenantId"
        );

        /**
         * NsLcmManager
         */
        when(nfvoService.terminateNs(any())).thenReturn("TERMINATE_PARENT_NS");

        /**
         * TERMINATE VS PARENT
         */
        vsLcmService.terminateVs(terminateVsRequest);
        Thread.sleep(5000);

        /**
         * Triggering NFVO NS_TERMINATED Notification
         */
        engine.notifyNfvNsStatusChange("nfvNsParentId", NsStatusChange.NS_TERMINATED, true);
        Thread.sleep(5000);

        /****************************************************** PURGE *************************************************/

        /**
         * Purge VS
         * purge message
         */
        PurgeVsRequest purgeVsRequest= new PurgeVsRequest(
                "3",
                "tenantId"
        );

        /**
         * Triggering VS PURGE
         */
        vsLcmService.purgeVs(purgeVsRequest);
        Thread.sleep(500);

    }

}
