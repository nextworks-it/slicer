package it.nextworks.nfvmano.sebastian.nstE2Ecomposer.messages;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

import java.util.List;

public class NstAdvertisementUpdate implements InterfaceMessage {

    private String nstUuid;
    private List<String> kpiList;

    public NstAdvertisementUpdate(String nstUuid, List<String> kpiList){
        this.nstUuid=nstUuid;
        this.kpiList=kpiList;
    }

    @Override
    public void isValid() throws MalformattedElementException {
            if(kpiList==null) throw new MalformattedElementException("kpi list is null");
        if(nstUuid==null || nstUuid.isEmpty()) throw new MalformattedElementException("Nst UUID is either empty or null.");
    }

    public String getNstUuid() {
        return nstUuid;
    }

    public List<String> getKpiList() {
        return kpiList;
    }

}
