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
package it.nextworks.nfvmano.sebastian.nsmf;

import it.nextworks.nfvmano.catalogue.template.elements.NsTemplateInfo;
import it.nextworks.nfvmano.catalogues.template.services.NsTemplateCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmConsumerInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceFailureNotification;
import it.nextworks.nfvmano.sebastian.record.NsRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for NSMF
 * 
 * @author nextworks
 *
 */
@Service
public class NsmfUtils {

	private static final Logger log = LoggerFactory.getLogger(NsmfUtils.class);

	@Autowired
	private NsTemplateCatalogueService nsTemplateCatalogueService;

	@Autowired
	private NsRecordService nsRecordService;

	private NsmfLcmConsumerInterface notificationDispatcher;

	@Value("${nfvo.catalogue.username}")
	private String nfvoCatalogueUsername;

	@Value("${nfvo.catalogue.type}")
	private String nfvoCatalogueType;

	@Value("${nfvo.lcm.polling}")
	private int nfvoLcmPolling;

	@Value("${plugAndPlay.hostname}")
	private String plugAndPlayHostname;

	@Value("${llmec.hostname}")
	private String llMecHostname;

	@Value("${llmecAdapter.hostname}")
	private String llMecAdapteHostname;

	@Value("#{new Boolean('${simulateLlMec}')}")
	private Boolean simutateLlMec;

	@Value("#{new Boolean('${simulateRan}')}")
	private Boolean simulateRan;

	@Value("#{new Boolean('${fakeNetworkSlice}')}")
	private Boolean fakeNetworkSlice;

	public NsTemplateInfo getNsTemplateInfoFromCatalogue(String nstUuid)
			throws NotExistingEntityException {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("NST_ID", nstUuid);
		Filter filter = new Filter(parameters);
		GeneralizedQueryRequest query = new GeneralizedQueryRequest(filter, null);
		try {
			List<NsTemplateInfo> nstInfos = nsTemplateCatalogueService.queryNsTemplate(query).getNsTemplateInfos();
			if (nstInfos.size() != 1)
				throw new NotExistingEntityException("Unable to find unique NST with UUID " + nstUuid + " in catalogue");
			return nstInfos.get(0);
		} catch (Exception e) {
			log.error("Unable to retrieve Network Slice Template from Catalogue: " + e.getMessage());
			throw new NotExistingEntityException("Unable to retrieve Network Slice Template from Catalogue: " + e.getMessage());
		}
	}

	public void manageNsError(String nsiUuid, String error) {
		log.error(error);
		nsRecordService.setNsFailureInfo(nsiUuid, error);
		notificationDispatcher.notifyNetworkSliceFailure(new NetworkSliceFailureNotification(nsiUuid, error));
	}

	public void setNotificationDispatcher(NsmfLcmConsumerInterface notificationDispatcher) {
		this.notificationDispatcher = notificationDispatcher;
	}

	public boolean isNmroNfvoCatalogueType(){
		return nfvoCatalogueType.equals("NMRO");
	}
	public String getNfvoCatalogueUsername(){
		return nfvoCatalogueUsername;
	}
	public int getNfvoLcmPolling(){ return nfvoLcmPolling; }

	public String getPlugAndPlayHostname() {
		return plugAndPlayHostname;
	}
	public String getLlMecHostname() {	return llMecHostname;	}
	public String getLlMecAdapteHostname() {		return llMecAdapteHostname;	}

	public boolean isLlMecSimulated() {
		return simutateLlMec;
	}

	public boolean isRanSimulated() {
		return simulateRan;
	}

	public Boolean getFakeNetworkSlice() {
		return fakeNetworkSlice;
	}
}
