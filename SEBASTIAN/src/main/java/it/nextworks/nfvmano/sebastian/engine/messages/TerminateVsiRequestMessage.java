package it.nextworks.nfvmano.sebastian.engine.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TerminateVsiRequestMessage extends EngineMessage {

	@JsonProperty("vsiId")
	private String vsiId;
	
	@JsonCreator
	public TerminateVsiRequestMessage(@JsonProperty("vsiId") String vsiId) {
		this.type = EngineMessageType.TERMINATE_VSI_REQUEST;
		this.vsiId = vsiId;
	}

	/**
	 * @return the vsiId
	 */
	public String getVsiId() {
		return vsiId;
	}
	
	
	
}
