package it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.polling;

import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.sebastian.vsfm.VsLcmService;
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

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Class that manages the periodical polling of VSMF LCM operations
 *
 * @author nextworks
 */
@EnableScheduling
@Service
public class VsmfLcmOperationPollingManager implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(VsmfLcmOperationPollingManager.class);

    @Value("${vsmf.lcm.polling:60}")
    private int pollingPeriod;

    //map of active polled operations on VSMF LCM. The key is the operation ID. The value provides the info to poll the operation.
    private Map<String, PolledVsmfLcmOperation> polledOperations = new HashMap<>();




    @Autowired
    private VsLcmService vsLcmService;

    public VsmfLcmOperationPollingManager() {
        log.debug("Initializing VSMF operations polling manager");
    }

    /**
     * @return the executor of the VSMF polling thread task
     */
    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Method to trigger the periodical polling task.
     * The period is configured in the application property file.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        taskRegistrar.addTriggerTask(
                new VsmfLcmOperationPollingTask(vsLcmService, vsLcmService, this),
                new Trigger() {

                    @Override
                    public Date nextExecutionTime(TriggerContext triggerContext) {
                        Calendar nextExecutionTime = new GregorianCalendar();
                        Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
                        nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
                        nextExecutionTime.add(Calendar.SECOND, pollingPeriod);
                        return nextExecutionTime.getTime();
                    }
                });
    }


    /**
     * Adds a new VSMF  operation in the list of the operations to be polled
     *
     * @param operationId    ID of the VSMF operation to be polled
     * @param expectedStatus expected status of the operation - when the operation reaches this status the listener is notified
     * @param vsiId          ID of the network service instance the operation refers to
     * @param operationType  type of operation
     */
    public synchronized void addOperation(String operationId, OperationStatus expectedStatus, String vsiId, String operationType, String domainId) {
        PolledVsmfLcmOperation operation = new PolledVsmfLcmOperation(operationId, expectedStatus, vsiId, operationType, domainId);
        this.polledOperations.put(operationId, operation);
        log.info("Added operation " + operationId + " to the list of VSFM operations in polling. vsiId: " + vsiId + " - Expected status: " + expectedStatus.toString());
    }

    /**
     * Removes an operation with the given ID from the list of operations to be polled
     *
     * @param operationId ID the VNFM operation to be removed from the list
     */
    public synchronized void removeOperation(String operationId) {
        this.polledOperations.remove(operationId);
        log.info("Operation " + operationId + " removed from the list of NSFM operations to be polled");
    }


    /**
     * @return a copy of the polledOperations
     */
    public synchronized Map<String, PolledVsmfLcmOperation> getPolledOperationsCopy() {
        Map<String, PolledVsmfLcmOperation> copy = new HashMap<>();
        for (Map.Entry<String, PolledVsmfLcmOperation> e : polledOperations.entrySet()) {
            copy.put(e.getKey(), e.getValue());
        }
        return copy;
    }
}