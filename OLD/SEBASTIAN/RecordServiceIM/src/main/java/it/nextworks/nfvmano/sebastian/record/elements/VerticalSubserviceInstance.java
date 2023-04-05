package it.nextworks.nfvmano.sebastian.record.elements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerticalSubserviceInstance {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;


    @ManyToOne
    @JsonIgnore
    private VerticalServiceInstance verticalServiceInstance;


    private String domainId;
    private String blueprintId;
    private String descriptorId;
    private String instanceId;

    private VerticalServiceStatus verticalServiceStatus;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, String> parameters= new HashMap<>();


    public VerticalSubserviceInstance(){

    }

    public VerticalSubserviceInstance(
                                      String domainId,
                                      String blueprintId,
                                      String descriptorId,
                                      String instanceId,
                                      VerticalServiceStatus verticalServiceStatus,
                                      Map<String, String> parameters) {

        this.domainId = domainId;
        this.blueprintId = blueprintId;
        this.descriptorId = descriptorId;
        this.instanceId = instanceId;
        this.verticalServiceStatus = verticalServiceStatus;
        this.parameters = parameters;
    }

    public String getDomainId() {
        return domainId;
    }

    public String getBlueprintId() {
        return blueprintId;
    }

    public String getDescriptorId() {
        return descriptorId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public VerticalServiceStatus getVerticalServiceStatus() {
        return verticalServiceStatus;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setVerticalServiceStatus(VerticalServiceStatus verticalServiceStatus) {
        this.verticalServiceStatus = verticalServiceStatus;
    }
}
