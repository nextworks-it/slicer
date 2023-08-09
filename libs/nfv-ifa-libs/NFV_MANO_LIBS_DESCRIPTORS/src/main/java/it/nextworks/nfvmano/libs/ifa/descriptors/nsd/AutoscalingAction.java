package it.nextworks.nfvmano.libs.ifa.descriptors.nsd;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.NsScaleType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

@Entity
public class AutoscalingAction implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private NsAutoscalingRule rule;
	
	private NsScaleType scaleType;
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy = "action", cascade=CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ScaleNsToLevelData scaleNsToLevelData;
	
	public AutoscalingAction() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param rule scaling rule this action belongs to.
	 * @param scaleType type of scaling to be performed
	 */
	public AutoscalingAction(NsAutoscalingRule rule, 
			NsScaleType scaleType) {
		this.rule = rule;
		this.scaleType = scaleType;
	}

	/**
	 * @return the scaleType
	 */
	public NsScaleType getScaleType() {
		return scaleType;
	}

	/**
	 * @return the scaleNsToLevelData
	 */
	public ScaleNsToLevelData getScaleNsToLevelData() {
		return scaleNsToLevelData;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.scaleType == null) throw new MalformattedElementException("Autoscaling action without type");
		if (this.scaleType != NsScaleType.SCALE_NS) throw new MalformattedElementException("Only NS scaling actions are acceptable in autoscaling rules so far.");
		if (this.scaleNsToLevelData == null) throw new MalformattedElementException("Autoscaling action without content.");
		else this.scaleNsToLevelData.isValid();
	}

}
