package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.ScalingTriggerEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class ScalingConfigAction {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("trigger")
    private ScalingTriggerEnum trigger = null;

    @JsonProperty("vnf-config-primitive-name-ref")
    private String vnfConfigPrimitiveNameRef = null;

    public ScalingConfigAction trigger(ScalingTriggerEnum trigger) {
        this.trigger = trigger;
        return this;
    }

    public ScalingTriggerEnum getTrigger() { return trigger; }

    public void setTrigger(ScalingTriggerEnum trigger) { this.trigger = trigger; }

    public ScalingConfigAction vnfConfigPrimitiveNameRef(String vnfConfigPrimitiveNameRef) {
        this.vnfConfigPrimitiveNameRef = vnfConfigPrimitiveNameRef;
        return this;
    }

    public String getVnfConfigPrimitiveNameRef() { return vnfConfigPrimitiveNameRef; }

    public void setVnfConfigPrimitiveNameRef(String vnfConfigPrimitiveNameRef) {
        this.vnfConfigPrimitiveNameRef = vnfConfigPrimitiveNameRef;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScalingConfigAction scalingConfigAction = (ScalingConfigAction) o;

        return Objects.equals(this.trigger, scalingConfigAction.trigger) &&
                Objects.equals(this.vnfConfigPrimitiveNameRef, scalingConfigAction.vnfConfigPrimitiveNameRef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trigger, vnfConfigPrimitiveNameRef);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class ScalingConfigAction {\n");
        sb.append("    trigger: ").append(toIndentedString(trigger)).append("\n");
        sb.append("    vnfConfigPrimitiveNameRef: ").append(toIndentedString(vnfConfigPrimitiveNameRef)).append("\n");
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
