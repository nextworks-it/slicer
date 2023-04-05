package it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SonataRequest {

    @JsonProperty("request_type")
    private SonataRequestType requestType;

    public SonataRequest(SonataRequestType requestType) {
        this.requestType = requestType;
    }

    public SonataRequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(SonataRequestType requestType) {
        this.requestType = requestType;
    }
}
