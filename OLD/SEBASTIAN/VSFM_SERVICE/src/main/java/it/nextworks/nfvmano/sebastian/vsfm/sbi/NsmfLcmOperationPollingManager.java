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
package it.nextworks.nfvmano.sebastian.vsfm.sbi;

import it.nextworks.nfvmano.catalogue.domainLayer.NspNbiType;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;

import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
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
 * Class that manages the periodical polling of NSMF LCM operations
 *
 * @author nextworks
 */
@EnableScheduling
@Service
public class NsmfLcmOperationPollingManager implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(NsmfLcmOperationPollingManager.class);

    @Value("${nsmf.lcm.polling:60}")
    private int pollingPeriod;

    //map of active polled operations on NSMF LCM. The key is the operation ID. The value provides the info to poll the operation.
    private Map<String, PolledNsmfLcmOperation> polledOperations = new HashMap<>();

    private NsmfLcmProviderInterface nsmfLcmProvider;

    @Autowired
    private VsLcmService vsLcmService;

    public NsmfLcmOperationPollingManager() {
        log.debug("Initializing NSMF operations polling manager");
    }

    /**
     * @return the executor of the NFVO polling thread task
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
                new NsmfLcmOperationPollingTask(this, nsmfLcmProvider, vsLcmService),
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
     * Adds a new TIMEO NFVO operation in the list of the operations to be polled
     *
     * @param operationId    ID of the NFVO operation to be polled
     * @param expectedStatus expected status of the operation - when the operation reaches this status the listener is notified
     * @param nsiId          ID of the network service instance the operation refers to
     * @param operationType  type of operation
     */
    public synchronized void addOperation(String operationId, OperationStatus expectedStatus, String nsiId, String operationType, String domainId, NspNbiType domainType) {
        PolledNsmfLcmOperation operation = new PolledNsmfLcmOperation(operationId, expectedStatus, nsiId, operationType, domainId, domainType);
        this.polledOperations.put(operationId, operation);
        log.info("Added operation " + operationId + " to the list of NSFM operations in polling. NsiId: " + nsiId + " - Expected status: " + expectedStatus.toString());
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
    public synchronized Map<String, PolledNsmfLcmOperation> getPolledOperationsCopy() {
        Map<String, PolledNsmfLcmOperation> copy = new HashMap<>();
        for (Map.Entry<String, PolledNsmfLcmOperation> e : polledOperations.entrySet()) {
            copy.put(e.getKey(), e.getValue());
        }
        return copy;
    }

    public void setNsmfLcmProvider(NsmfLcmProviderInterface nsmfLcmProvider) {
        this.nsmfLcmProvider = nsmfLcmProvider;
    }
}
