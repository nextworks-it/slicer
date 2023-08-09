package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SupplementalBootData {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("boot-data-drive")
    private boolean bootDataDrive;

    public boolean isBootDataDrive() {
        return bootDataDrive;
    }

    public void setBootDataDrive(boolean bootDataDrive) {
        this.bootDataDrive = bootDataDrive;
    }

    @Override
    public String toString() {
        return "SupplementalBootData{" +
                "bootDataDrive=" + bootDataDrive +
                '}';
    }
}