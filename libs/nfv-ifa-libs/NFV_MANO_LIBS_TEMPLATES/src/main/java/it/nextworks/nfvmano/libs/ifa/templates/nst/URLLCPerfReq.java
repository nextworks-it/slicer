/*
 * Copyright (c) 2021 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.nextworks.nfvmano.libs.ifa.templates.nst;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * See:
 *  3gpp Clause 6.4.1 of TS 28.541 v.16.06.2(PerfReq)
 * and
 *  3ggp Table 5.2-1, Table 5.3-1, Table 5.4-1 and Table 5.5-1 of TS 22.104
 *
 */
@Entity
public class URLLCPerfReq {
/**
 * cSAvailabilityTarget (float), cSReliabilityMeanTime (string), expDataRate (integer),
 * msgSizeByte (string), transferIntervalTarget (string), survivalTime (string)
 */
	@Id
    @GeneratedValue
    @JsonIgnore
	private Long id;
    private float cSAvailabilityTarget;
    //it cannot be mapped from GSMA GST
    private String cSReliabilityMeanTime;
    private int expDataRate;
    private String msgSizeByte;
    //These attributes cannot be mapped from GSMA GST
    private String transferIntervalTarget;
    private String survivalTime;

    public URLLCPerfReq() {
    }

    /**
     *
     * @param cSAvailabilityTarget
     * @param cSReliabilityMeanTime
     * @param expDataRate
     * @param msgSizeByte
     * @param transferIntervalTarget
     * @param survivalTime
     */
    public URLLCPerfReq(float cSAvailabilityTarget, String cSReliabilityMeanTime,
                        int expDataRate, String msgSizeByte, String transferIntervalTarget, String survivalTime ) {
        this.cSAvailabilityTarget = cSAvailabilityTarget;
        this.cSReliabilityMeanTime = cSReliabilityMeanTime;
        this.expDataRate = expDataRate;
        this.msgSizeByte = msgSizeByte;
        this.transferIntervalTarget=transferIntervalTarget;
        this.survivalTime = survivalTime;
    }

    public URLLCPerfReq(URLLCPerfReq urllcPerfReq ) {
        this.cSAvailabilityTarget = urllcPerfReq.cSAvailabilityTarget;
        this.cSReliabilityMeanTime = urllcPerfReq.cSReliabilityMeanTime;
        this.expDataRate = urllcPerfReq.expDataRate;
        this.msgSizeByte = urllcPerfReq.msgSizeByte;
        this.transferIntervalTarget=urllcPerfReq.transferIntervalTarget;
        this.survivalTime = urllcPerfReq.survivalTime;
    }

    public String getSurvivalTime() {
        return survivalTime;
    }

    public void setSurvivalTime(String survivalTime) {
        this.survivalTime = survivalTime;
    }

    public float getCSAvailabilityTarget() {
        return cSAvailabilityTarget;
    }

    public void setCSAvailabilityTarget(float cSAvailabilityTarget) {
        this.cSAvailabilityTarget = cSAvailabilityTarget;
    }

    public String getCSReliabilityMeanTime() {
        return cSReliabilityMeanTime;
    }

    public void setCSReliabilityMeanTime(String cSReliabilityMeanTime) {
        this.cSReliabilityMeanTime = cSReliabilityMeanTime;
    }

    public int getExpDataRate() {
        return expDataRate;
    }

    public void setExpDataRate(int expDataRate) {
        this.expDataRate = expDataRate;
    }

    public String getMsgSizeByte() {
        return msgSizeByte;
    }

    public void setMsgSizeByte(String payloadSize) {
        this.msgSizeByte = payloadSize;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public void setTransferIntervalTarget(String transferIntervalTarget) {
        this.transferIntervalTarget = transferIntervalTarget;
    }

    public String getTransferIntervalTarget() {
        return transferIntervalTarget;
    }
}
