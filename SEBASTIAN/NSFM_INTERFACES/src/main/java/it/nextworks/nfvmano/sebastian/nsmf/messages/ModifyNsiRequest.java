/*
 * Copyright (c) 2019 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.sebastian.nsmf.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

public class ModifyNsiRequest implements InterfaceMessage {

	@JsonProperty("nsiId")
    private String nsiId;

    @JsonProperty("nstId")
    private String nstId;

    /*OLD
    @JsonProperty("dfId")
    private String dfId;

    @JsonProperty("ilId")
    private String ilId;
*/
    @JsonProperty("vsiId")
    private String vsiId;
	
    
    /**
     * Constructor
     * 
     * @param nsiId ID of the network slice instance to be modified
     * @param nstId ID of the target network slice template
     * @param dfId ID of the target flavour of the NFV NS associated to the NST
     * @param ilId ID of the target instantiation level of the NFV NS associated to the NST
     * @param vsiId ID of the vertical service instance associated to the network slice
     */
	public ModifyNsiRequest(String nsiId, String nstId, String dfId, String ilId, String vsiId) {//OLD
		this.nsiId = nsiId;
		this.nstId = nstId;
		//this.dfId = dfId;
		//this.ilId = ilId;
		this.vsiId = vsiId;
	}

	public ModifyNsiRequest(String nsiId, String nstId, String vsiId) {
		this.nsiId = nsiId;
		this.nstId = nstId;
		//this.dfId = dfId;
		//this.ilId = ilId;
		this.vsiId = vsiId;
	}

	public ModifyNsiRequest(){}

	public String getNsiId() {
		return nsiId;
	}



	public String getNstId() {
		return nstId;
	}


/* OLD
	public String getDfId() {
		return dfId;
	}



	public String getIlId() {
		return ilId;
	}
*/


	public String getVsiId() {
		return vsiId;
	}



	@Override
	public void isValid() throws MalformattedElementException {
		if (nsiId == null) throw new MalformattedElementException("Modify NSI request without NS instance ID");
		if (nstId == null) throw new MalformattedElementException("Modify NSI request without NS template ID");
		//if (dfId == null) throw new MalformattedElementException("Modify NSI request without NFV NS DF ID"); OLD
		//if (ilId == null) throw new MalformattedElementException("Modify NSI request without NFV NS IL ID");
	}

}
