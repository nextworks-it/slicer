package it.nextworks.nfvmano.libs.vs.common.topology;

import java.util.List;

public class TopologyServiceInterfacePoint {

    private String sipId;
    private String topologyNodeId;
    private String topologyCpId;

    private ServiceInterfacePointDirection direction;
    private List<SupportedServiceClassifier> classifiers;

    public TopologyServiceInterfacePoint(){}

    /**
     *
     * @param sipId the service interface point id
     * @param topologyNodeId the id of the node where the sip is located
     * @param topologyCpId the id of the port (connection point) where the sip is mapped
     * @param direction the direction permitted of the sip
     * @param classifiers the classifiers supported by the sip
     */
    public TopologyServiceInterfacePoint(String sipId,
                                         String topologyNodeId,
                                         String topologyCpId,
                                         ServiceInterfacePointDirection direction,
                                         List<SupportedServiceClassifier> classifiers){
        this.sipId=sipId;
        this.topologyNodeId=topologyNodeId;
        this.topologyCpId=topologyCpId;
        this.direction=direction;
        this.classifiers=classifiers;
    }

    public String getSipId() {
        return sipId;
    }

    public void setSipId(String sipId) {
        this.sipId = sipId;
    }

    public String getTopologyNodeId() {
        return topologyNodeId;
    }

    public void setTopologyNodeId(String topologyNodeId) {
        this.topologyNodeId = topologyNodeId;
    }

    public String getTopologyCpId() {
        return topologyCpId;
    }

    public void setTopologyCpId(String topologyCpId) {
        this.topologyCpId = topologyCpId;
    }

    public ServiceInterfacePointDirection getDirection() {
        return direction;
    }

    public void setDirection(ServiceInterfacePointDirection direction) {
        this.direction = direction;
    }

    public List<SupportedServiceClassifier> getClassifiers() {
        return classifiers;
    }

    public void setClassifiers(List<SupportedServiceClassifier> classifiers) {
        this.classifiers = classifiers;
    }
}
