package it.nextworks.nfvmano.sebastian.engine.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.sebastian.vsnbi.messages.InstantiateVsRequest;

public class InstantiateVsiRequestMessage extends EngineMessage {

	@JsonProperty("vsiId")
	private String vsiId;
	
	@JsonProperty("request")
	private InstantiateVsRequest request;

	/**
	 * Constructor
	 * 
	 * @param vsiId ID of the VS to be instantiated
	 * @param request VSI instantiation request
	 */
	@JsonCreator
	public InstantiateVsiRequestMessage(@JsonProperty("vsiId") String vsiId, 
			@JsonProperty("request") InstantiateVsRequest request) {
		this.type = EngineMessageType.INSTANTIATE_VSI_REQUEST;
		this.vsiId = vsiId;
		this.request = request;
	}

	/**
	 * @return the vsiId
	 */
	public String getVsiId() {
		return vsiId;
	}

	/**
	 * @return the request
	 */
	public InstantiateVsRequest getRequest() {
		return request;
	}
	
	
	
}
