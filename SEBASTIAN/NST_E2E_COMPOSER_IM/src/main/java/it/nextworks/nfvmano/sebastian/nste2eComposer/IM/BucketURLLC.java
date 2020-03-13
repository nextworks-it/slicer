package it.nextworks.nfvmano.sebastian.nste2eComposer.IM;

import it.nextworks.nfvmano.libs.ifa.templates.URLLCPerfReq;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class BucketURLLC extends Bucket{

    @OneToOne(cascade= CascadeType.ALL)
    private URLLCPerfReq urllcPerfReq;

    public BucketURLLC(){
        //FOR JPA
    }
    public BucketURLLC(BucketScenario bucketScenario, URLLCPerfReq urllcPerfReq) {
        super(BucketType.URLLC, bucketScenario);
        this.urllcPerfReq=urllcPerfReq;

    }

    public boolean areRequirementsSatisfied(URLLCPerfReq externalUrllcPerfReq){
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
