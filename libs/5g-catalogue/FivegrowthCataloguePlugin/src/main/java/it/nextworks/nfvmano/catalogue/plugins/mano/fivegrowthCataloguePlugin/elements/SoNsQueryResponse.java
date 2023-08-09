package it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.elements;

import java.util.ArrayList;
import java.util.List;

public class SoNsQueryResponse {

    private List<SoNsInfoObject> queryResult = new ArrayList<>();

    public List<SoNsInfoObject> getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(List<SoNsInfoObject> queryResult) {
        this.queryResult = queryResult;
    }
}
