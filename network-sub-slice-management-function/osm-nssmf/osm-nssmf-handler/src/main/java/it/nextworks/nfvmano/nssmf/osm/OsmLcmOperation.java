package it.nextworks.nfvmano.nssmf.osm;

import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.sbi.nfvo.messages.CreateNsIdentifierRequestInternal;
import it.nextworks.nfvmano.sbi.nfvo.messages.InstantiateNsRequestInternal;
import it.nextworks.nfvmano.sbi.nfvo.osm.Osm10Client;
import it.nextworks.nfvmano.sbi.nfvo.osm.rest.model.InstantiateNsRequest;
import it.nextworks.nfvmano.sbi.nfvo.osm.rest.model.NsInstance;
import it.nextworks.nfvmano.sbi.nfvo.osm.rest.model.VnfInstanceInfo;
import it.nextworks.nfvmano.sbi.nfvo.osm.rest.model.VnfInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.UUID;

public class OsmLcmOperation {
    private Osm10Client osm10Client;

    private final static Logger LOG = LoggerFactory.getLogger(OsmLcmOperation.class);

    public OsmLcmOperation(Osm10Client osm10Client) {
        this.osm10Client = osm10Client;
    }

    public Osm10Client getOsm10Client() {
        return osm10Client;
    }


    public String createNetworkServiceInstanceIdentifier(String networkServiceName, String nsdId) throws FailedOperationException {
        LOG.info("Creating Network Service Identifier");
        CreateNsIdentifierRequestInternal createNsIdentifierRequestInternal = new CreateNsIdentifierRequestInternal(nsdId, networkServiceName);
        return osm10Client.createNetworkServiceIdentifier(createNsIdentifierRequestInternal);
    }

    public String instantiateNetworkService(String networkServiceInstanceIdentifier, String nsdId, String nsName, String UUID_VIM_ACCOUNT) throws FailedOperationException {
        LOG.info("Requesting instantiation of Network Service whose NSD ID is " + nsdId);
        InstantiateNsRequest instantiateNsRequest = new InstantiateNsRequest();
        instantiateNsRequest.setVimAccountId(UUID.fromString(UUID_VIM_ACCOUNT));
        instantiateNsRequest.setNsdId(UUID.fromString(nsdId));
        instantiateNsRequest.setNsName(nsName);

        InstantiateNsRequestInternal instantiateNsRequestInternal = new InstantiateNsRequestInternal(nsName, nsdId, networkServiceInstanceIdentifier, new HashMap<>());
        String operationId = osm10Client.instantiateNetworkService(instantiateNsRequestInternal);
        return operationId;
    }

    public String performTerminateNetworkService(String networkServiceId) throws FailedOperationException {
        LOG.info("Requesting termination of Network Service instance whose identifier is " + networkServiceId);
        String operationId = osm10Client.terminateNetworkService(networkServiceId);
        return operationId;
    }


    public String getIpAddressVNFonExtConnectionPoint(String networkServiceInstanceId, String extCpCore) throws FailedOperationException {
        NsInstance nsInstance = null;
        nsInstance = osm10Client.getNsInstance(networkServiceInstanceId);
        for (String vnfIdentifier : nsInstance.getConstituentVnfrRef()) {
            VnfInstanceInfo vnfInstanceInfo = osm10Client.getVnfInstanceInfo(vnfIdentifier);

            for (VnfInterface vnfInterface : vnfInstanceInfo.getVdur().get(0).getInterfaces()) {
                String externalConnectionPointRefFound = vnfInterface.getExternalConnectionPointRef();
                if (externalConnectionPointRefFound.equals(extCpCore)) {
                    String ipAddress = vnfInterface.getIpAddress();
                    LOG.info("The IP address on " + extCpCore + " is " + ipAddress);
                    return ipAddress;
                }
            }
        }
        return null;
    }
}
