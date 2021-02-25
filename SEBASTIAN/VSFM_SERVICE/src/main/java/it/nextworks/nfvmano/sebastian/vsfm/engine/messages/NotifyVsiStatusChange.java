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
package it.nextworks.nfvmano.sebastian.vsfm.engine.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceStatusChange;
import it.nextworks.nfvmano.sebastian.vsfm.messages.VerticalServiceStatusChange;


public class NotifyVsiStatusChange extends VsmfEngineMessage {

	@JsonProperty("vsiId")
	private String vsiId;

	@JsonProperty("statusChange")
	private VerticalServiceStatusChange statusChange;



	/**
	 * @param vsiId
	 * @param statusChange
	 */
	@JsonCreator
	public NotifyVsiStatusChange(@JsonProperty("vsiId") String vsiId,
                                 @JsonProperty("statusChange") VerticalServiceStatusChange statusChange) {
		this.type = VsmfEngineMessageType.NOTIFY_VSI_STATUS_CHANGE;
		this.vsiId = vsiId;
		this.statusChange = statusChange;
	}

	/**
	 * @return the vsiId
	 */
	public String getVsiId() {
		return vsiId;
	}

	/**
	 * @return the statusChange
	 */
	public VerticalServiceStatusChange getStatusChange() {
		return statusChange;
	}
	
	
	
}
