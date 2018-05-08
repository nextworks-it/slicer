package it.nextworks.nfvmano.sebastian.engine.messages;

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
	@Type(value = InstantiateNsiRequestMessage.class, 	name = "INSTANTIATE_NSI_REQUEST"),
	@Type(value = TerminateNsiRequestMessage.class, 	name = "TERMINATE_NSI_REQUEST"),
	@Type(value = NotifyNsiStatusChange.class, 	name = "NOTIFY_NSI_STATUS_CHANGE"),
})
public abstract class EngineMessage {

	@JsonProperty("type")
	EngineMessageType type;

	/**
	 * @return the type
	 */
	public EngineMessageType getType() {
		return type;
	}
	
	
	
}
