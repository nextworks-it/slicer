package it.nextworks.nfvmano.sebastian.nste2eComposer.IM;

import java.util.ArrayList;
import java.util.List;


public class Bucket {
    private BucketType bucketType;
    private List<String> nstIdList;

    public Bucket(BucketType bucketType){
        this.bucketType=bucketType;
        nstIdList=new ArrayList<>();
    }

    public BucketType getBucketType() {
        return bucketType;
    }

    public List<String> getNstId() {
        return nstIdList;
    }

    public void addNstId(String nstId){
        nstIdList.add(nstId);
    }

}
