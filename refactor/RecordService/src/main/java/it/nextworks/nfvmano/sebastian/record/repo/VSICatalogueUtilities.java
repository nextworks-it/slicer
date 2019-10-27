package it.nextworks.nfvmano.sebastian.record.repo;

import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;

import java.util.HashMap;
import java.util.Map;

public class VSICatalogueUtilities {


    public static Filter buildVsInstanceFilter(String vsiId, String tenantId) {
        //VSI_ID & TENANT_ID
        Map<String, String> filterParams = new HashMap<>();
        filterParams.put("VSI_ID", vsiId);
        filterParams.put("TENANT_ID", tenantId);
        return new Filter(filterParams);
    }
}
