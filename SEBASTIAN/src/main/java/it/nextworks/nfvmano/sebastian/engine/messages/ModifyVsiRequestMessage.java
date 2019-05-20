package it.nextworks.nfvmano.sebastian.engine.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.ModifyVsRequest;

public class ModifyVsiRequestMessage extends EngineMessage {

    @JsonProperty("vsiId")
    private String vsiId;

    @JsonProperty("request")
    private ModifyVsRequest request;

    /**
     * Constructor
     *
     * @param vsiId ID of the VS to be instantiated
     * @param request VSI modification request
     */
    @JsonCreator
    public ModifyVsiRequestMessage(@JsonProperty("vsiId") String vsiId,
                                   @JsonProperty("request") ModifyVsRequest request) {
        this.type = EngineMessageType.MODIFY_VSI_REQUEST;
        this.vsiId = vsiId;
        this.request = request;
    }

    /**
     * @return the vsiId
     */
    public String getVsiId() {
        return vsiId;
    }

    /**
     * @return the request
     */
    public ModifyVsRequest getRequest() {
        return request;
    }
}
