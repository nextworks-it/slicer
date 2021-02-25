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

import it.nextworks.nfvmano.catalogues.template.services.NsTemplateCatalogueService;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.sebastian.arbitrator.interfaces.ArbitratorInterface;
import it.nextworks.nfvmano.sebastian.common.VirtualResourceCalculatorService;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.catalogue.translator.TranslatorService;


/**
 * This is the abstract class for the arbitrator.
 * It must be extended through the specific arbitrators implementing the specific algorithms.
 *
 * @author nextworks
 */
public abstract class AbstractArbitrator implements ArbitratorInterface {

    AdminService adminService;

    VsRecordService vsRecordService;

    VsDescriptorCatalogueService vsDescriptorCatalogueService;

    VirtualResourceCalculatorService virtualResourceCalculatorService;

    TranslatorService translatorService;

    NfvoCatalogueService nfvoCatalogueService;

    NsTemplateCatalogueService nsTemplateCatalogueService;

    NsmfLcmProviderInterface nsmfLcmProvider;

    ArbitratorType type;

    public AbstractArbitrator(AdminService adminService,
                              VsRecordService vsRecordService,
                              VsDescriptorCatalogueService vsDescriptorCatalogueService,
                              TranslatorService translatorService,
                              NfvoCatalogueService nfvoCatalogueService,
                              NsTemplateCatalogueService nsTemplateCatalogueService,
                              ArbitratorType type,
                              VirtualResourceCalculatorService virtualResourceCalculatorService,
                              NsmfLcmProviderInterface nsmfLcmProvider) {
        this.adminService = adminService;
        this.vsRecordService = vsRecordService;
        this.vsDescriptorCatalogueService = vsDescriptorCatalogueService;
        this.translatorService = translatorService;
        this.nfvoCatalogueService = nfvoCatalogueService;
        this.nsTemplateCatalogueService = nsTemplateCatalogueService;
        this.type = type;
        this.virtualResourceCalculatorService = virtualResourceCalculatorService;
        this.nsmfLcmProvider = nsmfLcmProvider;
    }

    public AbstractArbitrator(){

    }

    public VsDescriptorCatalogueService getVsDescriptorCatalogueService() {
        return vsDescriptorCatalogueService;
    }

    public void setNsmfLcmProvider(NsmfLcmProviderInterface nsmfLcmProvider) {
        this.nsmfLcmProvider = nsmfLcmProvider;
    }

    public VirtualResourceCalculatorService getVirtualResourceCalculatorService() {
        return virtualResourceCalculatorService;
    }

    public NfvoCatalogueService getNfvoCatalogueService() {
        return nfvoCatalogueService;
    }

    public NsmfLcmProviderInterface getNsmfLcmProvider() {
        return nsmfLcmProvider;
    }

    /**
     * @return the adminService
     */
    public AdminService getAdminService() {
        return adminService;
    }

    /**
     * @return the vsRecordService
     */
    public VsRecordService getVsRecordService() {
        return vsRecordService;
    }

    /**
     * @return the translatorService
     */
    public TranslatorService getTranslatorService() {
        return translatorService;
    }

    /**
     * @return the type
     */
    public ArbitratorType getType() {
        return type;
    }

}
