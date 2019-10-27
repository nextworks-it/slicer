package it.nextworks.nfvmano.sebastian.admin;

import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;

import java.util.HashMap;
import java.util.Map;

public class MgmtCatalogueUtilities {


    public static Filter buildTenantFilter(String tenantId) {
        //TENANT_ID
        Map<String, String> filterParams = new HashMap<>();
        filterParams.put("TENANT_ID", tenantId);
        return new Filter(filterParams);
    }


}
