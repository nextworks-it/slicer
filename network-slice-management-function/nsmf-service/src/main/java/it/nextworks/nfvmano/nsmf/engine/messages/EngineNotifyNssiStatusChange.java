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
package it.nextworks.nfvmano.nsmf.engine.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NssStatusChange;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NssiNotifType;
import it.nextworks.nfvmano.libs.vs.common.nssmf.elements.NssiStatus;

import java.util.UUID;


public class EngineNotifyNssiStatusChange extends NsmfEngineMessage {

	@JsonProperty("nssiId")
	private UUID nssiId;
	
	@JsonProperty("nssiNotifType")
	private NssiNotifType nssiNotifType;

	@JsonProperty("successful")
	private boolean isSuccessful;

	@JsonProperty("nssiStatus")
	private NssiStatus nssiStatus;
	/**
	 * @param nssiId
	 * @param nssiNotifType
	 * @param successful
	 */
	@JsonCreator
	public EngineNotifyNssiStatusChange(@JsonProperty("nssiId") UUID nssiId,
										@JsonProperty("statusChange") NssiNotifType nssiNotifType,
										@JsonProperty("successful") boolean successful,
										@JsonProperty("nssiStatus") NssiStatus nssiStatus) {
		this.type = NsmfEngineMessageType.NOTIFY_NSSI_STATUS_CHANGE;
		this.nssiId = nssiId;
		this.nssiNotifType = nssiNotifType;
		this.isSuccessful = successful;
		this.nssiStatus=nssiStatus;
	}


	public UUID getNssiId() {
		return nssiId;
	}

	/**
	 * @return the statusChange
	 */
	public NssiNotifType getNssiNotifType() {
		return nssiNotifType;
	}

	/**
	 * @return the isSuccessful
	 */
	public boolean isSuccessful() {
		return isSuccessful;
	}

	public NssiStatus getNssiStatus() {
		return nssiStatus;
	}
}
