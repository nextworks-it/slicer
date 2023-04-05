package it.nextworks.nfvmano.nsmf.topology;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.nextworks.nfvmano.libs.vs.common.topology.NetworkTopology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class InfrastructureTopologyService {
    private final static Logger log=LoggerFactory.getLogger(InfrastructureTopologyService.class);

    public InfrastructureTopologyService(){}

    public NetworkTopology getInfrastructureTopology(){
        log.debug("Received request to retrieve infrastructure topology");
        NetworkTopology topology=null;

        ObjectMapper mapper=new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try{
            String resourceName = "topology.json";
            Resource resourceSpec = new ClassPathResource(resourceName);

            InputStream resource = resourceSpec.getInputStream();
            topology=mapper.readValue(resource, NetworkTopology.class);
        } catch (IOException e){
            e.printStackTrace();
        }

        return topology;
    }
}
