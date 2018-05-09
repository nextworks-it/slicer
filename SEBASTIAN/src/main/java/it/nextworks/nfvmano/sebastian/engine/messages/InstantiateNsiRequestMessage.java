package it.nextworks.nfvmano.sebastian.engine.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InstantiateNsiRequestMessage extends EngineMessage {

	@JsonProperty("nsiId")
	private String nsiId;
	
	@JsonProperty("nfvNsdId")
	private String nfvNsdId;
	
	@JsonProperty("nfvNsdVersion")
	private String nfvNsdVersion;
	
	@JsonProperty("dfId")
	private String dfId;
	
	@JsonProperty("ilId")
	private String ilId;
	
	

	/**
	 * @param nsiId
	 * @param nfvNsId
	 * @param nfvNsdVersion
	 * @param dfId
	 * @param ilId
	 */
	@JsonCreator
	public InstantiateNsiRequestMessage(@JsonProperty("nsiId") String nsiId, 
			@JsonProperty("nfvNsdId") String nfvNsdId,
			@JsonProperty("nfvNsdVersion") String nfvNsdVersion,
			@JsonProperty("dfId") String dfId, 
			@JsonProperty("ilId") String ilId) {
		this.type = EngineMessageType.INSTANTIATE_NSI_REQUEST;
		this.nsiId = nsiId;
		this.nfvNsdId = nfvNsdId;
		this.nfvNsdVersion = nfvNsdVersion;
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
	 * @return the nfvNsdId
	 */
	public String getNfvNsdId() {
		return nfvNsdId;
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

	/**
	 * @return the nfvNsdVersion
	 */
	public String getNfvNsdVersion() {
		return nfvNsdVersion;
	}
	
	
	
}
