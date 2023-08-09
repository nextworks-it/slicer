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
package it.nextworks.nfvmano.libs.ifa.common.elements;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashMap;
import java.util.Map;

/**
 * The MonitoringParameter information specifies a virtualised 
 * resource related performance metric to be monitored.
 * Ref. IFA 014 v2.3.1 - 6.2.8
 * 
 * @author nextworks
 *
 */
@Entity
public class MonitoringParameter implements DescriptorInformationElement {


	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;

	private String monitoringParameterId;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String name;


	private String performanceMetric;


	//OUT OF THE STANDARD: 5growth
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String exporter;


	//OUT OF THE STANDARD: 5growth
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, String> params= new HashMap<>();

	//OUT OF THE STANDARD: 5growth
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String type;




	public MonitoringParameter() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param monitoringParameterId Unique identifier of this monitoring parameter information element.
	 * @param name Human readable name of the monitoring parameter.
	 * @param performanceMetric Defines the virtualised resource-related performance metric.
	 * @param params exporter parameter
	 */
	public MonitoringParameter(String monitoringParameterId,
			String name,
			String performanceMetric,
							   String type,
							   Map<String, String> params,
							   String exporter) {
		this.monitoringParameterId = monitoringParameterId;
		this.name = name;
		this.performanceMetric = performanceMetric;
		if(params!=null) this.params=params;
		this.exporter=exporter;
		this.type= type;
	}


	public String getExporter() {
		return exporter;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public String getType() {
		return type;
	}

	/**
	 * @return the monitoringParameterId
	 */
	@JsonProperty("monitoringParameterId")
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
		if (this.monitoringParameterId == null) throw new MalformattedElementException("Monitoring Parameter without ID");
		if (this.performanceMetric == null) throw new MalformattedElementException("Monitoring Parameter without metric");
	}

	public void setMonitoringParameterId(String monitoringParameterId) {
		this.monitoringParameterId = monitoringParameterId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPerformanceMetric(String performanceMetric) {
		this.performanceMetric = performanceMetric;
	}
}
