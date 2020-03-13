package it.nextworks.nfvmano.sebastian.nste2eComposer.IM;

import it.nextworks.nfvmano.libs.ifa.templates.EMBBPerfReq;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class BucketEMBB extends Bucket{

    @OneToOne(cascade= CascadeType.ALL)
    private EMBBPerfReq embbPerfReq;

    public BucketEMBB(){
        //FOR JPA Only
    }
    public BucketEMBB(BucketScenario bucketScenario, EMBBPerfReq embbPerfReq) {
        super(BucketType.EMBB, bucketScenario);
        this.embbPerfReq=embbPerfReq;
    }

    public boolean areRequirementsSatisfied(EMBBPerfReq externalEmbbPerfReq){
        if(externalEmbbPerfReq.getActivityFactor()<embbPerfReq.getActivityFactor())
            return false;

        if(externalEmbbPerfReq.getAreaTrafficCapDL()<embbPerfReq.getAreaTrafficCapDL())
            return false;

        if(externalEmbbPerfReq.getAreaTrafficCapUL()<embbPerfReq.getAreaTrafficCapUL())
            return false;

        //if(!externalEmbbPerfReq.getCoverage().equals(embbPerfReq.getCoverage()))
         //   return false;

        if(externalEmbbPerfReq.getExpDataRateDL()<embbPerfReq.getExpDataRateDL())
            return false;

        if(externalEmbbPerfReq.getExpDataRateUL()<embbPerfReq.getExpDataRateUL())
            return false;

        if(externalEmbbPerfReq.getuESpeed()<embbPerfReq.getuESpeed())
            return false;

        if(externalEmbbPerfReq.getUserDensity()<embbPerfReq.getUserDensity())
            return false;

        return true;
    }

}
