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
 * This information element defines attributes that affect 
 * the invocation of the OperateVnf operation.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.5.7
 * 
 * 
 * @author nextworks
 *
 */
@Embeddable
public class TerminateAppOpConfig implements DescriptorInformationElement {

	private String minGracefulStopTimeout;
	private String maxRecommendedGracefulStopTimeout;
	
	public TerminateAppOpConfig() { }

	/**
	 * Constructor
	 * 
	 * @param minGracefulStopTimeout Minimum timeout value for graceful stop of a VNF instance.
	 * @param maxRecommendedGracefulStopTimeout Maximum recommended timeout value that can be needed to gracefully stop a VNF instance
	 */
	public TerminateAppOpConfig(String minGracefulStopTimeout,
								String maxRecommendedGracefulStopTimeout) {
		this.maxRecommendedGracefulStopTimeout = maxRecommendedGracefulStopTimeout;
		this.minGracefulStopTimeout = minGracefulStopTimeout;
	}
	
	/**
	 * @return the minGracefulStopTimeout
	 */
	@JsonProperty("minGracefulStopTimeout")
	public String getMinGracefulStopTimeout() {
		return minGracefulStopTimeout;
	}

	/**
	 * @return the maxRecommendedGracefulStopTimeout
	 */
	@JsonProperty("maxRecommendedGracefulStopTimeout")
	public String getMaxRecommendedGracefulStopTimeout() {
		return maxRecommendedGracefulStopTimeout;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (minGracefulStopTimeout == null) throw new MalformattedElementException("Operate VNF config data without minimum graceful stop timeout");
	}

}
