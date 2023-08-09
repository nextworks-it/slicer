package it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.elements;

import java.util.ArrayList;
import java.util.List;

public class SoVnfQueryResponse {
    private List<SoVnfInfoObject> queryResult = new ArrayList<>();

    public List<SoVnfInfoObject> getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(List<SoVnfInfoObject> queryResult) {
        this.queryResult = queryResult;
    }
}
