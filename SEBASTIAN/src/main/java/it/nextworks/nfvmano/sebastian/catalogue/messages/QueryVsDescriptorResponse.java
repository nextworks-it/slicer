package it.nextworks.nfvmano.sebastian.catalogue.messages;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsDescriptor;

public class QueryVsDescriptorResponse implements InterfaceMessage {

	private List<VsDescriptor> vsDescriptors = new ArrayList<>();
	
	public QueryVsDescriptorResponse() { }
	
	public QueryVsDescriptorResponse(List<VsDescriptor> vsDescriptors) {
		if (vsDescriptors != null) this.vsDescriptors = vsDescriptors;
	}
	
	/**
	 * @return the vsDescriptors
	 */
	public List<VsDescriptor> getVsDescriptors() {
		return vsDescriptors;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		for (VsDescriptor vsd : vsDescriptors) vsd.isValid();
	}

}
