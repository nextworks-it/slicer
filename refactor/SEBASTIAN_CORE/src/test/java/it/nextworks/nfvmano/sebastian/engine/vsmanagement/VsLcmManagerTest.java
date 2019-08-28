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
package it.nextworks.nfvmano.sebastian.engine.vsmanagement;

import it.nextworks.nfvmano.libs.catalogues.interfaces.elements.NsdInfo;
import it.nextworks.nfvmano.libs.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.admin.elements.Sla;
import it.nextworks.nfvmano.sebastian.admin.elements.SlaVirtualResourceConstraint;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.admin.elements.VirtualResourceUsage;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorResponse;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorService;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsDescriptor;
import it.nextworks.nfvmano.sebastian.common.VirtualResourceCalculatorService;
import it.nextworks.nfvmano.sebastian.engine.Engine;
import it.nextworks.nfvmano.sebastian.engine.messages.InstantiateVsiRequestMessage;
import it.nextworks.nfvmano.sebastian.engine.messages.TerminateVsiRequestMessage;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmService;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceStatus;
import it.nextworks.nfvmano.sebastian.translator.NfvNsInstantiationInfo;

import it.nextworks.nfvmano.sebastian.translator.TranslatorService;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.InstantiateVsRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class VsLcmManagerTest {
    NfvoCatalogueService nfvoCatalogueMock;
    NfvoLcmService nfvoLcmMock;
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
    VsDescriptorCatalogueService vsDescriptorCatalogueServiceMock;
    ArbitratorService arbitratorServiceMock;
    InstantiateVsRequest instantiateVsRequestMock;
    VsDescriptor vsDescriptorMock;
    NfvNsInstantiationInfo nfvNsInstantiationInfoMock;
    ArbitratorResponse arbitratorResponseMock;
    VerticalServiceInstance verticalServiceInstanceMock;
    VirtualResourceCalculatorService virtualResourceCalculatorServiceMock;
    @Before
    public void init(){
        this.nfvoCatalogueMock = mock(NfvoCatalogueService.class);
        this.nfvoLcmMock = mock(NfvoLcmService.class);
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

        this.vsDescriptorCatalogueServiceMock = mock(VsDescriptorCatalogueService.class);
        this.arbitratorServiceMock = mock(ArbitratorService.class);
        this.instantiateVsRequestMock = mock(InstantiateVsRequest.class);
        this.vsDescriptorMock = mock(VsDescriptor.class);
        this.nfvNsInstantiationInfoMock = mock(NfvNsInstantiationInfo.class);
        this.arbitratorResponseMock = mock(ArbitratorResponse.class);
        this.verticalServiceInstanceMock = mock(VerticalServiceInstance.class);
        this.virtualResourceCalculatorServiceMock = mock(VirtualResourceCalculatorService.class);
    }

    @Test
    public void testProcessInstantiateRequest() throws Exception{

        NfvNsInstantiationInfo nsInstantiationInfo =
                new NfvNsInstantiationInfo("nfvID", "nsdVersion", "deploymentFlavour", "instatiationLevelId");

        InstantiateVsRequest instantiateVsRequest = new InstantiateVsRequest("Vsname", "vsdescription", 
        		"vsdId", "tenantId", "stringa",null, null);
        VsLcmManager vsLcmManager = new VsLcmManager(
                "vsiId",
                vsRecordServiceMock,
                vsDescriptorCatalogueServiceMock,
                translatorServiceMock,
                arbitratorServiceMock,
                adminServiceMock,
                nfvoCatalogueMock,
                engineMock,
                virtualResourceCalculatorServiceMock
        );

        InstantiateVsiRequestMessage msg = new InstantiateVsiRequestMessage("vsiId", instantiateVsRequest);
        when(instantiateVsRequestMock.getVsdId()).thenReturn("vsdId");
        when(vsDescriptorCatalogueServiceMock.findByVsDescriptorId("vsdId")).thenReturn(Optional.of(vsDescriptorMock));
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

    @Test
    public void testTerminateLastService() throws Exception {
        String id = "vsiId";
        VsLcmManager vsLcmManager = new VsLcmManager(
                id,
                vsRecordServiceMock,
                vsDescriptorCatalogueServiceMock,
                translatorServiceMock,
                arbitratorServiceMock,
                adminServiceMock,
                nfvoCatalogueMock,
                engineMock,
                virtualResourceCalculatorServiceMock
        );
        String sliceId = "nsiId";
        vsLcmManager.setNetworkSliceId(sliceId);
        vsLcmManager.setInternalStatus(VerticalServiceStatus.INSTANTIATED);
        VerticalServiceInstance vsi = mock(VerticalServiceInstance.class);
        when(vsRecordServiceMock.getVsInstancesFromNetworkSlice(sliceId)).thenReturn(Collections.singletonList(vsi));

        TerminateVsiRequestMessage message = new TerminateVsiRequestMessage(id);
        vsLcmManager.processTerminateRequest(message);

        verify(vsRecordServiceMock, times(1)).setVsStatus(id, VerticalServiceStatus.TERMINATING);
        verify(engineMock, times(1)).terminateNs(sliceId);

    }

    @Test
    public void testTerminateShared() throws Exception {
        String id = "vsiId";
        VsLcmManager vsLcmManager = new VsLcmManager(
                id,
                vsRecordServiceMock,
                vsDescriptorCatalogueServiceMock,
                translatorServiceMock,
                arbitratorServiceMock,
                adminServiceMock,
                nfvoCatalogueMock,
                engineMock,
                virtualResourceCalculatorServiceMock
        );
        String sliceId = "nsiId";
        vsLcmManager.setNetworkSliceId(sliceId);
        vsLcmManager.setInternalStatus(VerticalServiceStatus.INSTANTIATED);
        vsLcmManager.setTenantId("tenantId");

        VerticalServiceInstance vsi1 = mock(VerticalServiceInstance.class);
        VerticalServiceInstance vsi2 = mock(VerticalServiceInstance.class);
        VirtualResourceUsage vruMock = mock(VirtualResourceUsage.class);

        when(vsRecordServiceMock.getVsInstancesFromNetworkSlice(sliceId)).thenReturn(Arrays.asList(vsi1, vsi2));
        when(vsRecordServiceMock.getNsInstance(sliceId)).thenReturn(networkSliceInstanceMock);
        when(virtualResourceCalculatorServiceMock.computeVirtualResourceUsage(networkSliceInstanceMock, true)).thenReturn(vruMock);

        TerminateVsiRequestMessage message = new TerminateVsiRequestMessage(id);
        vsLcmManager.processTerminateRequest(message);

        verify(vsRecordServiceMock, times(1)).setVsStatus(id, VerticalServiceStatus.TERMINATING);
        verify(adminServiceMock, times(1)).removeUsedResourcesInTenant("tenantId", vruMock);
        verify(vsRecordServiceMock, times(1)).setVsStatus(id, VerticalServiceStatus.TERMINATED);
        assert vsLcmManager.getInternalStatus().equals(VerticalServiceStatus.TERMINATED);
    }

}
