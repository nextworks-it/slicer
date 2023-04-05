package it.nextworks.nfvmano.nssmf.ran.nssmanagement;

import it.nextworks.nfvmano.libs.vs.common.utils.BaseRestClient;
import it.nextworks.nfvmano.sbi.cnc.GNbConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class NssmfCoreClient{
    private BaseRestClient baseRestClient;
    private String urlNssmfCore;

    private final static Logger LOG = LoggerFactory.getLogger(NssmfCoreClient.class);


    public NssmfCoreClient(String urlNssmfCore){
        baseRestClient = new BaseRestClient();
        this.urlNssmfCore = urlNssmfCore;
    }


    public boolean configureRan(String ipAddressRAN){

        GNbConfiguration gNbConfiguration = new GNbConfiguration();
        LOG.info("Setting emulated gNB information. gNB name, coverage, lat and long are fixed.");
        gNbConfiguration.setgNB_IPv4(ipAddressRAN);
        gNbConfiguration.setgNB_name("gNB emulated");
        gNbConfiguration.setCoverageArea("16512");
        gNbConfiguration.setLatitude(Float.valueOf(56.7632f));
        gNbConfiguration.setLongitude(Float.valueOf(10.4456f));

        LOG.info("Setting request for configuring gNB.");
        ResponseEntity<String> responseEntity= baseRestClient.performHTTPRequest(gNbConfiguration, urlNssmfCore, HttpMethod.POST);
        responseEntity.getStatusCode();
        responseEntity.getStatusCodeValue();

        return responseEntity.getStatusCode() == HttpStatus.OK;
    }
}
