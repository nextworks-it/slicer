package it.nextworks.nfvmano.sebastian.engine.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NotifyNsiStatusChange extends EngineMessage {

	@JsonProperty("nsiId")
	private String nsiId;
	
	@JsonProperty("statusChange")
	private NsStatusChange statusChange;

	
	
	/**
	 * @param nsiId
	 * @param statusChange
	 */
	@JsonCreator
	public NotifyNsiStatusChange(@JsonProperty("nsiId") String nsiId, 
			@JsonProperty("statusChange") NsStatusChange statusChange) {
		super();
		this.nsiId = nsiId;
		this.statusChange = statusChange;
	}

	/**
	 * @return the nsiId
	 */
	public String getNsiId() {
		return nsiId;
	}

	/**
	 * @return the statusChange
	 */
	public NsStatusChange getStatusChange() {
		return statusChange;
	}
	
	
	
}
