package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class InitialConfigPrimitive {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("seq")
    private String seq = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("execution-environment-ref")
    private String executionEnvironmentRef = null;

    @JsonProperty("parameter")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "initial_config_primitive_fk", referencedColumnName = "uuid")
    private List<InitialConfigPrimitiveParameter> parameter = null;

    @JsonProperty("user-defined-script")
    private String userDefinedScript = null;

    public InitialConfigPrimitive seq(String seq) {
        this.seq = seq;
        return this;
    }

    public String getSeq() { return seq; }

    public void setSeq(String seq) { this.seq = seq; }

    public InitialConfigPrimitive name(String name) {
        this.name = name;
        return this;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public InitialConfigPrimitive executionEnvironmentRef(String executionEnvironmentRef) {
        this.executionEnvironmentRef = executionEnvironmentRef;
        return this;
    }

    public String getExecutionEnvironmentRef() { return executionEnvironmentRef; }

    public void setExecutionEnvironmentRef(String executionEnvironmentRef) {
        this.executionEnvironmentRef = executionEnvironmentRef;
    }

    public InitialConfigPrimitive parameter(List<InitialConfigPrimitiveParameter> parameter) {
        this.parameter = parameter;
        return this;
    }

    public InitialConfigPrimitive addParameterItem(InitialConfigPrimitiveParameter initialConfigPrimitiveParameter) {
        if(this.parameter == null)
            this.parameter = new ArrayList<>();

        this.parameter.add(initialConfigPrimitiveParameter);
        return this;
    }

    public List<InitialConfigPrimitiveParameter> getParameter() { return parameter; }

    public void setParameter(List<InitialConfigPrimitiveParameter> parameter) { this.parameter = parameter; }

    public InitialConfigPrimitive userDefinedScript(String userDefinedScript) {
        this.userDefinedScript = userDefinedScript;
        return this;
    }

    public String getUserDefinedScript() { return userDefinedScript; }

    public void setUserDefinedScript(String userDefinedScript) { this.userDefinedScript = userDefinedScript; }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InitialConfigPrimitive initialConfigPrimitive = (InitialConfigPrimitive) o;

        return Objects.equals(this.seq, initialConfigPrimitive.seq) &&
                Objects.equals(this.name, initialConfigPrimitive.name) &&
                Objects.equals(this.executionEnvironmentRef, initialConfigPrimitive.executionEnvironmentRef) &&
                Objects.equals(this.parameter, initialConfigPrimitive.parameter) &&
                Objects.equals(this.userDefinedScript, initialConfigPrimitive.userDefinedScript);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq, name, executionEnvironmentRef, parameter, userDefinedScript);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class InitialConfigPrimitive {\n");
        sb.append("    seq: ").append(toIndentedString(seq)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    executionEnvironmentRef: ").append(toIndentedString(executionEnvironmentRef)).append("\n");
        sb.append("    parameter: ").append(toIndentedString(parameter)).append("\n");
        sb.append("    userDefinedScript: ").append(toIndentedString(userDefinedScript)).append("\n");
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
