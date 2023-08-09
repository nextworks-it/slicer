package it.nextworks.nfvmano.catalogue.plugins.siteInventory;

import it.nextworks.nfvmano.catalogue.plugins.siteInventory.model.NfvOrchestrator;
import it.nextworks.nfvmano.catalogue.plugins.siteInventory.model.Site;
import it.nextworks.nfvmano.catalogue.plugins.siteInventory.model.SiteInventoryNfvoResponse;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.util.List;

public class SiteInventoryDriver {

    private static final Logger log = LoggerFactory.getLogger(SiteInventoryDriver.class);

    private String baseUrl;

    private RestTemplate restTemplate;

    public SiteInventoryDriver(String url) {
        baseUrl = url;
        this.restTemplate= new RestTemplate(new BufferingClientHttpRequestFactory(
                new SimpleClientHttpRequestFactory()
        ));
    }

    public List<NfvOrchestrator> getNFVOs() throws FailedOperationException{
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        HttpEntity<?> getEntity = new HttpEntity<>(headers);
        List<NfvOrchestrator> nfvOrchestratorList = null;
        try {
            String nfvoUrl = String.format("%s/nfvOrchestrators", baseUrl);
            ResponseEntity<SiteInventoryNfvoResponse> httpResponseNfvo = restTemplate.exchange(nfvoUrl, HttpMethod.GET, getEntity, SiteInventoryNfvoResponse.class);
            if(httpResponseNfvo.getStatusCode() == HttpStatus.OK) {
                nfvOrchestratorList = httpResponseNfvo.getBody().getWrapper().getNfvOrchestratorList();
                if (nfvOrchestratorList != null) {
                    for (NfvOrchestrator nfvo : nfvOrchestratorList) {
                        if(nfvo.getId() == null)
                            throw new FailedOperationException("Error during Nfvo information retrieval: Nfvo without id");
                        String url = String.format("%s/%s/site", nfvoUrl, nfvo.getId());
                        ResponseEntity<Site> httpResponseSite = restTemplate.exchange(url, HttpMethod.GET, getEntity, Site.class);
                        if (httpResponseSite.getStatusCode() == HttpStatus.OK)
                            nfvo.setSite(httpResponseSite.getBody());
                    }
                }
                return nfvOrchestratorList;
            } else
                return null;
        }catch (HttpClientErrorException e) {
            throw new FailedOperationException("Error during Nfvo information retrieval: Client error");
        } catch (HttpServerErrorException e) {
            throw new FailedOperationException("Error during Nfvo information retrieval: Server error");
        } catch (UnknownHttpStatusCodeException e) {
            throw new FailedOperationException("Error during Nfvo information retrieval: Unknown error");
        } catch (Exception e) {
            throw new FailedOperationException("Generic error while interacting with Site Inventory instance at url: " + baseUrl);
        }
    }
}
