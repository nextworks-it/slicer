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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

/**
 * Abstract message exchanged among the engine components.
 * 
 * @author nextworks
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "msgType")
@JsonSubTypes({
	@Type(value = InstantiateVsiRequestMessage.class, 	name = "INSTANTIATE_VSI_REQUEST"),
	@Type(value = TerminateVsiRequestMessage.class, 	name = "TERMINATE_VSI_REQUEST"),
	@Type(value = ModifyVsiRequestMessage.class, name = "MODIFY_VSI_REQUEST"),
	@Type(value = TerminateVsiRequestMessage.class, 	name = "TERMINATE_VSI_REQUEST"),
	@Type(value = NotifyNsiStatusChange.class, 	name = "NOTIFY_NSI_STATUS_CHANGE"),
	@Type(value = CoordinateVsiRequest.class, 	name = "COORDINATE_VSI_REQUEST"),
	@Type(value = VsiTerminationNotificationMessage.class, 	name = "NOTIFY_TERMINATION"),
	@Type(value = NotifyResourceGranted.class, 	name = "RESOURCES_GRANTED"),
})
public abstract class VsmfEngineMessage {

	@JsonProperty("type")
	VsmfEngineMessageType type;

	/**
	 * @return the type
	 */
	public VsmfEngineMessageType getType() {
		return type;
	}
	
	
	
}
