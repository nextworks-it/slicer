package it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration;

import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.TransportConfig;

import java.util.UUID;

public class SatelliteNetworkConfiguration extends UpdateConfigurationRequest{


    private TransportConfig transportConfig;


    public SatelliteNetworkConfiguration(){
        super(null, null, null, ConfigSliceSubnetType.TRANSPORT);
        this.actionType = ConfigurationActionType.SATELLITE_NETWORK_CONFIGURATION;
    }


    public SatelliteNetworkConfiguration(UUID nsiId, UUID nssiId, String nstId, TransportConfig transportConfig){

        super(nsiId, nssiId, nstId, ConfigSliceSubnetType.TRANSPORT);
        this.actionType = ConfigurationActionType.SATELLITE_NETWORK_CONFIGURATION;
        this.transportConfig= transportConfig;
    }

    public TransportConfig getTransportConfig() {
        return transportConfig;
    }
}
