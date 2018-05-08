package it.nextworks.nfvmano.sebastian.engine.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TerminateNsiRequestMessage extends EngineMessage {

	@JsonProperty("nsiId")
	private String nsiId;

	
	
	/**
	 * @param nsiId
	 */
	@JsonCreator
	public TerminateNsiRequestMessage(@JsonProperty("nsiId") String nsiId) {
		this.type = EngineMessageType.TERMINATE_NSI_REQUEST;
		this.nsiId = nsiId;
	}



	/**
	 * @return the nsiId
	 */
	public String getNsiId() {
		return nsiId;
	}
	
	
	
}
