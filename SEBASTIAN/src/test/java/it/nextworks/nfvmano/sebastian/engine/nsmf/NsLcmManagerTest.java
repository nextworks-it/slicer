package it.nextworks.nfvmano.sebastian.engine.nsmf;

import it.nextworks.nfvmano.libs.catalogues.interfaces.elements.NsdInfo;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.QueryNsdResponse;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.libs.descriptors.nsd.Sapd;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.InstantiateNsRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.QueryNsResponse;
import it.nextworks.nfvmano.libs.records.nsinfo.NsInfo;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.admin.elements.Sla;
import it.nextworks.nfvmano.sebastian.admin.elements.SlaVirtualResourceConstraint;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorResponse;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorService;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsDescriptor;
import it.nextworks.nfvmano.sebastian.catalogue.repo.VsDescriptorRepository;
import it.nextworks.nfvmano.sebastian.engine.Engine;
import it.nextworks.nfvmano.sebastian.engine.messages.InstantiateNsiRequestMessage;
import it.nextworks.nfvmano.sebastian.engine.messages.NotifyNfvNsiStatusChange;
import it.nextworks.nfvmano.sebastian.engine.messages.NsStatusChange;
import it.nextworks.nfvmano.sebastian.engine.messages.TerminateNsiRequestMessage;
import it.nextworks.nfvmano.sebastian.nfvodriver.NfvoService;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceStatus;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.translator.NfvNsInstantiationInfo;
import it.nextworks.nfvmano.sebastian.translator.TranslatorService;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.InstantiateVsRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class NsLcmManagerTest {
    NfvoService nfvoMock;
    VsRecordService vsRecordServiceMock;
    Engine engineMock;
    NsdInfo nsdInfoMock;
    AdminService adminServiceMock;

    ArbitratorRequest arbitratorRequestMock;
    Nsd nsdMock;
    NetworkSliceInstance networkSliceInstanceMock;
    Tenant tenantMock;
    Sla tenantSlaMock;
    SlaVirtualResourceConstraint scMock;

    TranslatorService translatorServiceMock;
    VsDescriptorRepository vsDescriptorRepositoryMock;
    ArbitratorService arbitratorServiceMock;
    InstantiateVsRequest instantiateVsRequestMock;
    VsDescriptor vsDescriptorMock;
    NfvNsInstantiationInfo nfvNsInstantiationInfoMock;
    ArbitratorResponse arbitratorResponseMock;
    VerticalServiceInstance verticalServiceInstanceMock;
    QueryNsdResponse queryNsdResponseMock;
    Sapd sapdMock;
    QueryNsResponse queryNsResponseMock;
    NsInfo nsInfoMock;



    @Before
    public void init(){
        this.nfvoMock = mock(NfvoService.class);
        this.vsRecordServiceMock = mock(VsRecordService.class);
        this.engineMock = mock(Engine.class);
        this.nsdInfoMock = mock(NsdInfo.class);
        this.adminServiceMock = mock(AdminService.class);
        this.translatorServiceMock = mock(TranslatorService.class);
        this.arbitratorRequestMock = mock(ArbitratorRequest.class);
        this.nsdMock = mock(Nsd.class);
        this.networkSliceInstanceMock = mock(NetworkSliceInstance.class);
        this.tenantMock = mock(Tenant.class);
        this.tenantSlaMock = mock(Sla.class);
        this.scMock = mock(SlaVirtualResourceConstraint.class);

        this.vsDescriptorRepositoryMock = mock(VsDescriptorRepository.class);
        this.arbitratorServiceMock = mock(ArbitratorService.class);
        this.instantiateVsRequestMock = mock(InstantiateVsRequest.class);
        this.vsDescriptorMock = mock(VsDescriptor.class);
        this.nfvNsInstantiationInfoMock = mock(NfvNsInstantiationInfo.class);
        this.arbitratorResponseMock = mock(ArbitratorResponse.class);
        this.verticalServiceInstanceMock = mock(VerticalServiceInstance.class);
        this.queryNsdResponseMock = mock(QueryNsdResponse.class);
        this.sapdMock = mock(Sapd.class);
        this.queryNsResponseMock = mock(QueryNsResponse.class);
        this.nsInfoMock = mock(NsInfo.class);

    }
    @Test
    public void testProcessInstantiateRequest() throws Exception {

        NsLcmManager nsLcmManager = new NsLcmManager("nsiId", "nsName", "nsDescription", "tenantId", nfvoMock, vsRecordServiceMock, engineMock);
        InstantiateNsiRequestMessage nsiRequestMessage =
                new InstantiateNsiRequestMessage("nsiId", "nfvNsdId", "nvfNsdVersion", "dfId", "ilId", Collections.singletonList("nsSubnetId"));

        when(nfvoMock.queryNsd(any())).thenReturn(this.queryNsdResponseMock);
        when(queryNsdResponseMock.getQueryResult()).thenReturn(Collections.singletonList(nsdInfoMock));
        when(nsdInfoMock.getNsdInfoId()).thenReturn("nsdInfoId");
        when(nsdInfoMock.getNsd()).thenReturn(nsdMock);
        when(nfvoMock.createNsIdentifier(any())).thenReturn("nfvNsId");
        when(nsdMock.getSapd()).thenReturn(Collections.singletonList(sapdMock));
        when(sapdMock.getCpdId()).thenReturn("cpdId");
        when(vsRecordServiceMock.getNsInstance("nsSubnetId")).thenReturn(networkSliceInstanceMock);
        when(networkSliceInstanceMock.getNfvNsId()).thenReturn("nsSubnetNfvId");
        when(networkSliceInstanceMock.getNsiId()).thenReturn("nsSubnetId");
        when(nfvoMock.instantiateNs(any())).thenReturn("operationId");

        nsLcmManager.processInstantiateRequest(nsiRequestMessage);

        verify(vsRecordServiceMock, times(1)).setNfvNsiInNsi("nsiId", "nfvNsId");
        
        // Validate the instantiation request is as it should be
        ArgumentCaptor<InstantiateNsRequest> captor = ArgumentCaptor.forClass(InstantiateNsRequest.class);
        verify(nfvoMock).instantiateNs(captor.capture());
        InstantiateNsRequest req = captor.getValue();
        assertEquals(req.getNsInstanceId(), "nfvNsId");
        assertEquals(req.getFlavourId(), "dfId");
        assertTrue(req.getPnfInfo().isEmpty());
        assertTrue(req.getVnfInstanceData().isEmpty());
        assertEquals(req.getNestedNsInstanceId(), Collections.singletonList("nsSubnetNfvId"));
        assertEquals(req.getNsInstantiationLevelId(), "ilId");

        NotifyNfvNsiStatusChange msg = new NotifyNfvNsiStatusChange("nfvNsId", NsStatusChange.NS_CREATED, true);

        when(nfvoMock.queryNs(any())).thenReturn(queryNsResponseMock);
        when(queryNsResponseMock.getQueryNsResult()).thenReturn(Collections.singletonList(nsInfoMock));
        when(nsInfoMock.getNestedNsInfoId()).thenReturn(Collections.singletonList("nfvNsToBeNotFound"));
        when(vsRecordServiceMock.getNsInstanceFromNfvNsi("nfvNsToBeNotFound")).thenThrow(NotExistingEntityException.class);
        when(vsRecordServiceMock.createNetworkSliceInstanceEntry(null,
                null, null, null, "nfvNsToBeNotFound", null,
                null, null, null, true)).thenReturn("nsiNotToBeFound");
        nsLcmManager.processNfvNsChangeNotification(msg);

        verify(vsRecordServiceMock, times(1)).addNsSubnetsInNetworkSliceInstance("nsiId", Collections.singletonList("nsiNotToBeFound"));
        verify(vsRecordServiceMock, times(1)).setNsStatus("nsiId", NetworkSliceStatus.INSTANTIATED);
        verify(engineMock, times(1)).notifyNetworkSliceStatusChange("nsiId", NsStatusChange.NS_CREATED, true);

        TerminateNsiRequestMessage message = new TerminateNsiRequestMessage("nsiId");
        when(nfvoMock.terminateNs(any())).thenReturn("operationId");
        nsLcmManager.processTerminateRequest(message);

        msg = new NotifyNfvNsiStatusChange("nfvNsId", NsStatusChange.NS_TERMINATED, true);
        nsLcmManager.processNfvNsChangeNotification(msg);
        verify(vsRecordServiceMock, times(1)).setNsStatus("nsiNotToBeFound", NetworkSliceStatus.TERMINATED);

    }

    @Test
    public void testprocessNfvNsChangeNotification() throws Exception {

        String networkSliceInstanceId = "nsiId";
        String name= "nsName";
        String descritpion = "nsDescription";
        String tenantId = "teantId";
        String nfvNsiId = "nfvNsiId";

        NsLcmManager nsLcmManager = new NsLcmManager(networkSliceInstanceId, name, descritpion, tenantId, nfvoMock, vsRecordServiceMock, engineMock);
        NotifyNfvNsiStatusChange msg = new NotifyNfvNsiStatusChange(nfvNsiId, NsStatusChange.NS_CREATED, true);

        nsLcmManager.processNfvNsChangeNotification(msg);
    }
}
