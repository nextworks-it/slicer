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
public class NsConfiguration {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("script")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "script_fk", referencedColumnName = "uuid")
    private Script script = null;

    @JsonProperty("juju")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "juju_fk", referencedColumnName = "uuid")
    private Juju juju = null;

    @JsonProperty("execution-environment-list")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "ns_configuration_fk", referencedColumnName = "uuid")
    private List<ExecutionEnvironmentList> executionEnvironmentList = null;

    @JsonProperty("config-primitive")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "ns_configuration_fk", referencedColumnName = "uuid")
    private List<ConfigPrimitive> configPrimitive = null;

    @JsonProperty("initial-config-primitive")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "ns_configuration_fk", referencedColumnName = "uuid")
    private List<InitialConfigPrimitive> initialConfigPrimitive = null;

    @JsonProperty("terminate-config-primitive")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "ns_configuration_fk", referencedColumnName = "uuid")
    private List<TerminateConfigPrimitive> terminateConfigPrimitive = null;

    @JsonProperty("metrics")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "ns_configuration_fk", referencedColumnName = "uuid")
    private List<Metrics> metrics = null;

    @JsonProperty("relation")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "ns_configuration_fk", referencedColumnName = "uuid")
    private List<Relation> relation = null;

    public NsConfiguration script(Script script) {
        this.script = script;
        return this;
    }

    public Script getScript() { return script; }

    public void setScript(Script script) { this.script = script; }

    public NsConfiguration juju(Juju juju) {
        this.juju = juju;
        return this;
    }

    public Juju getJuju() { return juju; }

    public void setJuju(Juju juju) { this.juju = juju; }

    public NsConfiguration executionEnvironmentList(List<ExecutionEnvironmentList> executionEnvironmentList) {
        this.executionEnvironmentList = executionEnvironmentList;
        return this;
    }

    public NsConfiguration addExecutionEnvironmentListItem(ExecutionEnvironmentList executionEnvironmentList) {
        if(this.executionEnvironmentList == null)
            this.executionEnvironmentList = new ArrayList<>();

        this.executionEnvironmentList.add(executionEnvironmentList);
        return this;
    }

    public List<ExecutionEnvironmentList> getExecutionEnvironmentList() { return executionEnvironmentList; }

    public void setExecutionEnvironmentList(List<ExecutionEnvironmentList> executionEnvironmentList) {
        this.executionEnvironmentList = executionEnvironmentList;
    }

    public NsConfiguration configPrimitive(List<ConfigPrimitive> configPrimitive) {
        this.configPrimitive = configPrimitive;
        return this;
    }

    public NsConfiguration addConfigPrimitiveItem(ConfigPrimitive configPrimitive) {
        if(this.configPrimitive == null)
            this.configPrimitive = new ArrayList<>();

        this.configPrimitive.add(configPrimitive);
        return this;
    }

    public List<ConfigPrimitive> getConfigPrimitive() { return configPrimitive; }

    public void setConfigPrimitive(List<ConfigPrimitive> configPrimitive) { this.configPrimitive = configPrimitive; }

    public NsConfiguration initialConfigPrimitive(List<InitialConfigPrimitive> initialConfigPrimitive) {
        this.initialConfigPrimitive = initialConfigPrimitive;
        return this;
    }

    public NsConfiguration addInitialConfigPrimitiveItem(InitialConfigPrimitive initialConfigPrimitive) {
        if(this.initialConfigPrimitive == null)
            this.initialConfigPrimitive = new ArrayList<>();

        this.initialConfigPrimitive.add(initialConfigPrimitive);
        return this;
    }

    public List<InitialConfigPrimitive> getInitialConfigPrimitive() { return initialConfigPrimitive; }

    public void setInitialConfigPrimitive(List<InitialConfigPrimitive> initialConfigPrimitive) {
        this.initialConfigPrimitive = initialConfigPrimitive;
    }

    public NsConfiguration terminateConfigPrimitive(List<TerminateConfigPrimitive> terminateConfigPrimitive) {
        this.terminateConfigPrimitive = terminateConfigPrimitive;
        return this;
    }

    public NsConfiguration addTerminateConfigPrimitiveItem(TerminateConfigPrimitive terminateConfigPrimitive) {
        if(this.terminateConfigPrimitive == null)
            this.terminateConfigPrimitive = new ArrayList<>();

        this.terminateConfigPrimitive.add(terminateConfigPrimitive);
        return this;
    }

    public List<TerminateConfigPrimitive> getTerminateConfigPrimitive() { return terminateConfigPrimitive; }

    public void setTerminateConfigPrimitive(List<TerminateConfigPrimitive> terminateConfigPrimitive) {
        this.terminateConfigPrimitive = terminateConfigPrimitive;
    }

    public NsConfiguration metrics(List<Metrics> metrics) {
        this.metrics = metrics;
        return this;
    }

    public NsConfiguration addMetricsItem(Metrics metric) {
        if(this.metrics == null)
            this.metrics = new ArrayList<>();

        this.metrics.add(metric);
        return this;
    }

    public List<Metrics> getMetrics() { return metrics; }

    public void setMetrics(List<Metrics> metrics) { this.metrics = metrics; }

    public NsConfiguration relation(List<Relation> relation) {
        this.relation = relation;
        return this;
    }

    public NsConfiguration addRelationItem(Relation relation) {
        if(this.relation == null)
            this.relation = new ArrayList<>();

        this.relation.add(relation);
        return this;
    }

    public List<Relation> getRelation() { return relation; }

    public void setRelation(List<Relation> relation) { this.relation = relation; }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NsConfiguration nsConfiguration = (NsConfiguration) o;

        return Objects.equals(this.script, nsConfiguration.script) &&
                Objects.equals(this.juju, nsConfiguration.juju) &&
                Objects.equals(this.executionEnvironmentList, nsConfiguration.executionEnvironmentList) &&
                Objects.equals(this.configPrimitive, nsConfiguration.configPrimitive) &&
                Objects.equals(this.initialConfigPrimitive, nsConfiguration.initialConfigPrimitive) &&
                Objects.equals(this.terminateConfigPrimitive, nsConfiguration.terminateConfigPrimitive) &&
                Objects.equals(this.metrics, nsConfiguration.metrics) &&
                Objects.equals(this.relation, nsConfiguration.relation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(script, juju, executionEnvironmentList, configPrimitive, initialConfigPrimitive,
                terminateConfigPrimitive, metrics, relation);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class NsConfiguration {\n");
        sb.append("    script: ").append(toIndentedString(script)).append("\n");
        sb.append("    juju: ").append(toIndentedString(juju)).append("\n");
        sb.append("    executionEnvironmentList: ").append(toIndentedString(executionEnvironmentList)).append("\n");
        sb.append("    configPrimitive: ").append(toIndentedString(configPrimitive)).append("\n");
        sb.append("    initialConfigPrimitive: ").append(toIndentedString(initialConfigPrimitive)).append("\n");
        sb.append("    terminateConfigPrimitive: ").append(toIndentedString(terminateConfigPrimitive)).append("\n");
        sb.append("    metrics: ").append(toIndentedString(metrics)).append("\n");
        sb.append("    relation: ").append(toIndentedString(relation)).append("\n");
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
