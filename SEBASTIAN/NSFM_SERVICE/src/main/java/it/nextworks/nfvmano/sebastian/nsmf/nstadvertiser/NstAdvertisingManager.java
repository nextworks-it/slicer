package it.nextworks.nfvmano.sebastian.nsmf.nstadvertiser;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import it.nextworks.nfvmano.sebastian.nsmf.nbi.VsmfNstAdvertiserRestClient;
import it.nextworks.nfvmano.sebastian.nstE2Ecomposer.messages.NstAdvertisementRemoveRequest;
import it.nextworks.nfvmano.sebastian.nstE2Ecomposer.messages.NstAdvertisementRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;

import java.util.concurrent.BlockingQueue;

public class NstAdvertisingManager implements Runnable {

    private final BlockingQueue<Object> requests;
    private VsmfNstAdvertiserRestClient vsmfNstAdvertiserRestClient;
    private final int MAX_TIMEOUT=60000;
    private static final Logger log = LoggerFactory.getLogger(NstAdvertisingManager.class);

    public NstAdvertisingManager(VsmfNstAdvertiserRestClient vsmfNstAdvertiserRestClient,
                                 BlockingQueue<Object> requests) {
        this.requests=requests;
        this.vsmfNstAdvertiserRestClient=vsmfNstAdvertiserRestClient;
    }


    private void processRequest(Object request) throws FailedOperationException, InterruptedException {
            if(request instanceof NstAdvertisementRequest){

                NstAdvertisementRequest nstAdvertisementRequest = (NstAdvertisementRequest) request;
                log.info("Going to advertise NST with UUID "+nstAdvertisementRequest.getNst().getNstId()+". Domain name inserted is "+nstAdvertisementRequest.getDomainName());
                vsmfNstAdvertiserRestClient.advertiseNst(nstAdvertisementRequest);
            }
            else if(request instanceof NstAdvertisementRemoveRequest){
                NstAdvertisementRemoveRequest nstAdvertisementRemoveRequest = (NstAdvertisementRemoveRequest)request;
                log.info("Going to remove advertising of NST with UUID "+nstAdvertisementRemoveRequest.getNstId()+" Domain name inserted is "+nstAdvertisementRemoveRequest.getDomainName());
                vsmfNstAdvertiserRestClient.removeNstAdvertised(nstAdvertisementRemoveRequest);
            }
            else{
                log.warn("Cannot process the request.");
            }


    }


    private void sleepAWhile(int millisec){
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        log.info("Started NST Advertising manager.");
        int sleepMillisec=10000;
        boolean noPendingRequest=true;
        Object pendingRequest=null;
        while(true){
            try {
                if(noPendingRequest==true) {
                    log.debug("No pending requests.");
                    pendingRequest = requests.take();
                }
                else{
                    log.debug("There is one pending request to be satisfied.");
                }
                processRequest(pendingRequest);
                noPendingRequest=true;
                sleepMillisec=10000;
            } catch (FailedOperationException e) {
                log.error(e.getMessage());

            }
            catch (RestClientException e) {
                log.error(e.getMessage());
                if(e.getMessage().contains("Connection refused")){
                    log.warn("NST advertisement destination is not reachable.");
                    noPendingRequest=false;
                    sleepMillisec*=2;
                    if(sleepMillisec>=MAX_TIMEOUT){
                        sleepMillisec=MAX_TIMEOUT;
                    }
                    log.info("Going to retry in " +sleepMillisec/1000+ " seconds.");
                    sleepAWhile(sleepMillisec);

                }
                if(e.getMessage().contains("401")){
                    log.warn("NST advertisement not authorized. Please consider to create credentials on NST advertisement destination.");
                    noPendingRequest=false;
                    sleepMillisec*=3;
                    if(sleepMillisec>=MAX_TIMEOUT){
                        sleepMillisec=MAX_TIMEOUT;
                    }
                    log.info("Going to retry in " +sleepMillisec/1000+ " seconds.");
                    sleepAWhile(sleepMillisec);
                }
                if(e.getMessage().contains("400")){
                    log.warn("NST advertisement bad request. It will be discarded. Taking the next one available.");
                }

            }
            catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }
}

