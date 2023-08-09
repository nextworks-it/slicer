package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class SupplementalBootData {

    @JsonProperty("boot-data-drive")
    private Boolean bootDataDrive = null;

    public SupplementalBootData bootDataDrive(Boolean bootDataDrive) {
        this.bootDataDrive = bootDataDrive;
        return this;
    }

    public Boolean getBootDataDrive() { return bootDataDrive; }

    public void setBootDataDrive(Boolean bootDataDrive) { this.bootDataDrive = bootDataDrive; }

    @Override
    public boolean equals(Object o) {

        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        SupplementalBootData supplementalBootData = (SupplementalBootData) o;

        return Objects.equals(this.bootDataDrive, supplementalBootData.bootDataDrive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bootDataDrive);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class SupplementalBootData {\n");
        sb.append("    bootDataDrive: ").append(toIndentedString(bootDataDrive)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
