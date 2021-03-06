///*
//* Copyright 2018 Nextworks s.r.l.
//*
//* Licensed under the Apache License, Version 2.0 (the "License");
//* you may not use this file except in compliance with the License.
//* You may obtain a copy of the License at
//*
//*     http://www.apache.org/licenses/LICENSE-2.0
//*
//* Unless required by applicable law or agreed to in writing, software
//* distributed under the License is distributed on an "AS IS" BASIS,
//* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//* See the License for the specific language governing permissions and
//* limitations under the License.
//*/
//package it.nextworks.nfvmano;
//
//import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.elements.NsdInfo;
//import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.QueryNsdResponse;
//import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
//import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Nsd;
//import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Sapd;
//import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.QueryNsResponse;
//import it.nextworks.nfvmano.libs.ifa.records.nsinfo.NsInfo;
//import it.nextworks.nfvmano.sebastian.admin.AdminService;
//import it.nextworks.nfvmano.sebastian.admin.elements.Sla;
//import it.nextworks.nfvmano.sebastian.admin.elements.SlaVirtualResourceConstraint;
//import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
//import it.nextworks.nfvmano.sebastian.admin.elements.VirtualResourceUsage;
//
//import it.nextworks.nfvmano.catalogue.blueprint.services.VsBlueprintCatalogueService;
//import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
//import it.nextworks.nfvmano.catalogue.blueprint.elements.VsBlueprint;
//import it.nextworks.nfvmano.catalogue.blueprint.elements.VsDescriptor;
//import it.nextworks.nfvmano.catalogue.blueprint.elements.VsdNsdTranslationRule;
//import it.nextworks.nfvmano.catalogue.blueprint.elements.VsdParameterValueRange;
//import it.nextworks.nfvmano.catalogue.blueprint.messages.OnBoardVsBlueprintRequest;
//import it.nextworks.nfvmano.sebastian.common.VirtualResourceCalculatorService;
//import it.nextworks.nfvmano.sebastian.engine.Engine;
//import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
//import it.nextworks.nfvmano.nfvodriver.NfvoLcmService;
//
//import it.nextworks.nfvmano.nfvodriver.NsStatusChange;
//import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
//import it.nextworks.nfvmano.sebastian.translator.NfvNsInstantiationInfo;
//import it.nextworks.nfvmano.sebastian.translator.TranslatorService;
//import it.nextworks.nfvmano.sebastian.vsfm.VsLcmService;
//import it.nextworks.nfvmano.sebastian.vsfm.messages.InstantiateVsRequest;
//import it.nextworks.nfvmano.sebastian.vsfm.messages.PurgeVsRequest;
//import it.nextworks.nfvmano.sebastian.vsfm.messages.TerminateVsRequest;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.*;
//
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.*;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class SebastianTest {
//
//
//    @MockBean
//    private VirtualResourceCalculatorService virtualResourceCalculatorService;
//    @MockBean
//    private NfvoCatalogueService nfvoCatalogueService;
//
//    @MockBean
//    private NfvoLcmService nfvoLcmService;
//
//    @MockBean
//    private VsDescriptorCatalogueService vsDescriptorCatalogueService;
//
//    @Autowired
//    private VsBlueprintCatalogueService vsBlueprintCatalogueService;
//
//    @MockBean
//    private AdminService adminService;
//
//    @MockBean
//    private TranslatorService translatorService;
//
//
//    @Autowired
//    VsLcmService vsLcmService;
//
//    @Autowired
//    Engine engine;
//
//    /**
//     * This test cover the workflow: createChildVsi -> createParentVsi -> terminateParentVsi -> purgeParentVsi
//     * NOTE: parentVsi will reuse childVsi as nested Vsi
//     * @throws Exception
//     */
//    @Test
//    public void vsLcTest() throws Exception {
//        /**
//         * Local Mocks
//         */
//
//    	//VsBlueprintInfo vsbInfoMock = mock(VsBlueprintInfo.class);
//    	//VsBlueprint vsbMock = mock(VsBlueprint.class);
//        VsDescriptor vsdMock = mock(VsDescriptor.class);
//        Nsd nsdMock = new TestNsds().makeEMonNsd();
//        Tenant tenantMock = mock(Tenant.class);
//        Sla tenantSlaMock = mock(Sla.class);
//        SlaVirtualResourceConstraint scMock = mock(SlaVirtualResourceConstraint.class);
//        NsdInfo nsdInfoMock = mock(NsdInfo.class);
//        QueryNsdResponse queryNsdResponseMock = mock(QueryNsdResponse.class);
//        Sapd sapdMock = mock(Sapd.class);
//        QueryNsResponse queryNsResponseMock = mock(QueryNsResponse.class);
//        NsInfo nsInfoMock = mock(NsInfo.class);
//
//        /**
//         * Instantiation Request
//         * NsInstance info after translation process
//         */
//        InstantiateVsRequest instantiateVsRequest = new InstantiateVsRequest(
//                "vsName",
//                "vsDescription",
//                "vsdId",
//                "tenantId",
//                "NotificationUrl",
//                null,
//                null);
//        NfvNsInstantiationInfo nsInstantiationInfo = new NfvNsInstantiationInfo(
//                nsdMock.getNsdIdentifier(),
//                nsdMock.getVersion(),
//                "deploymentFlavour",
//                "instatiationLevelId");
//
//        List<VsdNsdTranslationRule> translationRules = new ArrayList<>();
//        List<VsdParameterValueRange> pvrs = new ArrayList<>();
//        VsdParameterValueRange pvr = new VsdParameterValueRange("parameterId", 0, 1);
//        pvrs.add(pvr);
//        VsdNsdTranslationRule rule = new VsdNsdTranslationRule(pvrs, "nsdId", "nsdVersion", "nsFlavourId", "nsInstantiationLevelId");
//        translationRules.add(rule);
//        VsBlueprint vsb = new VsBlueprint(null, "0.1", "VsbName", null, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//        OnBoardVsBlueprintRequest request = new OnBoardVsBlueprintRequest(vsb, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), translationRules);
//        String vsbId = vsBlueprintCatalogueService.onBoardVsBlueprint(request);
//
//        /**
//         * VsLcmService
//         */
//        when(vsDescriptorCatalogueService.getVsd("vsdId")).thenReturn(vsdMock);
//        when(vsdMock.getTenantId()).thenReturn("tenantId");
//        when(vsdMock.getVsBlueprintId()).thenReturn(vsbId);
//        //when(vsbInfoMock.getCtxBlueprint()).thenReturn(vsbMock);
//        //when(vsbMock.getExpBlueprintId()).thenReturn("vsbId");
//
//
//        //when(vsBlueprintCatalogueService.queryVsBlueprint(new GeneralizedQueryRequest(Utilities.buildVsBlueprintFilter("vsbId"), null))).thenReturn(new QueryVsBlueprintResponse(Collections.singletonList(vsbInfoMock)));
//
//        /**
//         * VsLcmManager
//         */
//        when(translatorService.translateVsd(Collections.singletonList("vsdId"))).thenReturn(Collections.singletonMap("vsdId", nsInstantiationInfo));
//        when(vsDescriptorCatalogueService.findByVsDescriptorId("vsdId")).thenReturn(Optional.of(vsdMock));
//        /**
//         * Arbitrator (Basic Arbitrator)
//         */
//        when(nfvoCatalogueService.queryNsdAssumingOne(any(Filter.class))).thenReturn(nsdMock);
//        //when(nsdMock.getNestedNsdId()).thenReturn(Collections.emptyList());
//
//        when(virtualResourceCalculatorService.computeVirtualResourceUsage(any())).thenReturn(new VirtualResourceUsage(10, 10, 10));
//        when(adminService.getTenant("tenantId")).thenReturn(tenantMock);
//        when(tenantMock.getActiveSla()).thenReturn(tenantSlaMock);
//        when(tenantSlaMock.getGlobalConstraint()).thenReturn(scMock);
//        when(scMock.getMaxResourceLimit()).thenReturn(new VirtualResourceUsage(50, 50, 50));
//        when(tenantMock.getAllocatedResources()).thenReturn(new VirtualResourceUsage()); // 0, 0, 0
//
//        /**
//         * NsLcmManager
//         */
//        when(nfvoCatalogueService.queryNsd(any())).thenReturn(queryNsdResponseMock);
//        when(queryNsdResponseMock.getQueryResult()).thenReturn(Collections.singletonList(nsdInfoMock));
//        when(nsdInfoMock.getNsdInfoId()).thenReturn("nsdInfoId");
//        when(nsdInfoMock.getNsd()).thenReturn(nsdMock);
//        when(nfvoLcmService.createNsIdentifier(any())).thenReturn("nfvNsId");
//        //when(nsdMock.getSapd()).thenReturn(Collections.singletonList(sapdMock));
//        //when(sapdMock.getCpdId()).thenReturn("cpdId");
//        when(nfvoLcmService.instantiateNs(any())).thenReturn("INSTANTIATE_NS");
//
//        /**
//         * INSTANTIATE VS
//         */
//        vsLcmService.instantiateVs(instantiateVsRequest);
//        Thread.sleep(5000);  // Simulate NFVO response waiting
//
//        /**
//         * After NFV NS_CREATED
//         * NSLcmManager
//         */
//        when(nfvoLcmService.queryNs(any())).thenReturn(queryNsResponseMock);
//        when(queryNsResponseMock.getQueryNsResult()).thenReturn(Collections.singletonList(nsInfoMock));
//        when(nsInfoMock.getNestedNsInfoId()).thenReturn(Collections.emptyList());
//
//        /**
//         * VsLcmManager
//         */
//        when(virtualResourceCalculatorService.computeVirtualResourceUsage(any(NetworkSliceInstance.class), eq(true))).thenReturn(new VirtualResourceUsage(10,10,10));
//
//        /**
//         * Triggering NFVO NS_CREATED Notification
//         */
//        engine.notifyNfvNsStatusChange("nfvNsId", NsStatusChange.NS_CREATED, true);
//        Thread.sleep(5000); //
//
//        /**
//         * NEW VS Instantiation starts here
//         * tenantId, nsdVersion, deploymentFlavour and instatiationLevelId don't change
//         */
//
//        nsdMock = new TestNsds().makeHealtEmergencyNsd();
//
//        instantiateVsRequest = new InstantiateVsRequest(
//                "vsParentName",
//                "vsParentDescription",
//                "vsdParentId",
//                "tenantId",
//                "ParentNotificationUrl",
//                null,
//                null);
//        nsInstantiationInfo = new NfvNsInstantiationInfo(
//                nsdMock.getNsdIdentifier(),
//                nsdMock.getVersion(),
//                "deploymentFlavour",
//                "instatiationLevelId");
//
//        /**
//         * VsLcmService
//         */
//        when(vsDescriptorCatalogueService.getVsd("vsdParentId")).thenReturn(vsdMock);
//        when(vsdMock.getTenantId()).thenReturn("tenantId");
//
//        /**
//         * VsLcmManager
//         */
//        when(translatorService.translateVsd(any())).thenReturn(Collections.singletonMap("vsdParentId", nsInstantiationInfo));
//        when(vsDescriptorCatalogueService.findByVsDescriptorId("vsdParentId")).thenReturn(Optional.of(vsdMock));
//
//        /**
//         * Arbitrator (Basic Arbitrator)
//         */
//        when(nfvoCatalogueService.queryNsdAssumingOne(any(Filter.class))).thenReturn(nsdMock);
//        //when(nsdMock.getNestedNsdId()).thenReturn(Collections.singletonList("nfvNsdId")); //returning the existing ID (previously created)
//        when(virtualResourceCalculatorService.computeVirtualResourceUsage(any(NfvNsInstantiationInfo.class))).thenReturn(new VirtualResourceUsage(15, 5, 5));
//        when(adminService.getTenant("tenantId")).thenReturn(tenantMock);
//        when(tenantMock.getActiveSla()).thenReturn(tenantSlaMock);
//        when(tenantSlaMock.getGlobalConstraint()).thenReturn(scMock);
//        when(scMock.getMaxResourceLimit()).thenReturn(new VirtualResourceUsage(50, 50, 50));
//        when(tenantMock.getAllocatedResources()).thenReturn(new VirtualResourceUsage(10, 10, 10)); // Previously allocated
//
//        /**
//         * NsLcmManager
//         */
//        when(nfvoCatalogueService.queryNsd(any())).thenReturn(queryNsdResponseMock);
//        when(queryNsdResponseMock.getQueryResult()).thenReturn(Collections.singletonList(nsdInfoMock));
//        when(nsdInfoMock.getNsdInfoId()).thenReturn("nsdInfoParentId");
//        when(nsdInfoMock.getNsd()).thenReturn(nsdMock);
//        when(nfvoLcmService.createNsIdentifier(any())).thenReturn("nfvNsParentId");
//        //when(nsdMock.getSapd()).thenReturn(Collections.singletonList(sapdMock));
//        //when(sapdMock.getCpdId()).thenReturn("cpdParentId");
//        when(nfvoLcmService.instantiateNs(any())).thenReturn("INSTANTIATE_PARENT_NS");
//
//        /**
//         * INSTANTIATE PARENT VS
//         */
//        vsLcmService.instantiateVs(instantiateVsRequest);
//        Thread.sleep(5000);  // Simulate NFVO response waiting
//
//        /**
//         * After NFV NS_CREATED (parent)
//         * NSLcmManager
//         */
//        when(nfvoLcmService.queryNs(any())).thenReturn(queryNsResponseMock);
//        when(queryNsResponseMock.getQueryNsResult()).thenReturn(Collections.singletonList(nsInfoMock));
//        when(nsInfoMock.getNestedNsInfoId()).thenReturn(Collections.singletonList("partentSoCreatedNs"));
//
//        /**
//         * VsLcmManager
//         */
//        when(virtualResourceCalculatorService.computeVirtualResourceUsage(any(NetworkSliceInstance.class), eq(true))).thenReturn(new VirtualResourceUsage(25,15,15));
//
//        /**
//         * Triggering NFVO NS_CREATED Notification
//         */
//        engine.notifyNfvNsStatusChange("nfvNsParentId", NsStatusChange.NS_CREATED, true);
//        Thread.sleep(5000); //
//
//        /************************************************** TERMINATION ***********************************************/
//
//        /**
//         * TERMINATE VS
//         * termination message
//         */
//        TerminateVsRequest terminateVsRequest = new TerminateVsRequest(
//                "6",
//                "tenantId"
//        );
//
//        /**
//         * NsLcmManager
//         */
//        when(nfvoLcmService.terminateNs(any())).thenReturn("TERMINATE_PARENT_NS");
//
//        /**
//         * TERMINATE VS PARENT
//         */
//        vsLcmService.terminateVs(terminateVsRequest);
//        Thread.sleep(5000);
//
//        /**
//         * Triggering NFVO NS_TERMINATED Notification
//         */
//        engine.notifyNfvNsStatusChange("nfvNsParentId", NsStatusChange.NS_TERMINATED, true);
//        Thread.sleep(5000);
//
//        /****************************************************** PURGE *************************************************/
//
//        /**
//         * Purge VS
//         * purge message
//         */
//        PurgeVsRequest purgeVsRequest= new PurgeVsRequest(
//                "6",
//                "tenantId"
//        );
//
//        /**
//         * Triggering VS PURGE
//         */
//        vsLcmService.purgeVs(purgeVsRequest);
//        Thread.sleep(500);
//
//    }
//
//}
