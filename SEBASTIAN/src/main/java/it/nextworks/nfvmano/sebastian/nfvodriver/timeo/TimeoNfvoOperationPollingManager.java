package it.nextworks.nfvmano.sebastian.nfvodriver.timeo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.common.enums.OperationStatus;
import it.nextworks.nfvmano.sebastian.nfvodriver.NfvoNotificationsManager;
import it.nextworks.nfvmano.sebastian.nfvodriver.NfvoService;

/**
 * Class that manages the periodical polling of TIMEO NFVO operations
 * 
 * @author nextworks
 *
 */
@EnableScheduling
@Service
public class TimeoNfvoOperationPollingManager implements SchedulingConfigurer {

	private static final Logger log = LoggerFactory.getLogger(TimeoNfvoOperationPollingManager.class);
	
	@Value("${sebastian.nfvo.timeo.polling}")
	private int timeoPollingPeriod;
	
	//map of active polled operations on TIMEO NFVO. The key is the operation ID. The value provides the info to poll the operation.
	private Map<String, PolledTimeoNfvoOperation> polledOperations = new HashMap<>();
	
	@Autowired
	private NfvoService nfvoService;
	
	@Autowired
	NfvoNotificationsManager nfvoNotificationManager;
	
	public TimeoNfvoOperationPollingManager() {
		log.debug("Initializing TIMEO NFVO operations polling manager");
	}
	
	/**
	 * 
	 * @return the executor of the NFVO polling thread task
	 */
	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		return Executors.newSingleThreadScheduledExecutor();
	}

	/**
	 * Method to trigger the periodical polling task. 
	 * The period is configured in the application property file.
	 * 
	 */
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskExecutor());
		taskRegistrar.addTriggerTask(
				new TimeoNfvoOperationPollingTask(this, nfvoService, nfvoNotificationManager),
				new Trigger() {
					
					@Override
					public Date nextExecutionTime(TriggerContext triggerContext) {
						Calendar nextExecutionTime = new GregorianCalendar();
						Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
						nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
						nextExecutionTime.add(Calendar.SECOND, timeoPollingPeriod);
						return nextExecutionTime.getTime();
					}
				});
	}

	
	/**
	 * Adds a new TIMEO NFVO operation in the list of the operations to be polled
	 * 
	 * @param operationId ID of the NFVO operation to be polled
	 * @param expectedStatus expected status of the operation - when the operation reaches this status the listener is notified
	 * @param nfvNsiId ID of the network service instance the operation refers to
	 * @param operationType type of operation
	 */
	public synchronized void addOperation(String operationId, OperationStatus expectedStatus, String nfvNsiId, String operationType) {
		PolledTimeoNfvoOperation operation = new PolledTimeoNfvoOperation(operationId, expectedStatus, nfvNsiId, operationType);
		this.polledOperations.put(operationId, operation);
		log.debug("Added operation " + operationId + " to the list of TIMEO NFVO operations in polling. Expected status: " + expectedStatus.toString());
	}
	
	/**
	 * Removes an operation with the given ID from the list of operations to be polled
	 * 
	 * @param operationId ID the VNFM operation to be removed from the list
	 */
	public synchronized void removeOperation(String operationId) {
		this.polledOperations.remove(operationId);
		log.debug("Operation " + operationId + " removed from the list of VNFM operations to be polled");
	}


//	/**
//	 * @return the polledOperations
//	 */
//	public Map<String, PolledTimeoNfvoOperation> getPolledOperations() {
//		return polledOperations;
//	}
	
	/**
	 * @return a copy of the polledOperations
	 */
	public synchronized Map<String, PolledTimeoNfvoOperation> getPolledOperationsCopy() {
		Map<String, PolledTimeoNfvoOperation> copy = new HashMap<>();
		for (Map.Entry<String, PolledTimeoNfvoOperation> e : polledOperations.entrySet()) {
			copy.put(e.getKey(), e.getValue());
		}
		return copy;
	}

	
}
