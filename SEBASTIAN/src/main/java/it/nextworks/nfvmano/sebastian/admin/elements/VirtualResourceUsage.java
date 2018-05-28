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
	
	public void addResources(int storage, int cpu, int ram) {
		this.diskStorage += storage;
		this.vCPU += cpu;
		this.memoryRAM += ram;
	}
	
	public void removeResources(int storage, int cpu, int ram) {
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
