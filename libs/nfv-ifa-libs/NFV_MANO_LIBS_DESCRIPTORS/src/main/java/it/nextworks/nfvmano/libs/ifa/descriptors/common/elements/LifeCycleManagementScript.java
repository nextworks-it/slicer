/*
* Copyright 2018 Nextworks s.r.l.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package it.nextworks.nfvmano.libs.ifa.descriptors.common.elements;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.LcmEventType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.libs.ifa.descriptors.vnfd.Vnfd;

/**
 * Information element related to the lifecycle management script for the VNF.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.13
 * REF. IFA-014 v2.3.1 - section 6.2.9
 * 
 * @author nextworks
 *
 */
@Entity
public class LifeCycleManagementScript implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@ManyToOne
	@JsonIgnore
	private Vnfd vnfd;
	
	@ManyToOne
	@JsonIgnore
	private Nsd nsd;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<LcmEventType> event = new ArrayList<>();
	
	@Lob @Basic(fetch=FetchType.EAGER)
	@Column(columnDefinition = "TEXT")
	private String script;
	
	public LifeCycleManagementScript() { }
	
	/**
	 * Constructor
	 * 
	 * @param vnfd VNFD this script belongs to
	 * @param event Describes VNF lifecycle event(s) or an external stimulus detected on a VNFM reference point.
	 * @param script Includes a VNF LCM script triggered to react to one of the events listed in the event attribute.
	 */
	public LifeCycleManagementScript(Vnfd vnfd,
			List<LcmEventType> event,
			String script) { 
		this.vnfd = vnfd;
		if (event != null) this.event = event;
		this.script = script;
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsd NSD this script belongs to
	 * @param event Describes NS lifecycle event(s) or an external stimulus detected on an NFVO reference point.
	 * @param script Includes a NS LCM script triggered to react to one of the events listed in the event attribute.
	 */
	public LifeCycleManagementScript(Nsd nsd,
			List<LcmEventType> event,
			String script) { 
		this.nsd = nsd;
		if (event != null) this.event = event;
		this.script = script;
	}
	

	/**
	 * @return the event
	 */
	public List<LcmEventType> getEvent() {
		return event;
	}

	/**
	 * @return the script
	 */
	public String getScript() {
		return script;
	}
	
	@JsonIgnore
	public boolean includeEvent(LcmEventType eventType) {
		for (LcmEventType t : event) {
			if (t == eventType) return true;
		}
		return false;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (script == null) throw new MalformattedElementException("LCM script without script info");
		if ((event == null) || (event.isEmpty())) throw new MalformattedElementException("LCM script without event");
	}

	public void setVnfd(Vnfd vnfd) {
		this.vnfd = vnfd;
	}

	public void setNsd(Nsd nsd) {
		this.nsd = nsd;
	}

	public void setEvent(List<LcmEventType> event) {
		this.event = event;
	}

	public void setScript(String script) {
		this.script = script;
	}
}
