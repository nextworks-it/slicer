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
package it.nextworks.nfvmano.libs.ifa.monit.interfaces.elements;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element defines the format of a performance report 
 * provided by the producer to the consumer on a specified object instance 
 * or a set of them. The object instances for this information element will 
 * be virtualised resources.
 * 
 * REF IFA 005 v2.1.1 - 8.5.5
 * REF IFA 013 v2.3.1 - 8.4.5
 * 
 * @author nextworks
 *
 */
public class PerformanceReport implements InterfaceInformationElement {

	private List<PerformanceReportEntry> performanceReportEntry = new ArrayList<>();
	
	public PerformanceReport() { }
	
	/**
	 * Constructor
	 * 
	 * @param performanceReportEntry List of performance information entries.
	 */
	public PerformanceReport(List<PerformanceReportEntry> performanceReportEntry) { 
		if (performanceReportEntry != null) this.performanceReportEntry = performanceReportEntry;
	}
	
	

	/**
	 * @return the performanceReportEntry
	 */
	public List<PerformanceReportEntry> getPerformanceReportEntry() {
		return performanceReportEntry;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if ( (performanceReportEntry == null) || (performanceReportEntry.isEmpty())) throw new MalformattedElementException("Performance report without entries");
		else {
			for (PerformanceReportEntry e : performanceReportEntry) e.isValid();
		}
	}

}
