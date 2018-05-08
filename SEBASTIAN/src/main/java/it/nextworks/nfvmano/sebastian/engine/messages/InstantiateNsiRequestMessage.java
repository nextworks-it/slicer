package it.nextworks.nfvmano.sebastian.engine.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InstantiateNsiRequestMessage extends EngineMessage {

	@JsonProperty("nsiId")
	private String nsiId;
	
	@JsonProperty("nfvNsId")
	private String nfvNsId;
	
	@JsonProperty("dfId")
	private String dfId;
	
	@JsonProperty("ilId")
	private String ilId;
	
	

	/**
	 * @param nsiId
	 * @param nfvNsId
	 * @param dfId
	 * @param ilId
	 */
	@JsonCreator
	public InstantiateNsiRequestMessage(@JsonProperty("nsiId") String nsiId, 
			@JsonProperty("nfvNsId") String nfvNsId, 
			@JsonProperty("dfId") String dfId, 
			@JsonProperty("ilId") String ilId) {
		this.type = EngineMessageType.INSTANTIATE_NSI_REQUEST;
		this.nsiId = nsiId;
		this.nfvNsId = nfvNsId;
		this.dfId = dfId;
		this.ilId = ilId;
	}

	/**
	 * @return the nsiId
	 */
	public String getNsiId() {
		return nsiId;
	}

	/**
	 * @return the nfvNsId
	 */
	public String getNfvNsId() {
		return nfvNsId;
	}

	/**
	 * @return the dfId
	 */
	public String getDfId() {
		return dfId;
	}

	/**
	 * @return the ilId
	 */
	public String getIlId() {
		return ilId;
	}
	
	
	
}
