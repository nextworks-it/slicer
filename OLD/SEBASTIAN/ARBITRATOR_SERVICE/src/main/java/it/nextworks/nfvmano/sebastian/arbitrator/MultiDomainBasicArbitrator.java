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
package it.nextworks.nfvmano.sebastian.arbitrator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import it.nextworks.nfvmano.catalogue.blueprint.BlueprintCatalogueUtilities;
import it.nextworks.nfvmano.catalogue.blueprint.elements.ServiceConstraints;
import it.nextworks.nfvmano.catalogue.blueprint.elements.ServicePriorityLevel;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsDescriptor;
import it.nextworks.nfvmano.catalogue.translator.NfvNsInstantiationInfo;
import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import it.nextworks.nfvmano.sebastian.admin.elements.Sla;
import it.nextworks.nfvmano.sebastian.admin.elements.SlaVirtualResourceConstraint;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.admin.elements.VirtualResourceUsage;
import it.nextworks.nfvmano.sebastian.common.VsAction;
import it.nextworks.nfvmano.sebastian.common.VsActionType;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.catalogue.translator.TranslatorService;
import it.nextworks.nfvmano.catalogues.template.services.NsTemplateCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorResponse;
import it.nextworks.nfvmano.sebastian.common.VirtualResourceCalculatorService;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;

/**
 * Basic arbitrator for multi-domain scenarios with 1:N relationships between CSMF and NSMFs.
 * This arbitrator does not support network slice sharing and assumes that new end-to-end network
 * slices can and must be always created (i.e. enough resources are always available without
 * checking the SLA of the user).
 *
 * @author nextworks
 */
public class MultiDomainBasicArbitrator extends AbstractArbitrator {

    private static final Logger log = LoggerFactory.getLogger(MultiDomainBasicArbitrator.class);

    /**
     * Constructor
     *
     * @param adminService                 Administration service to retrieve SLA information for tenants - not used in this arbitrator
     * @param vsRecordService              Service to access the local Vertical Service Record
     * @param vsDescriptorCatalogueService Service to access the Vertical Service Descriptor Catalog
     * @param translatorService            Service to access the translator
     * @param nfvoService                  Service to access the NFVO catalog for NSDs
     * @param nsTemplateCatalogueService   Service to access the Network Slice Template catalog
     * @param vsc                          Support service to compute the allocation of virtual resources
     * @param nsmfLcmProvider              Service to access the NSMFs
     */
    public MultiDomainBasicArbitrator(AdminService adminService,
                                      VsRecordService vsRecordService,
                                      VsDescriptorCatalogueService vsDescriptorCatalogueService,
                                      TranslatorService translatorService,
                                      NfvoCatalogueService nfvoService,
                                      NsTemplateCatalogueService nsTemplateCatalogueService,
                                      VirtualResourceCalculatorService vsc,
                                      NsmfLcmProviderInterface nsmfLcmProvider) {
        super(adminService,
                vsRecordService,
                vsDescriptorCatalogueService,
                translatorService,
                nfvoService,
                nsTemplateCatalogueService,
                ArbitratorType.MULTI_DOMAIN_BASIC_ARBITRATOR,
                vsc,
                nsmfLcmProvider);
    }

    @Override
    public List<ArbitratorResponse> computeArbitratorSolution(List<ArbitratorRequest> requests)
            throws FailedOperationException, NotExistingEntityException {
        log.debug("Received request at the arbitrator.");

        //TODO: At the moment we process only the first request and the first ns init info
        ArbitratorRequest req = requests.get(0);
        String tenantId = req.getTenantId();
        NfvNsInstantiationInfo nsInitInfo = null;
        Map<String, VsAction> impactedVerticalServiceInstances = new HashMap<>();
        Map<String, Boolean> existingNsiIds = new HashMap<>();
        Map<String, NfvNsInstantiationInfo> nsInitInfos = req.getInstantiationNsd();
        for (Map.Entry<String, NfvNsInstantiationInfo> e : nsInitInfos.entrySet()) {
            nsInitInfo = e.getValue();
        }

        log.debug("The request is for tenant " + tenantId + " and for NST " + nsInitInfo.getNstId());

        try {
            Map<String, String> nsstDomain = nsInitInfo.getNsstDomain();
            VirtualResourceUsage requiredRes = new VirtualResourceUsage(0, 0, 0);
            Nsd nsd;

            // TODO: in 5Growth a single NST with a number of nsst will be onboarded along with the VSB, then the arbitration will be performed among the included nssts
            if (!nsstDomain.isEmpty()) {
                String nsstId;
                String nsst_nfvNsdId;
                String nsst_nsdVersion;
                NfvNsInstantiationInfo instantiationInfo;
                NST nsst;
                for (Map.Entry<String, String> nsstEntry : nsstDomain.entrySet()) {
                    nsstId = nsstEntry.getKey();
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("NST_ID", nsstId);
                    Filter nsstFilter = new Filter(parameters);
                    GeneralizedQueryRequest nsstRequest = new GeneralizedQueryRequest(nsstFilter, null);
                    nsst = nsTemplateCatalogueService.queryNsTemplate(nsstRequest).getNsTemplateInfos().get(0).getNST();
                    nsst_nfvNsdId = nsst.getNsdId();
                    nsst_nsdVersion = nsst.getNsdVersion();

                    // TODO: for the time being in 5Growth nested NSD are not considered
                    // TODO: we are assuming only one InstantiationLevel and only one DeploymentFlavour
                    /*nsd = nfvoCatalogueService.queryNsdAssumingOne(BlueprintCatalogueUtilities.buildNsdFilter(nsst_nfvNsdId, nsst_nsdVersion));
                    String nsst_nsdInstantiationLevel = nsd.getNsDf().get(0).getDefaultNsInstantiationLevelId();
                    String nsst_nsdDeploymentFlavour = nsd.getNsDf().get(0).getNsDfId();*/


                    instantiationInfo = new NfvNsInstantiationInfo();
                    instantiationInfo.setNfvNsdId(nsst_nfvNsdId);
                    instantiationInfo.setNsdVersion(nsst_nsdVersion);
                    /*instantiationInfo.setInstantiationLevelId(nsst_nsdInstantiationLevel);
                    instantiationInfo.setDeploymentFlavourId(nsst_nsdDeploymentFlavour);
                    VirtualResourceUsage tempRes = virtualResourceCalculatorService.computeVirtualResourceUsage(instantiationInfo);
                    log.debug("The amount of required resources for the service " + nsst_nfvNsdId + " is the following: " + tempRes.toString());

                    // TODO: arbitrate NST request, to be understood how requested resources have to be considered (cumulative?)
                    requiredRes.addResources(tempRes);*/
                }
            } /*else {
                //TODO: handle the case of an end-to-end NSD modelling (with nested NSDs)
                if (nsInitInfo.getNfvNsdId() != null) {
                    String nfvNsdId = nsInitInfo.getNfvNsdId();
                    String nsdVersion = nsInitInfo.getNsdVersion();
                    nsd = nfvoCatalogueService.queryNsdAssumingOne(BlueprintCatalogueUtilities.buildNsdFilter(nfvNsdId, nsdVersion));
                    List<String> nestedNsdIds = nsd.getNestedNsdId();

                    if (!nestedNsdIds.isEmpty()) {

                        //Retrieve <DF, IL> from nsInitInfo
                        String instantiationLevelId = nsInitInfo.getInstantiationLevelId();
                        String deploymentFlavourID = nsInitInfo.getDeploymentFlavourId();
                        //Create NSIid sublist
                        existingNsiIds = new HashMap<>();
                        for (String nestedNsdId : nestedNsdIds) {
                            //Check existing NSI per id, tenant, IL, DF
                            List<NetworkSliceInstance> nsis = getUsableSlices(tenantId, nestedNsdId, nsdVersion, deploymentFlavourID, instantiationLevelId);

                            for (NetworkSliceInstance nsi : nsis) {
                                existingNsiIds.put(nsi.getNsiId(), false);
                                log.debug("Existing NSI found found: {}", nsi.getNsiId());
                            }
                        }
                    }

                    requiredRes = virtualResourceCalculatorService.computeVirtualResourceUsage(nsInitInfo);
                } else {
                    throw new FailedOperationException("Invalid computation request: no associated NSD/NSSTs for computing resources");
                }
            }*/

            /*log.debug("The total amount of required resources for the service is the following: " + requiredRes.toString());

            log.debug("Reading info about active SLA and used resources for the given tenant.");

            Tenant tenant = adminService.getTenant(tenantId);
            Sla tenantSla = tenant.getActiveSla();
            //TODO: At the moment we are considering only the SLA about global resource usage. MEC versus cloud still to be managed.
            SlaVirtualResourceConstraint sc = tenantSla.getGlobalConstraint();
            VirtualResourceUsage maxRes = sc.getMaxResourceLimit();
            log.debug("The maximum amount of global virtual resources allowed for the tenant is the following: " + maxRes.toString());

            VirtualResourceUsage usedRes = tenant.getAllocatedResources();
            log.debug("The current resource usage for the tenant is the following: " + usedRes.toString());*/

            boolean acceptableRequest = true;
            /*if ((requiredRes.getDiskStorage() + usedRes.getDiskStorage()) > maxRes.getDiskStorage())
                acceptableRequest = false;
            if ((requiredRes.getMemoryRAM() + usedRes.getMemoryRAM()) > maxRes.getMemoryRAM())
                acceptableRequest = false;
            if ((requiredRes.getvCPU() + usedRes.getvCPU()) > maxRes.getvCPU()) acceptableRequest = false;*/

            // TODO: At the moment we are not considering other services belonging to the same tenant
            //if (!acceptableRequest) impactedVerticalServiceInstances = generateImpactedVsList(tenantId);

            ArbitratorResponse response = new ArbitratorResponse(requests.get(0).getRequestId(),
                    acceptableRequest,                    //acceptableRequest
                    true,                                //newSliceRequired,
                    null,                                //existingCompositeSlice,
                    false,                                //existingCompositeSliceToUpdate,
                    existingNsiIds,
                    impactedVerticalServiceInstances);
            List<ArbitratorResponse> responses = new ArrayList<>();
            responses.add(response);
            return responses;
        } catch (NotExistingEntityException e) {
            log.error("Info not found from NFVO or DB: " + e.getMessage());
            throw new NotExistingEntityException("Error retrieving info at the arbitrator: " + e.getMessage());
        } catch (Exception e) {
            log.error("Failure at the arbitrator: " + e.getMessage());
            throw new FailedOperationException(e.getMessage());
        }
    }

    @Override
    public List<ArbitratorResponse> arbitrateVsScaling(List<ArbitratorRequest> requests)
            throws FailedOperationException, NotExistingEntityException {
        log.debug("Received VS Scaling request at the arbitrator.");
        throw new FailedOperationException("Vs Scaling arbitration not yet supported");
    }

    private List<NetworkSliceInstance> getUsableSlices(String tenantId, String nestedNsdId,
                                                       String nsdVersion, String deploymentFlavourID, String instantiationLevelId) {
        //TODO: find a better way to query this. Maybe with ad hoc filter supported on NSMF side.
        List<NetworkSliceInstance> target = new ArrayList<NetworkSliceInstance>();
        log.debug("Interacting with NSMF service to get information about all network slices");
        GeneralizedQueryRequest request = new GeneralizedQueryRequest(new Filter(new HashMap<String, String>()),
                new ArrayList<String>());
        try {
            List<NetworkSliceInstance> nsis = nsmfLcmProvider.queryNetworkSliceInstance(request, null, tenantId);
            for (NetworkSliceInstance nsi : nsis) {
                if ((nsi.getNsdId().equals(nestedNsdId)) &&
                        (nsi.getNsdVersion().equals(nsdVersion)) &&
                        (nsi.getDfId().equals(deploymentFlavourID)) &&
                        (nsi.getInstantiationLevelId().contentEquals(instantiationLevelId))) {
                    log.debug("Found usable network slice " + nsi.getNsiId());
                    target.add(nsi);
                }
            }
        } catch (Exception e) {
            log.debug("Error while getting network slice instances. Returning empty array");
        }
        return target;
    }
}
