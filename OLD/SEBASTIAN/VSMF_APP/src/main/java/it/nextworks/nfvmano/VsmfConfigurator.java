/*
 * Copyright (c) 2019 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano;

import javax.annotation.PostConstruct;

import it.nextworks.nfvmano.sebastian.vsfm.sbi.NsmfLcmOperationPollingManager;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.NsmfType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.sebastian.vsfm.VsLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.VsmfUtils;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.NsmfInteractionHandler;

/**
 * This class is used to link the components handling the
 * interfaces towards the lower layers (e.g. NSMF)
 *
 * @author nextworks
 */
@Service
public class VsmfConfigurator {

    @Autowired
    private VsLcmService vsLcmService;

    @Autowired
    private VsmfUtils vsmfUtils;

    @Autowired
    private NsmfInteractionHandler nsmfInteractionHandler;

    @Autowired
    private NsmfLcmOperationPollingManager nsmfLcmOperationPollingManager;

    @Value("${nsmf.lcm.type}")
    private NsmfType nsmfType;

    @Value("${nsmf.restnbi.url}")
    private String nsmfRestServerNbiUrl;

    @Value("${nsmf.lcm.osm.username}")
    private String osmUsername;

    @Value("${nsmf.lcm.osm.password}")
    private String osmPassword;

    @Value("${nsmf.lcm.osm.project}")
    private String osmProject;

    @Value("${nsmf.lcm.osm.vim.account}")
    private String osmVimAccount;

    @PostConstruct
    public void configComService() {
        nsmfInteractionHandler.init();
        nsmfInteractionHandler.setNsmfClientConfiguration(nsmfType, nsmfRestServerNbiUrl, osmUsername, osmPassword, osmProject, osmVimAccount);
        //in the stand-alone VSMF version the NSMF Interaction Handler is used to mediate the interaction with an external NSMF
        vsLcmService.setNsmfLcmProvider(nsmfInteractionHandler);
        vsmfUtils.setNsmfLcmProvider(nsmfInteractionHandler);
        nsmfLcmOperationPollingManager.setNsmfLcmProvider(nsmfInteractionHandler);
    }


}
