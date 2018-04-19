package it.nextworks.nfvmano.sebastian.catalogue.messages;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsBlueprintInfo;

public class QueryVsBlueprintResponse implements InterfaceMessage {
	
	private List<VsBlueprintInfo> vsBlueprintInfo = new ArrayList<>();

	public QueryVsBlueprintResponse() {	}
	
	public QueryVsBlueprintResponse(List<VsBlueprintInfo> vsBlueprintInfo) {	
		if (vsBlueprintInfo != null) this.vsBlueprintInfo = vsBlueprintInfo;
	}
	
	/**
	 * @return the vsBlueprintInfo
	 */
	public List<VsBlueprintInfo> getVsBlueprintInfo() {
		return vsBlueprintInfo;
	}

	@Override
	public void isValid() throws MalformattedElementException {	
		for (VsBlueprintInfo vsbi : vsBlueprintInfo) vsbi.isValid();
	}

}
