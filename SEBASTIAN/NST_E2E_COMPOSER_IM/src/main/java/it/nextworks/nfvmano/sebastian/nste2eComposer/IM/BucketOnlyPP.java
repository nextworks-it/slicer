package it.nextworks.nfvmano.sebastian.nste2eComposer.IM;

import javax.persistence.Entity;
@Entity
public class BucketOnlyPP extends Bucket{


    public BucketOnlyPP(){
        super(BucketType.PP, BucketScenario.ONLY_PP);
    }
}
