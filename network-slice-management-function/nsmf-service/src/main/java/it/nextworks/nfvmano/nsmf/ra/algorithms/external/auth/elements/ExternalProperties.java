package it.nextworks.nfvmano.nsmf.ra.algorithms.external.auth.elements;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Support Class to deserialize additionalProperties.json file and set the parameters that are not presents into internal topology
 */
public class ExternalProperties {

    private List<Map<String, Object>> externalProperties;
    private Map<String, Float> pnfParameters;
    private List<Map<String, BigInteger>> port_power;

    public ExternalProperties(){}

    public ExternalProperties(List<Map<String, Object>> externalProperties, Map<String, Float> pnfParameters, List<Map<String, BigInteger>> port_power) {
        this.externalProperties = externalProperties;
        this.pnfParameters = pnfParameters;
        this.port_power = port_power;
    }

    public List<Map<String, Object>> getExternalProperties() {
        return externalProperties;
    }

    public void setExternalProperties(List<Map<String, Object>> externalProperties) {
        this.externalProperties = externalProperties;
    }

    public Map<String, Float> getPnfParameters() {
        return pnfParameters;
    }

    public void setPnfParameters(Map<String, Float> pnfParameters) {
        this.pnfParameters = pnfParameters;
    }

    public List<Map<String, BigInteger>> getPort_power() {
        return port_power;
    }

    public void setPort_power(List<Map<String, BigInteger>> port_power) {
        this.port_power = port_power;
    }
}
