package it.nextworks.nfvmano.sebastian.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

import java.util.Map;

public class ActuationRequest {

    @JsonProperty("nsiId")
    private String nsiId;
    @JsonProperty("actuationName")
    private String actuationName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("parameters")
    private Map<String, Object> parameters;
    @JsonProperty("notificationEndpoint")
    private String notificationEndpoint;

    public ActuationRequest() {
    }

    @JsonCreator
    public ActuationRequest(@JsonProperty("nsiId") String nsiId,
                            @JsonProperty("actuationName") String actuationName,
                            @JsonProperty("description") String description,
                            @JsonProperty("parameters") Map<String, Object> parameters,
                            @JsonProperty("notificationEndpoint") String notificationEndpoint
                            ) {
        this.nsiId = nsiId;
        this.actuationName = actuationName;
        this.description = description;
        this.parameters = parameters;
        this.notificationEndpoint = notificationEndpoint;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getNotificationEndpoint() {
        return notificationEndpoint;
    }

    public void setNotificationEndpoint(String notificationEndPoint) {
        this.notificationEndpoint = notificationEndPoint;
    }

    public void isValid() throws MalformattedElementException {
        if(nsiId == null)
            throw new MalformattedElementException("Actuation request without Network Slice ID.");
        if (actuationName == null)
            throw new MalformattedElementException("Actuation request without actuation name.");

        if (description == null)
            throw new MalformattedElementException("Actuation request without description.");

        if (parameters == null)
            throw new MalformattedElementException("Actuation request without parameters.");

        if (parameters.size() == 0)
            throw new MalformattedElementException("Actuation request has zero parameters.");

        if (notificationEndpoint == null)
            throw new MalformattedElementException("Actuation request has no notification endpoint.");
    }

    public String getActuationName() {
        return actuationName;
    }

    public void setActuationName(String actuationName) {
        this.actuationName = actuationName;
    }

    public String getNsiId() {
        return nsiId;
    }

    public void setNsiId(String nsiId) {
        this.nsiId = nsiId;
    }
}
