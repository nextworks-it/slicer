package it.nextworks.nfvmano.nsmf.nfvcatalogue;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import it.nextworks.nfvmano.libs.descriptors.sol006.Nsd;
import it.nextworks.nfvmano.libs.descriptors.sol006.Vnfd;
import it.nextworks.nfvmano.libs.vs.common.utils.BaseRestClient;
import it.nextworks.nfvmano.nsmf.nfvcatalogue.elements.NsdMapping;
import it.nextworks.nfvmano.nsmf.nfvcatalogue.elements.VnfdMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class NfvoCatalogueClient extends BaseRestClient {
    private final static Logger log= LoggerFactory.getLogger(NfvoCatalogueClient.class);

    @Value("${nfvo.catalogue.baseurl:http://localhost}")
    private String catalogueBaseUrl;

    private ObjectMapper mapper;

    public NfvoCatalogueClient(){
        super();
        this.mapper=new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public Nsd getNsdById(String nsdId){
        log.debug("Received request to retrieve NSD with ID {}", nsdId);
        String nsdInfoId=getNsdInfoId(nsdId);
        String url=catalogueBaseUrl+"/nsd/v1/ns_descriptors/"+nsdInfoId+"/nsd_content";
        ResponseEntity<String> httpResponse= performHTTPRequest(null, url, HttpMethod.GET);
        String response=manageHTTPResponse(httpResponse, "Impossible to find NSD with ID " + nsdId, "NSD with ID " + nsdId + " found", HttpStatus.OK);
        try {
            Nsd nsd;
            if(response.startsWith("{")) {
                 nsd= mapper.readValue(response, Nsd.class);
            } else {
                ObjectMapper ymalMapper= new ObjectMapper(new YAMLFactory());
                nsd= ymalMapper.readValue(response, Nsd.class);
            }
            return nsd;
        } catch (IOException e){
            log.error("No NSD with ID {} found", nsdId);
        }
        return null;
    }

    public Vnfd getVnfdById(String vnfdId){
        log.debug("Received request to retrieve VNFD with ID {}", vnfdId);
        String vnfdInfoId=getVnfdInfoId(vnfdId);
        String url=catalogueBaseUrl+"/vnfpkgm/v1/vnf_packages/"+vnfdInfoId+"/vnfd";
        ResponseEntity<String> httpResponse= performHTTPRequest(null, url, HttpMethod.GET);
        String response=manageHTTPResponse(httpResponse, "Impossible to find VNFD with ID " + vnfdId, "VNFD with ID " + vnfdId + " found", HttpStatus.OK);
        try {
            Vnfd vnfd;
            if(response.startsWith("{"))
                vnfd = mapper.readValue(response, Vnfd.class);
            else {
                ObjectMapper ymalMapper= new ObjectMapper(new YAMLFactory());
                vnfd= ymalMapper.readValue(response, Vnfd.class);
            }
            return vnfd;
        } catch (IOException e){
            log.error("No VNFD with ID {} found", vnfdId);
        }
        return null;
    }

    private String getNsdInfoId(String nsdId){
        List<NsdMapping> mapping=null;
        String url=catalogueBaseUrl+"/nsd/v1/ns_descriptors";
        ResponseEntity<String> httpResponse=performHTTPRequest(null, url, HttpMethod.GET);
        try{
            mapping=mapper.readValue(manageHTTPResponse(httpResponse, "Impossible to find NSD packages", "NSD packages found", HttpStatus.OK), new TypeReference<List<NsdMapping>>(){});
        }catch (IOException e){
            log.error("No NSD packages found");
        }
        for(NsdMapping m: mapping){
            if (nsdId.equals(m.getNsdId()))
                return m.getId();
        }
        return null;
    }

    private String getVnfdInfoId(String vnfdId){
        List<VnfdMapping> mapping=null;
        String url=catalogueBaseUrl+"/vnfpkgm/v1/vnf_packages";
        ResponseEntity<String> httpResponse=performHTTPRequest(null, url, HttpMethod.GET);
        try{
            mapping=mapper.readValue(manageHTTPResponse(httpResponse, "Impossible to find VNFD packages", "VNFD packages found", HttpStatus.OK), new TypeReference<List<VnfdMapping>>(){});
        }catch (IOException e){
            log.error("No VNFD packages found");
        }
        for(VnfdMapping m: mapping){
            if(vnfdId.equals(m.getVnfdId()))
                return m.getId();
        }
        return null;
    }
}
