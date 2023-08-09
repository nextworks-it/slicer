package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.DataTypeEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class InitialConfigPrimitiveParameter {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("data-type")
    private DataTypeEnum dataType = null;

    @JsonProperty("value")
    private String value = null;

    public InitialConfigPrimitiveParameter name(String name) {
        this.name = name;
        return this;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public InitialConfigPrimitiveParameter dataType(DataTypeEnum dataType) {
        this.dataType = dataType;
        return this;
    }

    public DataTypeEnum getDataType() { return dataType; }

    public void setDataType(DataTypeEnum dataType) { this.dataType = dataType; }

    public InitialConfigPrimitiveParameter value(String value) {
        this.value = value;
        return this;
    }

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InitialConfigPrimitiveParameter initialConfigPrimitiveParameter = (InitialConfigPrimitiveParameter) o;

        return Objects.equals(this.name, initialConfigPrimitiveParameter.name) &&
                Objects.equals(this.dataType, initialConfigPrimitiveParameter.dataType) &&
                Objects.equals(this.value, initialConfigPrimitiveParameter.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dataType, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class InitialConfigPrimitiveParameter {\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
        sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
