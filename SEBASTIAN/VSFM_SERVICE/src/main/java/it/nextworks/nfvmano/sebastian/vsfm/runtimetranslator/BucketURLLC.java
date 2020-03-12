package it.nextworks.nfvmano.sebastian.vsfm.runtimetranslator;

import it.nextworks.nfvmano.libs.ifa.templates.URLLCPerfReq;

public class BucketURLLC extends Bucket{

    private URLLCPerfReq urllcPerfReq;

    public BucketURLLC(BucketType bucketType,URLLCPerfReq urllcPerfReq) {
        super(bucketType);
        this.urllcPerfReq=urllcPerfReq;

    }

    boolean areRequirementsSatisfied(URLLCPerfReq externalUrllcPerfReq){
        if(externalUrllcPerfReq.getConnDensity()<urllcPerfReq.getConnDensity())
            return false;
        if(externalUrllcPerfReq.getcSAvailability()<urllcPerfReq.getcSAvailability())
            return false;
        if(externalUrllcPerfReq.getE2eLatency()>urllcPerfReq.getE2eLatency())
            return false;
        if(externalUrllcPerfReq.getExpDataRate()<urllcPerfReq.getExpDataRate())
            return false;
        //if(externalUrllcPerfReq.getJitter()<urllcPerfReq.getJitter())
          //  return false;
        //if(externalUrllcPerfReq.getPayloadSize().equals(urllcPerfReq.getPayloadSize()))
          //  return false;
        if(externalUrllcPerfReq.getReliability()<urllcPerfReq.getReliability())
            return false;
        //if(!externalUrllcPerfReq.getServiceAreaDimension().equals(urllcPerfReq.getServiceAreaDimension()))
         //   return false;
        if(externalUrllcPerfReq.getSurvivalTime()>urllcPerfReq.getSurvivalTime())
            return false;
        if(externalUrllcPerfReq.getTrafficDensity()<urllcPerfReq.getTrafficDensity())
            return false;

        return true;
    }
}
