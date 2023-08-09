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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * See:
 *  3gpp Clause 6.4.1 of TS 28.541 v. 16.06.2(PerfReq)
 * and
 *  3ggp Table 7.1-1 of TS 22.261
 *
 * NOTE: scenario examples present in table 7.1-1 have not been reported in the implementation
 *        as suggested by TS 28.541
 */
@Entity
public class EMBBPerfReq{

	@Id
    @GeneratedValue
    @JsonIgnore
	private Long id;
    private int expDataRateDL;
    private int expDataRateUL;
    private int areaTrafficCapDL;
    private int areaTrafficCapUL;
    private int overallUserDensity;
    //it cannot be mapped from GSMA GST
    private int activityFactor;

    public EMBBPerfReq() { }

    /**
     *
     * @param expDataRateDL
     * @param expDataRateUL
     * @param areaTrafficCapDL
     * @param areaTrafficCapUL
     * @param overallUserDensity
     * @param activityFactor
     */
    public EMBBPerfReq(int expDataRateDL, int expDataRateUL, int areaTrafficCapDL, int areaTrafficCapUL,
                       int overallUserDensity, int activityFactor) {
        this.expDataRateDL = expDataRateDL;
        this.expDataRateUL = expDataRateUL;
        this.areaTrafficCapDL = areaTrafficCapDL;
        this.areaTrafficCapUL = areaTrafficCapUL;
        this.overallUserDensity = overallUserDensity;
        this.activityFactor = activityFactor;
    }

    public EMBBPerfReq(EMBBPerfReq embbPerfReq) {
        this.expDataRateDL = embbPerfReq.expDataRateDL;
        this.expDataRateUL = embbPerfReq.expDataRateUL;
        this.areaTrafficCapDL = embbPerfReq.areaTrafficCapDL;
        this.areaTrafficCapUL = embbPerfReq.areaTrafficCapUL;
        this.overallUserDensity = embbPerfReq.overallUserDensity;
        this.activityFactor = embbPerfReq.activityFactor;
    }

    public int getExpDataRateDL() {
        return expDataRateDL;
    }

    public void setExpDataRateDL(int expDataRateDL) {
        this.expDataRateDL = expDataRateDL;
    }

    public int getExpDataRateUL() {
        return expDataRateUL;
    }

    public void setExpDataRateUL(int expDataRateUL) {
        this.expDataRateUL = expDataRateUL;
    }

    public int getAreaTrafficCapDL() {
        return areaTrafficCapDL;
    }

    public void setAreaTrafficCapDL(int areaTrafficCapDL) {
        this.areaTrafficCapDL = areaTrafficCapDL;
    }

    public int getAreaTrafficCapUL() {
        return areaTrafficCapUL;
    }

    public void setAreaTrafficCapUL(int areaTrafficCapUL) {
        this.areaTrafficCapUL = areaTrafficCapUL;
    }

    public int getOverallUserDensity() {
        return overallUserDensity;
    }

    public void setOverallUserDensity(int overallUserDensity) {
        this.overallUserDensity = overallUserDensity;
    }

    public int getActivityFactor() {
        return activityFactor;
    }

    public void setActivityFactor(int activityFactor) {
        this.activityFactor = activityFactor;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
