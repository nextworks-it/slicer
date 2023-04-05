package it.nextworks.nfvmano.nsmf.ra.algorithms.external.auth.elements;

import it.nextworks.nfvmano.libs.vs.common.ra.elements.ExternalAlgorithmRequest;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthExternalAlgorithmRequest extends ExternalAlgorithmRequest {

    private List<Node> nodeList;
    private List<Link> linkList;
    private List<Vnf> vnfs;
    private Sfc sfc;
    private E2EQoS e2eQoS;
    private Map<String, Float> pnfParameters;
    private List<Map<String, BigInteger>> port_power;

    private int numUsersInSlice;

    private Map<String, String> sliceParameters=new HashMap<>();

    public AuthExternalAlgorithmRequest(){}

    public AuthExternalAlgorithmRequest(String requestId,
                                        List<Node> nodes, List<Link> linkList,
                                        List<Vnf> vnfs, Sfc sfc,
                                        E2EQoS e2eQoS, Map<String, Float> pnfParameters,
                                        List<Map<String, BigInteger>> port_power,
                                        int numUsersInSlice,
                                        Map<String, String> sliceParameters){
        this.setRequestId(requestId);
        this.nodeList =nodes;
        this.linkList=linkList;
        this.vnfs=vnfs;
        this.sfc=sfc;
        this.e2eQoS=e2eQoS;
        this.pnfParameters=pnfParameters;
        this.port_power=port_power;
        this.numUsersInSlice=numUsersInSlice;
        this.sliceParameters=sliceParameters;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public List<Link> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<Link> linkList) {
        this.linkList = linkList;
    }

    public List<Vnf> getVnfs() {
        return vnfs;
    }

    public void setVnfs(List<Vnf> vnfs) {
        this.vnfs = vnfs;
    }

    public Sfc getSfc() {
        return sfc;
    }

    public void setSfc(Sfc sfc) {
        this.sfc = sfc;
    }

    public E2EQoS getE2eQoS() {
        return e2eQoS;
    }

    public void setE2eQoS(E2EQoS e2eQoS) {
        this.e2eQoS = e2eQoS;
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

    public int getNumUsersInSlice() {
        return numUsersInSlice;
    }

    public void setNumUsersInSlice(int numUsersInSlice) {
        this.numUsersInSlice = numUsersInSlice;
    }

    public Map<String, String> getSliceParameters() {
        return sliceParameters;
    }

    public void setSliceParameters(Map<String, String> sliceParameters) {
        this.sliceParameters = sliceParameters;
    }

    public void addParameter(String key, String value){
        this.sliceParameters.put(key, value);
    }
}
