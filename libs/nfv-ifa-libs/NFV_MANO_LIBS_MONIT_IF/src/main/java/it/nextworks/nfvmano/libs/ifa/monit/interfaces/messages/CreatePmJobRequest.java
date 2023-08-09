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
package it.nextworks.nfvmano.libs.ifa.monit.interfaces.messages;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.monit.interfaces.elements.ObjectSelection;


/**
 * Request to create a PM job
 * 
 * REF IFA 013 v2.3.1 - 7.5.2
 * REF IFA 007 v2.3.1 - 7.4.2
 * 
 * @author nextworks
 *
 */
public class CreatePmJobRequest implements InterfaceMessage {
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private ObjectSelection nsSelector;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private ObjectSelection resourceSelector;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private ObjectSelection vnfSelector;
	
	private List<String> performanceMetric = new ArrayList<>();
	private List<String> performanceMetricGroup = new ArrayList<>();
	private int collectionPeriod;
	private int reportingPeriod;
	private String reportingBoundary;
	
	public CreatePmJobRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param nsSelector Defines the NSs for which performance information is to be collected
	 * @param resourceSelector Defines the resources for which performance information is requested to be collected.
	 * @param vnfSelector Defines the VNFs for which performance information is requested to be collected.
	 * @param performanceMetric the type of performance metric(s) for the specified resources.
	 * @param performanceMetricGroup Group of performance metrics. A metric group is a pre-defined list of metrics, known to the producer that  it can decompose to individual metrics.
	 * @param collectionPeriod Specifies the periodicity at which the VIM will collect performance information
	 * @param reportingPeriod Specifies the periodicity at which the VIM will report to the NFVO about performance information
	 * @param reportingBoundary Identifies a boundary after which the reporting will stop. The boundary shall allow a single reporting as well as periodic reporting up to the boundary.
	 * 	 */
	public CreatePmJobRequest(ObjectSelection nsSelector,
			ObjectSelection resourceSelector,
			ObjectSelection vnfSelector,
			List<String> performanceMetric,
			List<String> performanceMetricGroup,
			int collectionPeriod,
			int reportingPeriod,
			String reportingBoundary) {
		this.nsSelector = nsSelector;
		this.resourceSelector = resourceSelector;
		this.vnfSelector = vnfSelector;
		if (performanceMetric != null) this.performanceMetric = performanceMetric;
		if (performanceMetricGroup != null) this.performanceMetricGroup = performanceMetricGroup;
		this.collectionPeriod = collectionPeriod;
		this.reportingPeriod = reportingPeriod;
		this.reportingBoundary = reportingBoundary;
	}
	
	

	/**
	 * @return the nsSelector
	 */
	public ObjectSelection getNsSelector() {
		return nsSelector;
	}

	/**
	 * @return the resourceSelector
	 */
	public ObjectSelection getResourceSelector() {
		return resourceSelector;
	}

	
	
	/**
	 * @return the vnfSelector
	 */
	public ObjectSelection getVnfSelector() {
		return vnfSelector;
	}

	/**
	 * @return the performanceMetric
	 */
	public List<String> getPerformanceMetric() {
		return performanceMetric;
	}

	/**
	 * @return the performanceMetricGroup
	 */
	public List<String> getPerformanceMetricGroup() {
		return performanceMetricGroup;
	}

	/**
	 * @return the collectionPeriod
	 */
	public int getCollectionPeriod() {
		return collectionPeriod;
	}

	/**
	 * @return the reportingPeriod
	 */
	public int getReportingPeriod() {
		return reportingPeriod;
	}

	/**
	 * @return the reportingBoundary
	 */
	public String getReportingBoundary() {
		return reportingBoundary;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if ((resourceSelector == null) && (nsSelector == null) && (vnfSelector == null)) throw new MalformattedElementException("Create PM job request without object selector");
		else {
			if (resourceSelector != null) resourceSelector.isValid();
			if (nsSelector != null) nsSelector.isValid();
			if (vnfSelector != null) vnfSelector.isValid();
		}

	}

}
