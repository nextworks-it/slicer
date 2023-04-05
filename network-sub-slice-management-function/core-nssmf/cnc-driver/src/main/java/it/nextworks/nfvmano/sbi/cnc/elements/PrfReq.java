package it.nextworks.nfvmano.sbi.cnc.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrfReq{
    @JsonProperty("perfReqEmbbList")
    public PerfReqEmbbList perfReqEmbbList;
    @JsonProperty("perfReqUrllcList")
    public PerfReqUrllcList perfReqUrllcList;

    public PrfReq(){
        perfReqEmbbList= new PerfReqEmbbList();
        perfReqUrllcList = new PerfReqUrllcList();
    }
}