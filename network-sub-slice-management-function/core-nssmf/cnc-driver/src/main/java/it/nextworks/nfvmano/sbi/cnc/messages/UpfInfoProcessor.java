package it.nextworks.nfvmano.sbi.cnc.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.sbi.cnc.dnnList.DnnList;
import it.nextworks.nfvmano.sbi.cnc.dnnList.SNssai;
import it.nextworks.nfvmano.sbi.cnc.dnnList.SNssaiUpfInfoList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpfInfoProcessor {



    public Map<String, UpfMainInfo> parseFromResponse(String response) throws JsonProcessingException {
        Map map = new HashMap();
        ObjectMapper om = new ObjectMapper();
        DnnListCnc dnnListCnc = om.readValue(response, DnnListCnc.class);

        for(DnnList dnnList: dnnListCnc.dnnList){
            String nfIp = dnnList.ipv4Addresses.get(0);
            String nfUuid = dnnList.nfInstanceId;
            String nfInstanceName = dnnList.nfInstanceName;
            List<SNssai> sNssaiList = new ArrayList();
            for(SNssaiUpfInfoList sNssaiUpfInfoList: dnnList.upfInfo.sNssaiUpfInfoList){

                SNssai sNssai = new SNssai();
                sNssai.sst = sNssaiUpfInfoList.sNssai.sst;
                sNssai.sd = sNssaiUpfInfoList.sNssai.sd;
                sNssaiList.add(sNssai);
            }
            UpfMainInfo upfMainInfo = new UpfMainInfo(nfUuid, nfInstanceName, nfIp, sNssaiList);
            map.put(nfUuid, upfMainInfo);
        }
        return map;
    }
}
