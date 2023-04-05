package it.nextworks.nfvmano.nsmf.ra.algorithms.stat.messages;

import it.nextworks.nfvmano.nsmf.ra.algorithms.stat.record.elements.StaticRaResponseRecord;

public class OnboardStaticRaResponseRequest {
    private StaticRaResponseRecord staticNsiResponse;

    public OnboardStaticRaResponseRequest(){}

    public OnboardStaticRaResponseRequest(StaticRaResponseRecord staticNsiResponse){
        this.staticNsiResponse=staticNsiResponse;
    }

    public StaticRaResponseRecord getStaticNsiResponse() {
        return staticNsiResponse;
    }

    public void setStaticNsiResponse(StaticRaResponseRecord staticNsiResponse) {
        this.staticNsiResponse = staticNsiResponse;
    }
}
