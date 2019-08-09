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
package it.nextworks.nfvmano.sebastian.engine.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NotifyNfvNsiStatusChange extends EngineMessage {

	@JsonProperty("nfvNsiId")
	private String nfvNsiId;
	
	@JsonProperty("statusChange")
	private NsStatusChange statusChange;

	@JsonProperty("successful")
	private boolean isSuccessful;
	
	/**
	 * @param nsiId
	 * @param statusChange
	 * @param successful
	 */
	@JsonCreator
	public NotifyNfvNsiStatusChange(@JsonProperty("nfvNsiId") String nfvNsiId, 
			@JsonProperty("statusChange") NsStatusChange statusChange,
			@JsonProperty("successful") boolean successful) {
		this.type = EngineMessageType.NOTIFY_NFV_NSI_STATUS_CHANGE;
		this.nfvNsiId = nfvNsiId;
		this.statusChange = statusChange;
		this.isSuccessful = successful;
	}

	/**
	 * @return the nfvNsiId
	 */
	public String getNfvNsiId() {
		return nfvNsiId;
	}

	/**
	 * @return the statusChange
	 */
	public NsStatusChange getStatusChange() {
		return statusChange;
	}

	/**
	 * @return the isSuccessful
	 */
	public boolean isSuccessful() {
		return isSuccessful;
	}
	
	
	
}
