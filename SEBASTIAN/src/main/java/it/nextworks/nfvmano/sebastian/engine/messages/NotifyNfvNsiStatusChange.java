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
