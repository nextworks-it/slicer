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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * Abstract message exchanged among the engine components.
 * 
 * @author nextworks
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "msgType")
@JsonSubTypes({
	@Type(value = InstantiateNsiRequestMessage.class, 	name = "INSTANTIATE_NSI_REQUEST"),

	@Type(value = TerminateNsiRequestMessage.class, 	name = "TERMINATE_NSI_REQUEST"),
	@Type(value = EngineNotifyNssiStatusChange.class, 	name = "NOTIFY_NSSI_STATUS_CHANGE"),
		@Type(value = NotifyResourceAllocationResponse.class, 	name = "NOTIFY_RESOURCE_ALLOCATION_RESPONSE"),
		@Type(value = EngineUpdateNsiRequest.class, 	name = "UPDATE_NSI_REQUEST"),
})
public abstract class NsmfEngineMessage {

	@JsonProperty("type")
    NsmfEngineMessageType type;

	/**
	 * @return the type
	 */
	public NsmfEngineMessageType getType() {
		return type;
	}
	
	
	
}
