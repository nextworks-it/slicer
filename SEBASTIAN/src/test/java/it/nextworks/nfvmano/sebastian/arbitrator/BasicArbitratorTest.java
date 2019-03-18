package it.nextworks.nfvmano.sebastian.arbitrator;

import it.nextworks.nfvmano.libs.catalogues.interfaces.elements.NsdInfo;
import it.nextworks.nfvmano.libs.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.admin.elements.Sla;
import it.nextworks.nfvmano.sebastian.admin.elements.SlaVirtualResourceConstraint;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.admin.elements.VirtualResourceUsage;
import it.nextworks.nfvmano.sebastian.engine.Engine;
import it.nextworks.nfvmano.sebastian.nfvodriver.NfvoService;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.translator.NfvNsInstantiationInfo;
import it.nextworks.nfvmano.sebastian.translator.TranslatorService;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BasicArbitratorTest {

    NfvoService nfvoMock;
    VsRecordService vsRecordServiceMock;
    Engine engineMock;
    NsdInfo nsdInfoMock;
    AdminService adminServiceMock;
    TranslatorService translatorServiceMock;
    ArbitratorRequest arbitratorRequestMock;
    Nsd nsdMock;
    NetworkSliceInstance networkSliceInstanceMock;
    Tenant tenantMock;
    Sla tenantSlaMock;
    SlaVirtualResourceConstraint scMock;

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

    }

    @Test
    public void testComputeArbitratorSolution() throws Exception{
        NfvNsInstantiationInfo nsInstantiationInfo =
                new NfvNsInstantiationInfo("nfvID", "nsdVersion", "deploymentFlavour", "instatiationLevelId");

        Map<String, NfvNsInstantiationInfo> nsInitInfos = new HashMap<>();
        nsInitInfos.put("string", nsInstantiationInfo);

        List<NetworkSliceInstance> nsis = new ArrayList<>();
        nsis.add(this.networkSliceInstanceMock);




        BasicArbitrator basicArbitrator = new BasicArbitrator(this.adminServiceMock,
                this.vsRecordServiceMock,
                this.translatorServiceMock,
                this.nfvoMock);

        List<ArbitratorRequest> request = new ArrayList<>();
        request.add(this.arbitratorRequestMock);

        when(arbitratorRequestMock.getTenantId()).thenReturn("banana");
        when(arbitratorRequestMock.getInstantiationNsd()).thenReturn(nsInitInfos);
        when(nfvoMock.queryNsdAssumingOne(any(), any())).thenReturn(this.nsdMock);
        when(nsdMock.getNestedNsdId()).thenReturn(Collections.singletonList("nestedID"));
        when(networkSliceInstanceMock.getNsiId()).thenReturn("nestedNsi");
        when(vsRecordServiceMock.getUsableSlices(any(), any(), any(), any(), any())).thenReturn(nsis);
        when(nfvoMock.computeVirtualResourceUsage(any(NfvNsInstantiationInfo.class))).thenReturn(new VirtualResourceUsage());
        when(adminServiceMock.getTenant("banana")).thenReturn(tenantMock);
        when(tenantMock.getActiveSla()).thenReturn(tenantSlaMock);
        when(tenantSlaMock.getGlobalConstraint()).thenReturn(scMock);
        when(scMock.getMaxResourceLimit()).thenReturn(new VirtualResourceUsage());
        when(tenantMock.getAllocatedResources()).thenReturn(new VirtualResourceUsage());



        List<ArbitratorResponse> responses = basicArbitrator.computeArbitratorSolution(request);
        System.out.println(responses.get(0).isNewSliceRequired());
        System.out.println(responses.get(0).getExistingSliceSubnets().get("nestedNsi"));

    }

}
