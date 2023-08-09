/*
 * Copyright (c) 2021 Nextworks s.r.l.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.nextworks.nfvmano.nssmf.record.services.system;

import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.nssmf.elements.NssiErrors;
import it.nextworks.nfvmano.libs.vs.common.nssmf.elements.NssiStatus;
import it.nextworks.nfvmano.nssmf.record.im.system.NssiBaseRecord;
import it.nextworks.nfvmano.nssmf.record.repo.system.NssiBaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class NssiBaseRecordService {

    private static final Logger log = LoggerFactory.getLogger(NssiBaseRecordService.class);

    @Autowired
    private NssiBaseRepository nssiBaseRepository;

    public synchronized void createNssiEntry(UUID nssiId, NssiStatus status){
        log.debug("Creating a new Network Sub-Slice instance - NSSI ID: "+nssiId.toString()+", Status: " + status.toString());
        NssiBaseRecord nssiBaseRecord = new NssiBaseRecord(nssiId, status);
        nssiBaseRepository.saveAndFlush(nssiBaseRecord);
        log.debug("NSSI: "+nssiId.toString()+" stored in DB");
    }

    public synchronized NssiBaseRecord getNssi(UUID nssiId) throws NotExistingEntityException {
        log.debug("Retrieving NSIS with ID " + nssiId.toString() + " from DB.");
        Optional<NssiBaseRecord> nsi = nssiBaseRepository.findByNssiId(nssiId);
        if (nsi.isPresent()) return nsi.get();
        else throw new NotExistingEntityException("NSI with ID " + nssiId.toString() + " not present in DB.");
    }

    public synchronized void nssiStatusUpdate(UUID nssiId, NssiStatus status) throws NotExistingEntityException {
        NssiBaseRecord nssiBaseRecord = getNssi(nssiId);
        nssiBaseRecord.setStatus(status);
        nssiBaseRepository.saveAndFlush(nssiBaseRecord);
        log.debug("NSSI: "+nssiId.toString()+", Status Updated: " + status.toString());
    }

    public synchronized NssiStatus getLastStatus(UUID nssiId) throws NotExistingEntityException {
        NssiBaseRecord nssiBaseRecord = getNssi(nssiId);
        return nssiBaseRecord.getStatus();
    }

    public synchronized void nssiConfigurationUpdate(UUID nssiId, UUID configId) throws NotExistingEntityException {
        NssiBaseRecord nssiBaseRecord = getNssi(nssiId);
        nssiBaseRecord.setConfigId(configId);
        nssiBaseRepository.saveAndFlush(nssiBaseRecord);
        log.debug("NSSI: "+nssiId.toString()+", Configuration ID Updated: " + configId.toString());
    }

    public synchronized void deleteNssiEntry(UUID nssiId) throws NotExistingEntityException {
        log.debug("Removing NSSI with ID " + nssiId.toString() + " from DB.");
        NssiBaseRecord nssiBaseRecord = this.getNssi(nssiId);
        nssiBaseRepository.delete(nssiBaseRecord);
        log.debug("NSSI ID " + nssiId.toString() + " removed from DB.");
    }

    public synchronized void nssiSetError(UUID nssiId, NssiErrors error, String description) throws NotExistingEntityException {
        NssiBaseRecord nssiBaseRecord = getNssi(nssiId);
        Map<NssiErrors, String> errors = nssiBaseRecord.getErrors();
        errors.put(error, description);
        nssiBaseRecord.setErrors(errors);
        nssiBaseRepository.saveAndFlush(nssiBaseRecord);
        log.debug("NSSI: " + nssiId.toString() + ", error stored: " + error.toString() + " " + description);
    }
}
