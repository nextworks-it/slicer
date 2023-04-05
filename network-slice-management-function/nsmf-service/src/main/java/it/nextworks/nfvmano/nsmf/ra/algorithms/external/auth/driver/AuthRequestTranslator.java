package it.nextworks.nfvmano.nsmf.ra.algorithms.external.auth.driver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.nextworks.nfvmano.libs.descriptors.sol006.Nsd;
import it.nextworks.nfvmano.libs.descriptors.sol006.Vnfd;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NSST;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NST;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NstServiceProfile;
import it.nextworks.nfvmano.libs.ifa.templates.nst.SliceSubnetType;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeRequest;
import it.nextworks.nfvmano.libs.vs.common.topology.*;
import it.nextworks.nfvmano.nsmf.nfvcatalogue.NfvoCatalogueClient;
import it.nextworks.nfvmano.nsmf.ra.algorithms.external.auth.elements.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuthRequestTranslator {
    private static final Logger log = LoggerFactory.getLogger(AuthRequestTranslator.class);
    private AuthExternalAlgorithmRequest authRequest;
    private ObjectMapper mapper;
    private ExternalProperties externalProperties;
    private NfvoCatalogueClient nfvoCatalogueClient;

    public AuthRequestTranslator(NfvoCatalogueClient nfvoCatalogueClient){
        this.authRequest=new AuthExternalAlgorithmRequest();
        this.mapper=new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        this.nfvoCatalogueClient=nfvoCatalogueClient;
    }

    public void readExternalProperties(){
        try {
            this.externalProperties = mapper.readValue(new File("/home/nicola/network-slice-management-function/nsmf-service/src/main/resources/externalProperties.json"), ExternalProperties.class);
        } catch (IOException e){
            e.printStackTrace();
            log.error("Error during external properties deserialization");
        }
    }

    public AuthExternalAlgorithmRequest translateRAComputeRequest(ResourceAllocationComputeRequest request){
        log.debug("Received request to translate resource allocation compute request with ID {} into AUTH request", request.getRequestId());
        readExternalProperties();
        authRequest.setRequestId(request.getRequestId());

        authRequest.addParameter("nsiId", request.getNsiId());
        setNodes(request.getTopology());
        setLinks(request.getTopology());
        setVnfs(request.getNst());
        setSfcs(request.getNst());
        setE2EQoS(request.getNst());
        authRequest.setPnfParameters(externalProperties.getPnfParameters());
        authRequest.setPort_power(externalProperties.getPort_power());

        return authRequest;
    }

    public void setNodes(NetworkTopology topology){
        List<Node> nodes=new ArrayList<>();

        for(TopologyNode node: topology.getNodes()) {
            Node n = new Node();
            n.setNodeId(node.getNodeId());
            switch (node.getNodeType()) {
                case COMPUTE:
                    n.setType(ExtNodeType.REGULAR);
                    n.setProcessingCapabilities(((ComputeNode) node).getProcessingCapabilities());
                    break;
                case SWITCH:
                    n.setType(ExtNodeType.REGULAR);
                    n.setProcessingCapabilities(ProcessingCapabilities.NONE);
                    break;
                case PNF:
                    if (((Pnf) node).getPnfType().equals(PnfType.gNB))
                        n.setType(ExtNodeType.gNB);
                    else if (((Pnf) node).getPnfType().equals(PnfType.BS))
                        n.setType(ExtNodeType.BS);
                    else
                        n.setType(ExtNodeType.SC);
                    n.setProcessingCapabilities(((Pnf) node).getProcessingCapabilities());
            }
            for(Map<String, Object> entry: externalProperties.getExternalProperties()){
                if((entry.get("nodeId")).toString().equals(node.getNodeId())){
                    n.setPosition((Map<String, Double>) entry.get("position"));
                    n.setProcessingInfra((Map<String, Double>) entry.get("processingInfra"));
                }
            }
            nodes.add(n);
        }
        authRequest.setNodeList(nodes);
    }

    public void setLinks(NetworkTopology topology) {
        List<Link> links=new ArrayList<>();
        List<Node> nodes=authRequest.getNodeList();

        for(TopologyLink l: topology.getLinks()){
            Link link=new Link();
            link.setLinkId(l.getLinkId());
            TopologyNode s=l.getSource();
            TopologyNode d=l.getDestination();
            for(Node n: authRequest.getNodeList())
                if(s.getNodeId().equals(n.getNodeId()))
                    link.setSource(n);
                else if (d.getNodeId().equals(n.getNodeId()))
                    link.setDestination(n);
            if(l.getLinkType().equals(LinkType.WIRED)){
                link.setLinkType(LinkType.WIRED);
                link.setBandwidth(l.getBandwidth());
                link.setDelay(l.getDelay());
            } else
                link.setLinkType(LinkType.WIRELESS);
            links.add(link);
        }

        authRequest.setLinkList(links);
    }

    public void setVnfs(NST nst){
        List<Vnf> vnfs=new ArrayList<>();
        Nsd nsd=getVappNsd(nst);
        List<String> vnfdIds=nsd.getVnfdId();
        for(String vnfdId: vnfdIds) {
            Vnfd vnfd=nfvoCatalogueClient.getVnfdById(vnfdId);
            Vnf vnf = new Vnf();
            vnf.setVnfdId(vnfdId);
            vnf.setType(vnfd.getProductInfoDescription());
            vnf.setCpuResources(Integer.valueOf(vnfd.getVirtualComputeDesc().get(0).getVirtualCpu().getNumVirtualCpu()));
            //Here it has to be added the maximum throughput available
            vnfs.add(vnf);
        }
        authRequest.setVnfs(vnfs);
    }

    public void setSfcs(NST nst){
        Nsd nsd=getVappNsd(nst);
        authRequest.addParameter("nsdId", nsd.getId());
        Sfc sfc=new Sfc("sfc-"+nsd.getId(), nsd.getName(), nsd.getVnfdId());
        authRequest.setSfc(sfc);
    }

    public void setE2EQoS(NST nst){
        E2EQoS qos=new E2EQoS();
        NstServiceProfile serviceProfile=nst.getNstServiceProfileList().get(0);
        qos.setSfcReq(authRequest.getSfc().getSfcId());
        qos.setLatency(serviceProfile.getLatency());
        qos.setSurvivalTime(serviceProfile.getSurvivalTime());
        int throughput=(serviceProfile.getdLThptPerSlice()+serviceProfile.getuLThptPerSlice())/2;
        qos.setThroughput(throughput);
        authRequest.setE2eQoS(qos);
        authRequest.setNumUsersInSlice(serviceProfile.getMaxNumberofUEs());
    }

    public Nsd getVappNsd(NST nst){
        List<NSST> nssts=nst.getNsst().getNsstList();
        String nsdId="";
        for(NSST nsst: nssts)
            if(nsst.getType().equals(SliceSubnetType.VAPP)){
                nsdId=nsst.getNsdInfo().getNsdId();
                break;
            }

        return nfvoCatalogueClient.getNsdById(nsdId);
    }
}
