package it.nextworks.nfvmano.sebastian.record.elements;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
public class NetworkSliceInstance {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	private String nsiId;	//ID of the network slice
	
	private String nsdId;	//ID of the descriptor of the NFV network service that implements the network slice
	
	private String dfId; 	//ID of the deployment flavour in the NFV network service
	
	private String instantiationLevelId;	//ID of the instantiation level in the NFV network service
	
	private String nfvNsId;	//ID of the NFV network service that implements the network slice
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> networkSliceSubnetInstances = new ArrayList<>();
	
	private String tenantId;	//owner of the slice
	
	public NetworkSliceInstance() {	}

	/**
	 * @param nsiId ID of the network slice
	 * @param nsdId ID of the descriptor of the NFV network service that implements the network slice
	 * @param dfId ID of the deployment flavour in the NFV network service
	 * @param instantiationLevelId ID of the instantiation level in the NFV network service
	 * @param nfvNsId ID of the NFV network service that implements the network slice
	 * @param networkSliceSubnetInstances in case of composite network slice, the ID of its network slice subnets
	 * @param tenantID owner of the slice
	 */
	public NetworkSliceInstance(String nsiId, String nsdId, String dfId, String instantiationLevelId, String nfvNsId,
			List<String> networkSliceSubnetInstances, String tenantId) {
		this.nsiId = nsiId;
		this.nsdId = nsdId;
		this.dfId = dfId;
		this.instantiationLevelId = instantiationLevelId;
		this.nfvNsId = nfvNsId;
		if (networkSliceSubnetInstances != null) this.networkSliceSubnetInstances = networkSliceSubnetInstances;
		this.tenantId = tenantId;
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

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
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
	
	
	
}
