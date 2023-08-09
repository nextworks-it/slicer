package it.nextworks.nfvmano.libs.ifa.descriptors.nsd;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.LogicOperation;
import it.nextworks.nfvmano.libs.ifa.common.enums.ScalingProcedureType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Information element defining a condition that must be matched to trigger a scaling rule for a NS.
 * This is not standard, since not specified in IFA so far.
 * This version is inspired by OSM information model.
 * 
 * @author nextworks
 *
 */
@Entity
public class AutoscalingRuleCondition implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    @JoinColumn(name = "rule_conditions_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
	private NsAutoscalingRule rule;
	
	private String name;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private ScalingProcedureType scalingType;
	
	private boolean enabled;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private LogicOperation scaleInOperationType;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private LogicOperation scaleOutOperationType;
	
	private int thresholdTime;
	
	private int cooldownTime;
	
	private String initialInstantiationLevel;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "condition", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<AutoscalingRuleCriteria> scalingCriteria = new ArrayList<>();
	
	public AutoscalingRuleCondition() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param rule Autoscaling rule this condition belongs to.
	 * @param name Name of the condition.
	 * @param scalingType Type of scaling action.
	 * @param enabled Current status of the condition.
	 * @param scaleInOperationType Type of logic operation to be applied to the criteria for scale in action.
	 * @param scaleOutOperationType Type of logic operation to be applied to the criteria for scale out action.
	 * @param thresholdTime Time interval when the threshold must be passed. 
	 * @param cooldownTime Time interval after the action.
	 * @param initialInstantiationLevel Original instantiation level of the NS instance.
	 */
	public AutoscalingRuleCondition(NsAutoscalingRule rule,
			String name,
			ScalingProcedureType scalingType,
			boolean enabled,
			LogicOperation scaleInOperationType,
			LogicOperation scaleOutOperationType,
			int thresholdTime,
			int cooldownTime,
			String initialInstantiationLevel) {
		this.rule = rule;
		this.name = name;
		this.scalingType = scalingType;
		this.enabled = enabled;
		this.scaleInOperationType = scaleInOperationType;
		this.scaleOutOperationType = scaleOutOperationType;
		this.thresholdTime = thresholdTime;
		this.cooldownTime = cooldownTime;
		this.initialInstantiationLevel = initialInstantiationLevel;
	}
	
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the scalingType
	 */
	public ScalingProcedureType getScalingType() {
		return scalingType;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @return the scaleInOperationType
	 */
	public LogicOperation getScaleInOperationType() {
		return scaleInOperationType;
	}

	/**
	 * @return the scaleOutOperationType
	 */
	public LogicOperation getScaleOutOperationType() {
		return scaleOutOperationType;
	}

	/**
	 * @return the thresholdTime
	 */
	public int getThresholdTime() {
		return thresholdTime;
	}

	/**
	 * @return the cooldownTime
	 */
	public int getCooldownTime() {
		return cooldownTime;
	}

	/**
	 * @return the scalingCriteria
	 */
	public List<AutoscalingRuleCriteria> getScalingCriteria() {
		return scalingCriteria;
	}

	
	
	/**
	 * @return the initialInstantiationLevel
	 */
	public String getInitialInstantiationLevel() {
		return initialInstantiationLevel;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.name == null) throw new MalformattedElementException("Autoscaling rule condition without name");
		if ((this.scalingCriteria == null) || (this.scalingCriteria.isEmpty())) 
			throw new MalformattedElementException("Autoscaling rule condition without criteria"); 
			else for (AutoscalingRuleCriteria arc : scalingCriteria) arc.isValid();
	}

	public void setRule(NsAutoscalingRule rule) {
		this.rule = rule;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScalingType(ScalingProcedureType scalingType) {
		this.scalingType = scalingType;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setScaleInOperationType(LogicOperation scaleInOperationType) {
		this.scaleInOperationType = scaleInOperationType;
	}

	public void setScaleOutOperationType(LogicOperation scaleOutOperationType) {
		this.scaleOutOperationType = scaleOutOperationType;
	}

	public void setThresholdTime(int thresholdTime) {
		this.thresholdTime = thresholdTime;
	}

	public void setCooldownTime(int cooldownTime) {
		this.cooldownTime = cooldownTime;
	}

	public void setInitialInstantiationLevel(String initialInstantiationLevel) {
		this.initialInstantiationLevel = initialInstantiationLevel;
	}

	public void setScalingCriteria(List<AutoscalingRuleCriteria> scalingCriteria) {
		this.scalingCriteria = scalingCriteria;
	}
}
