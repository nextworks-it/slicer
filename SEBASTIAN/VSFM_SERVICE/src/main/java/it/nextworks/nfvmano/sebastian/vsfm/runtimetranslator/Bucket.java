package it.nextworks.nfvmano.sebastian.vsfm.runtimetranslator;

import it.nextworks.nfvmano.libs.ifa.templates.EMBBPerfReq;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import it.nextworks.nfvmano.libs.ifa.templates.URLLCPerfReq;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


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
