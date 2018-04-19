package it.nextworks.nfvmano.sebastian.catalogue.messages;

import it.nextworks.nfvmano.libs.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsDescriptor;

public class OnboardVsDescriptorRequest implements InterfaceMessage {

	private VsDescriptor vsd;
	private String tenantId;
	private boolean isPublic;
	
	public OnboardVsDescriptorRequest() { }
	
	

	/**
	 * @param vsd
	 * @param tenantId
	 * @param isPublic
	 */
	public OnboardVsDescriptorRequest(VsDescriptor vsd, String tenantId, boolean isPublic) {
		this.vsd = vsd;
		this.tenantId = tenantId;
		this.isPublic = isPublic;
	}



	/**
	 * @return the vsd
	 */
	public VsDescriptor getVsd() {
		return vsd;
	}



	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}



	/**
	 * @return the isPublic
	 */
	public boolean isPublic() {
		return isPublic;
	}



	@Override
	public void isValid() throws MalformattedElementException {
		if (vsd == null) throw new MalformattedElementException("Onboard VSD request without VSD");
		else vsd.isValid();
		if (tenantId == null) throw new MalformattedElementException("Onboard VSD request without tenant ID");
	}

}
