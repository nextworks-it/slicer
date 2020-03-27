package it.nextworks.nfvmano.sebastian.vsfm.engine.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.sebastian.common.ActuationRequest;

public class ActauteNsiMessage extends VsmfEngineMessage{

    @JsonProperty("actuationRequest")
    private ActuationRequest actuationRequest;

    public ActauteNsiMessage(){}
    public ActauteNsiMessage(@JsonProperty("actuationRequest")ActuationRequest actuationRequest){
        this.type = VsmfEngineMessageType.ACTUATION_REQUEST;
        this.actuationRequest=actuationRequest;
    }

    public ActuationRequest getActuationRequest() {
        return actuationRequest;
    }

    public void setActuationRequest(ActuationRequest actuationRequest) {
        this.actuationRequest = actuationRequest;
    }

}
