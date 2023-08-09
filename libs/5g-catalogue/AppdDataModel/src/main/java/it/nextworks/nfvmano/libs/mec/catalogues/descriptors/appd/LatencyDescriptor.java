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
package it.nextworks.nfvmano.libs.mec.catalogues.descriptors.appd;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

/**
 * The LatencyDescriptor data type describes latency requirements for a ME application.
 * NOTE: The meaning of latency will be decided in stage 3 specification.
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.2.1.14
 * 
 * @author nextworks
 *
 */
@Embeddable
public class LatencyDescriptor implements DescriptorInformationElement {

	private int timeUnit;
	private int latency;
	
	public LatencyDescriptor() {
		// JPA only
	}
	
	/**
	 * Constructor
	 *  
	 * @param timeUnit Time unit, e.g. ms
	 * @param latency The value of the latency
	 */
	public LatencyDescriptor(int timeUnit,
			int latency) {
		this.timeUnit = timeUnit;
		this.latency = latency;
	}
	
	
	
	/**
	 * @return the timeUnit
	 */
	@JsonProperty("timeUnit")
	public int getTimeUnit() {
		return timeUnit;
	}

	/**
	 * @return the latency
	 */
	@JsonProperty("latency")
	public int getLatency() {
		return latency;
	}

	@Override
	public void isValid() throws MalformattedElementException { }

}
