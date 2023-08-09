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
package it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NS;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Entity
public class NSCapabilities implements DescriptorInformationElement {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;

	@OneToOne
	@JsonIgnore
	private NSNode nsNode;

	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> monitoringParameter = new ArrayList<>();

	public NSCapabilities() {

	}

	public NSCapabilities(List<String> monitoringParameter) {
		this.monitoringParameter = monitoringParameter;
	}

	public NSCapabilities(NSNode nsNode, List<String> monitoringParameter) {
		this.nsNode = nsNode;
		this.monitoringParameter = monitoringParameter;
	}

	public Long getId() {
		return id;
	}

	public NSNode getNsNode() {
		return nsNode;
	}

	@JsonProperty("monitoringParameter")
	public List<String> getMonitoringParameter() {
		return monitoringParameter;
	}

	@Override
	public void isValid() throws MalformattedElementException {

	}
}
