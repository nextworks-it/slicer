package it.nextworks.nfvmano.sebastian.translator;

public class NfvNsInstantiationInfo {

	private String nfvNsdId;
	private String nsdVersion;
	private String deploymentFlavourId;
	private String instantiationLevelId;
	
	/**
	 * Constructor
	 * 
	 * @param nfvNsdId NSD ID
	 * @param nsdVersion NSD version
	 * @param deploymentFlavourId NS Deployment Flavour ID
	 * @param instantiationLevelId NS Instantiation Level ID 
	 */
	public NfvNsInstantiationInfo(String nfvNsdId,
			String nsdVersion,
			String deploymentFlavourId,
			String instantiationLevelId) {
		this.nfvNsdId = nfvNsdId;
		this.nsdVersion = nsdVersion;
		this.deploymentFlavourId = deploymentFlavourId;
		this.instantiationLevelId = instantiationLevelId;
	}
	
	/**
	 * @return the nfvNsdId
	 */
	public String getNfvNsdId() {
		return nfvNsdId;
	}
	
	
	
	/**
	 * @return the nsdVersion
	 */
	public String getNsdVersion() {
		return nsdVersion;
	}

	/**
	 * @return the deploymentFlavourId
	 */
	public String getDeploymentFlavourId() {
		return deploymentFlavourId;
	}
	
	/**
	 * @return the instantiationLevelId
	 */
	public String getInstantiationLevelId() {
		return instantiationLevelId;
	}
	
}
