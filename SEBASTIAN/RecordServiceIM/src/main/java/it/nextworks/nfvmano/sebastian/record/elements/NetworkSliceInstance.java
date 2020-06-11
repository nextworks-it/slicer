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
package it.nextworks.nfvmano.sebastian.record.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;
import javax.persistence.Entity;

import it.nextworks.nfvmano.catalogue.translator.NfvNsInstantiationInfo;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.LocationInfo;
import org.hibernate.annotations.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.CascadeType;

/*An NST may have zero to N NSST embedded.
In the generic case of N NSST case, the network slice instance could refer to up to N nfvNsInstantiationInfoList and N nfvNsIdList,
depending how these NSST are internally composed.
*/
@Entity
public class NetworkSliceInstance {

	@Id
    @GeneratedValue
    @JsonIgnore
    private UUID uuid;
	
	private String name;
	
	private String description;
	
	private String nsiId;	//ID of the network slice
	
	private String nstId; //OLD;	//ID of the network slice template

	private String nsdId;//OLD	//ID of the descriptor of the NFV network service that implements the network slice

	private String nsdVersion;//OLD	//version of the descriptor of the NFV network service that implements the network slice
	
	private String dfId;//OLD 	//ID of the deployment flavour in the NFV network service
	
	private String instantiationLevelId;//OLD	//ID of the instantiation level in the NFV network service
	
	@JsonIgnore
	private String oldInstantiationLevelId;//OLD //ID of the previous instantiation level when the NFV network service is scaled
	
	private String nfvNsId;	//ID of the NFV network service that implements the network slice

	private boolean soManaged;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> networkSliceSubnetInstances = new ArrayList<>();
	
	private String tenantId;	//owner of the slice
	
	private NetworkSliceStatus status;
	
	private String errorMessage; //this field gets a value only in case of failure

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String nfvNsUrl;


    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(targetClass= NfvNsInstantiationInfo.class)
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<NfvNsInstantiationInfo> nfvNsInstantiationInfoList;

    /*
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(targetClass=ImsiInfo.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ImsiInfo> imsiInfoList;*/

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> nfvNsIdList;

	@OneToMany
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ImsiInfo> imsiInfoList = new ArrayList<>();

	private Integer ranSliceId;

	public NetworkSliceInstance() {	}

	/**
	 * @param nsiId ID of the network slice
	 * @param nstId ID of the network slice template
	 * @param tenantId owner of the slice
	 */

	public NetworkSliceInstance(String nsiId,
								String nstId,
								String tenantId,
								String name,
								String description,
								boolean soManaged){
		this.nsiId=nsiId;
		this.nstId = nstId;
		this.tenantId=tenantId;
		this.name=name;
		this.description=description;
		this.soManaged = soManaged;
		this.nfvNsInstantiationInfoList=new ArrayList<NfvNsInstantiationInfo>();
		this.nfvNsIdList = new ArrayList<String>();
		this.networkSliceSubnetInstances=new ArrayList<String>();
		this.ranSliceId=-1;
		//if(imsiInfoList!=null) this.imsiInfoList = imsiInfoList;
	}


	public boolean addNfvNetworkServiceInfo(NfvNsInstantiationInfo nfvNsInstantiationInfo, String networkServiceId){
		nfvNsInstantiationInfoList.add(nfvNsInstantiationInfo);
		nfvNsIdList.add(networkServiceId);
		return true;
	}

	public boolean setNfvNetworkServiceId(String nsstUuid, String nfvNsiId){
		for(int i=0; i<nfvNsInstantiationInfoList.size(); i++){
			if(nfvNsInstantiationInfoList.get(i).getNstId().equals(nsstUuid)){
				nfvNsIdList.set(i,nfvNsiId);
				return true;
			}
		}
		return false;
	}

	public boolean setDeploymentFlavourId(String nsstUuid, String newDeploymentFlavourId){
		for(NfvNsInstantiationInfo nfvNsInstantiationInfoTmp: nfvNsInstantiationInfoList){
			if(nfvNsInstantiationInfoTmp.getNstId().equals(nsstUuid)){
				nfvNsInstantiationInfoTmp.setDeploymentFlavourId(newDeploymentFlavourId);
				return true;
			}
		}
		return false;
	}

	public boolean setInstantiationLevel(String nsstId, String newInstantiationLevel){
		for(NfvNsInstantiationInfo nfvNsInstantiationInfoTmp: nfvNsInstantiationInfoList){
			if(nfvNsInstantiationInfoTmp.getNstId().equals(nsstId)){
				nfvNsInstantiationInfoTmp.setInstantiationLevelId(newInstantiationLevel);
				return true;
			}
		}
		return false;
	}


	public NfvNsInstantiationInfo getNsInstantiationInfo(boolean current) {
		if (current)
		return new NfvNsInstantiationInfo(
				nsdId,
				nsdVersion,
				dfId,
				instantiationLevelId,
				new ArrayList<String>()); 
		else return new NfvNsInstantiationInfo(
				nsdId,
				nsdVersion,
				dfId,
				oldInstantiationLevelId,
				new ArrayList<String>());
	}
	
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the nsiId
	 */
	public String getNsiId() {
		return nsiId;
	}

	/**
	 * @param nsiId the nsiId to set
	 */
	public void setNsiId(String nsiId) {
		this.nsiId = nsiId;
	}
	
	

	public void setDfId(String dfId) {
		this.dfId = dfId;
	}

	public void setNetworkSliceSubnetInstances(List<String> networkSliceSubnetInstances) {
		this.networkSliceSubnetInstances = networkSliceSubnetInstances;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 *
	 * @return soManaged: if true, NS is created and managed by SO
	 */
	public boolean getSoManaged() {
		return soManaged;
	}

	/**
	 *
	 * @param soManaged if true, NS is created and managed by SO
	 */
	public void setSoManaged(boolean soManaged) {
		this.soManaged = soManaged;
	}

	/**
	 * @return the nsdId
	 */
	public String getNsdId() {
		return nsdId;
	}

	/**
	 * @return the dfId
	 */
	public String getDfId() {
		return dfId;
	}

	/**
	 * @return the instantiationLevelId
	 */
	public String getInstantiationLevelId() {
		return instantiationLevelId;
	}

	/**
	 *
 	 * @param instantiationLevelId
	 */
	public void setInstantiationLevelId(String instantiationLevelId) {
		this.instantiationLevelId = instantiationLevelId;
	}

	/**
	 * @return the nfvNsId
	 */
	public String getNfvNsId() {
		return nfvNsId;
	}

	/**
	 * @return the networkSliceSubnetInstances
	 */
	public List<String> getNetworkSliceSubnetInstances() {
		return networkSliceSubnetInstances;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @return the nsdVersion
	 */
	public String getNsdVersion() {
		return nsdVersion;
	}

	/**
	 * @return the status
	 */
	public NetworkSliceStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(NetworkSliceStatus status) {
		this.status = status;
	}

	/**
	 * @param nfvNsId the nfvNsId to set
	 */
	public void setNfvNsId(String nfvNsId) {
		this.nfvNsId = nfvNsId;
	}

	/**
	 * This method adds a new slice subnet into the main slice
	 * 
	 * @param subnetId ID of the subnet to be added
	 */
	public void addSubnet(String subnetId) {
		this.networkSliceSubnetInstances.add(subnetId);
	}
	
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	public String getNfvNsUrl() {
		return nfvNsUrl;
	}

	public void setNfvNsUrl(String nfvNsUrl) {
		this.nfvNsUrl = nfvNsUrl;
	}
	
	
	
	public String getNstId() {
		return nstId;
	}

	@JsonIgnore
	public void updateInstantiationLevelAfterScaling(String newInstantiationLevel) {
		oldInstantiationLevelId = this.instantiationLevelId;
		instantiationLevelId = newInstantiationLevel;
	}
	
	

	/**
	 * @return the oldInstantiationLevelId
	 */
	@JsonIgnore
	public String getOldInstantiationLevelId() {
		return oldInstantiationLevelId;
	}

	/**
	 * @param oldInstantiationLevelId the oldInstantiationLevelId to set
	 */
	@JsonIgnore
	public void setOldInstantiationLevelId(String oldInstantiationLevelId) {
		this.oldInstantiationLevelId = oldInstantiationLevelId;
	}

	/**
	 * This method fills the failure related fields
	 * 
	 * @param errorMessage the cause of the slice failure
	 */
	public void setFailureState(String errorMessage) {
		this.status = NetworkSliceStatus.FAILED;
		this.errorMessage = errorMessage;
	}

	public UUID getUuid() {
		return uuid;
	}

	public List<NfvNsInstantiationInfo> getNfvNsInstantiationInfoList() {
		return nfvNsInstantiationInfoList;
	}

	public List<String> getNfvNsIdList() {
		return nfvNsIdList;
	}

	public List<ImsiInfo> getImsiInfoList() {
		return imsiInfoList;
	}

	public void setImsiInfoList(List<ImsiInfo> imsiInfoList) {
		this.imsiInfoList = imsiInfoList;
	}

	public Integer getRanSliceId() {
		return ranSliceId;
	}

	public void setRanSliceId(Integer ranSliceId) {
		this.ranSliceId = ranSliceId;
	}
}
