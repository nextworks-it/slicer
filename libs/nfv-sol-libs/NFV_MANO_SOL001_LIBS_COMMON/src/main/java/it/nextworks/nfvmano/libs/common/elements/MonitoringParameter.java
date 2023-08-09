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
package it.nextworks.nfvmano.libs.common.elements;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

/**
 * The MonitoringParameter information specifies a virtualised resource related
 * performance metric to be monitored. Ref. IFA 014 v2.3.1 - 6.2.8
 * 
 * @author nextworks
 *
 */
@Embeddable
public class MonitoringParameter implements DescriptorInformationElement {

	private String monitoringParameterId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String name;

	private String performanceMetric;

	public MonitoringParameter() {
		// JPA only
	}

	/**
	 * Constructor
	 * 
	 * @param monitoringParameterId Unique identifier of this monitoring parameter
	 *                              information element.
	 * @param name                  Human readable name of the monitoring parameter.
	 * @param performanceMetric     Defines the virtualised resource-related
	 *                              performance metric.
	 */
	public MonitoringParameter(String monitoringParameterId, String name, String performanceMetric) {
		this.monitoringParameterId = monitoringParameterId;
		this.name = name;
		this.performanceMetric = performanceMetric;
	}

	/**
	 * @return the monitoringParameterId
	 */
	@JsonProperty("monitoringParamaterId")
	public String getMonitoringParameterId() {
		return monitoringParameterId;
	}

	/**
	 * @return the name
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	 * @return the performanceMetric
	 */
	@JsonProperty("performanceMetric")
	public String getPerformanceMetric() {
		return performanceMetric;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.monitoringParameterId == null)
			throw new MalformattedElementException("Monitoring Parameter without ID");
		if (this.performanceMetric == null)
			throw new MalformattedElementException("Monitoring Parameter without metric");
	}
}
