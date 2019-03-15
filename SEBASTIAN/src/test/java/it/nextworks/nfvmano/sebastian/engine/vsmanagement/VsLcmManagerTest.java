package it.nextworks.nfvmano.sebastian.engine.vsmanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.libs.catalogues.interfaces.elements.NsdInfo;
import it.nextworks.nfvmano.libs.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.admin.elements.Sla;
import it.nextworks.nfvmano.sebastian.admin.elements.SlaVirtualResourceConstraint;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorResponse;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorService;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsDescriptor;
import it.nextworks.nfvmano.sebastian.catalogue.repo.VsDescriptorRepository;
import it.nextworks.nfvmano.sebastian.common.Utilities;
import it.nextworks.nfvmano.sebastian.engine.Engine;
import it.nextworks.nfvmano.sebastian.engine.messages.InstantiateVsiRequestMessage;
import it.nextworks.nfvmano.sebastian.nfvodriver.NfvoService;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceStatus;
import it.nextworks.nfvmano.sebastian.translator.NfvNsInstantiationInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import it.nextworks.nfvmano.sebastian.translator.TranslatorService;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.InstantiateVsRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class VsLcmManagerTest {
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

    }
    @Test
    public void testProcessInstantiateRequest() throws Exception{

        NfvNsInstantiationInfo nsInstantiationInfo =
                new NfvNsInstantiationInfo("nfvID", "nsdVersion", "deploymentFlavour", "instatiationLevelId");

        InstantiateVsRequest instantiateVsRequest = new InstantiateVsRequest("Vsname", "vsdescription", "vsdId", "tenantId", "stringa",null);
        VsLcmManager vsLcmManager = new VsLcmManager
                ("vsiId", vsRecordServiceMock, vsDescriptorRepositoryMock, translatorServiceMock, arbitratorServiceMock, adminServiceMock, nfvoMock, engineMock);

        InstantiateVsiRequestMessage msg = new InstantiateVsiRequestMessage("vsiId", instantiateVsRequest);
        when(instantiateVsRequestMock.getVsdId()).thenReturn("vsdId");
        when(vsDescriptorRepositoryMock.findByVsDescriptorId("vsdId")).thenReturn(Optional.of(vsDescriptorMock));
        /*when(instantiateVsRequestMock.getTenantId()).thenReturn("tenantId");
        when(instantiateVsRequestMock.getName()).thenReturn("tenantName");
        when(instantiateVsRequestMock.getDescription()).thenReturn("tenantDescription");*/
        when(translatorServiceMock.translateVsd(any())).thenReturn(Collections.singletonMap("vsdId", nsInstantiationInfo));
        when(arbitratorServiceMock.computeArbitratorSolution(any())).thenReturn(Collections.singletonList(arbitratorResponseMock));
        when(arbitratorResponseMock.isAcceptableRequest()).thenReturn(true);
        when(arbitratorResponseMock.isNewSliceRequired()).thenReturn(true);
        when(arbitratorResponseMock.getExistingSliceSubnets()).thenReturn(Collections.singletonMap("nsSubnetId", false));
        when(vsRecordServiceMock.createNetworkSliceForVsi(any(), any(),any(),any(),any(),any(), any(), any(), any())).thenReturn("nsiId");
        when(verticalServiceInstanceMock.getVsiId()).thenReturn("vsiIdNested");
        when(vsRecordServiceMock.getVsInstancesFromNetworkSlice("nsSubnetId")).thenReturn(Collections.singletonList(verticalServiceInstanceMock));


        vsLcmManager.processInstantiateRequest(msg);

        verify(vsRecordServiceMock, times(1)).setNsiInVsi("vsiId", "nsiId");
        verify(engineMock, times(1)).initNewNsLcmManager(any(), any(), any(), any());
        verify(engineMock, times(1)).instantiateNs(any(), any(), any(), any(), any(), any(), any(), any());
        //System.out.println(vsLcmManager.getNestedVsi().get(0));

    }

}
