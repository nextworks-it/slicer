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
package it.nextworks.nfvmano.libs.mec.catalogues.descriptors.appd;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.enums.MeHostPacketAction;
import it.nextworks.nfvmano.libs.common.enums.TrafficFilterType;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The TrafficRuleDescriptor data type describes traffic rules related to a ME application.
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.2.1.9
 * 
 * @author nextworks
 *
 */
@Entity
public class TrafficRuleDescriptor implements DescriptorInformationElement {
	
	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Appd appd;
	
	private String trafficRuleId;
	
	private TrafficFilterType filterType;
	
	private int priority;
	
	@OneToMany(mappedBy = "trd", cascade= CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TrafficFilter> trafficFilter = new ArrayList<>();
	
	private MeHostPacketAction action;
	
	@OneToMany(mappedBy = "trd", cascade= CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MecAppInterfaceDescriptor> dstInterface = new ArrayList<>();
	
	public TrafficRuleDescriptor() {
		//JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param appd AppD this traffic rule descriptor belongs to.
	 * @param trafficRuleId Identifies the traffic rule.
	 * @param filterType Definition of filter type
	 * @param priority Priority of this traffic rule. If traffic rule conflicts, the one with higher priority take precedence.
	 * @param action Identifies the action of the ME host data plane, when a packet matches the trafficFilter
	 */
	public TrafficRuleDescriptor(Appd appd,
			String trafficRuleId,
			TrafficFilterType filterType,
			int priority,
			MeHostPacketAction action) {
		this.appd = appd;
		this.trafficRuleId = trafficRuleId;
		this.filterType = filterType;
		this.priority = priority;
		this.action = action;
	}
	
	
	
	/**
	 * @return the trafficRuleId
	 */
	@JsonProperty("trafficRuleId")
	public String getTrafficRuleId() {
		return trafficRuleId;
	}

	/**
	 * @return the filterType
	 */
	@JsonProperty("filterType")
	public TrafficFilterType getFilterType() {
		return filterType;
	}

	/**
	 * @return the priority
	 */
	@JsonProperty("priority")
	public int getPriority() {
		return priority;
	}

	/**
	 * @return the trafficFilter
	 */
	@JsonProperty("trafficFilter")
	public List<TrafficFilter> getTrafficFilter() {
		return trafficFilter;
	}

	/**
	 * @return the action
	 */
	@JsonProperty("action")
	public MeHostPacketAction getAction() {
		return action;
	}

	/**
	 * @return the dstInterface
	 */
	@JsonProperty("dstInterface")
	public List<MecAppInterfaceDescriptor> getDstInterface() {
		return dstInterface;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (trafficRuleId == null) throw new MalformattedElementException("Traffic rule descriptor without ID.");
		if ((trafficFilter == null) || (trafficFilter.isEmpty())) throw new MalformattedElementException("Traffic rule descriptor without traffic filter.");
		else for (TrafficFilter tf : trafficFilter) tf.isValid();
		if (dstInterface.size() > 2) throw new MalformattedElementException("Traffic rule descriptor with too many interfaces");
		for (MecAppInterfaceDescriptor meid : dstInterface) meid.isValid();
	}

}
