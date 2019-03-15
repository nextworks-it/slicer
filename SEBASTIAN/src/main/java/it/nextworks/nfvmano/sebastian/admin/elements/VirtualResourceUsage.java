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
package it.nextworks.nfvmano.sebastian.admin.elements;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable
public class VirtualResourceUsage {

	private int diskStorage;	//in GB
	private int vCPU;
	private int memoryRAM;		//in MB
	
	public VirtualResourceUsage() {	}
	
	public VirtualResourceUsage(int diskStorage,
			int vCPU,
			int memoryRAM) {
		this.diskStorage = diskStorage;
		this.vCPU = vCPU;
		this.memoryRAM = memoryRAM;
	}

	/**
	 * @return the diskStorage
	 */
	public int getDiskStorage() {
		return diskStorage;
	}

	/**
	 * @return the vCPU
	 */
	public int getvCPU() {
		return vCPU;
	}

	/**
	 * @return the memoryRAM
	 */
	public int getMemoryRAM() {
		return memoryRAM;
	}

	public void addResources(VirtualResourceUsage other) {
		addResources(other.getDiskStorage(), other.getvCPU(), other.getMemoryRAM());
	}
	
	private void addResources(int storage, int cpu, int ram) {
		this.diskStorage += storage;
		this.vCPU += cpu;
		this.memoryRAM += ram;
	}

	public void removeResources(VirtualResourceUsage other) {
		removeResources(other.getDiskStorage(), other.getvCPU(), other.getMemoryRAM());
	}

	private void removeResources(int storage, int cpu, int ram) {
		this.diskStorage -= storage;
		this.vCPU -= cpu;
		this.memoryRAM -= ram;
	}
	
	@JsonIgnore
	public String toString() {
		String s = "Disk: " + diskStorage + "; vCPU: " + vCPU + "; RAM: " + memoryRAM;
		return s;
	}

}
