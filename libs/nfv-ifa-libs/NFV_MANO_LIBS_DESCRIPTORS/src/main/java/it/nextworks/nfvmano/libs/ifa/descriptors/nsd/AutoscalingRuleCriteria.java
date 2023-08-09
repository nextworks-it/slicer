package it.nextworks.nfvmano.libs.ifa.descriptors.nsd;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.RelationalOperation;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Information element defining a threshold criteria in a condition that must be matched 
 * to trigger a scaling rule for a NS.
 * This is not standard, since not specified in IFA so far.
 * This version is inspired by OSM information model.
 * 
 * @author nextworks
 *
 */
@Entity
public class AutoscalingRuleCriteria implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private AutoscalingRuleCondition condition;
	
	private String name;
	
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private Integer scaleInThreshold;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private RelationalOperation scaleInRelationalOperation;
	
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private Integer scaleOutThreshold;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private RelationalOperation scaleOutRelationalOperation;
		
	private String nsMonitoringParamRef;
	
	public AutoscalingRuleCriteria() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param condition Autoscaling rule condition this criteria belongs to.
	 * @param name Name of the autoscaling criteria.
	 * @param scaleInThreshold Value of the threshold for scale in action.
	 * @param scaleInRelationalOperation Relational operation to evaluate the threshould for scale in action.
	 * @param scaleOutThreshould Value of the threshold for scale out action.
	 * @param scaleOutRelationalOperation Relational operation to evaluate the threshould for scale out action.
	 * @param nsMonitoringParamRef ID of the monitoring parameter in the NSD the threshold is referred to.
	 */
	public AutoscalingRuleCriteria(AutoscalingRuleCondition condition,
			String name,
			Integer scaleInThreshold,
			RelationalOperation scaleInRelationalOperation,
			Integer scaleOutThreshould,
			RelationalOperation scaleOutRelationalOperation,
			String nsMonitoringParamRef) {
		this.condition = condition;
		this.name = name;
		this.scaleInThreshold = scaleInThreshold;
		this.scaleInRelationalOperation = scaleInRelationalOperation;
		this.scaleOutThreshold = scaleOutThreshould;
		this.scaleOutRelationalOperation = scaleOutRelationalOperation;
		this.nsMonitoringParamRef = nsMonitoringParamRef;
	}

	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the scaleInThreshold
	 */
	public Integer getScaleInThreshold() {
		return scaleInThreshold;
	}

	/**
	 * @return the scaleInRelationalOperation
	 */
	public RelationalOperation getScaleInRelationalOperation() {
		return scaleInRelationalOperation;
	}

	/**
	 * @return the scaleOutThreshold
	 */
	public Integer getScaleOutThreshold() {
		return scaleOutThreshold;
	}

	/**
	 * @return the scaleOutRelationalOperation
	 */
	public RelationalOperation getScaleOutRelationalOperation() {
		return scaleOutRelationalOperation;
	}

	/**
	 * @return the nsMonitoringParamRef
	 */
	public String getNsMonitoringParamRef() {
		return nsMonitoringParamRef;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.name == null) throw new MalformattedElementException("Autoscaling rule criteria without name");
		if (this.nsMonitoringParamRef == null) throw new MalformattedElementException("Autoscaling rule criteria without monitoring parameter");
		if ( (this.scaleInRelationalOperation == null) && (this.scaleOutRelationalOperation == null) )
				throw new MalformattedElementException("Autoscaling rule criteria without relational operation");
	}

}
