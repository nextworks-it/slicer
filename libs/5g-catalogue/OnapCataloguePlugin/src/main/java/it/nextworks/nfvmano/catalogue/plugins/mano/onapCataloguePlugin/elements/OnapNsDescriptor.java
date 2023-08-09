package it.nextworks.nfvmano.catalogue.plugins.mano.onapCataloguePlugin.elements;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

public class OnapNsDescriptor {

    private Map<String, Object> nsDescriptor = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> any() {
        return nsDescriptor;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        nsDescriptor.put(name, value);
    }

    @JsonIgnore
    public Map <String, Object> getMetadata(){
        return (Map <String, Object>) this.nsDescriptor.get("metadata");
    }

    @JsonIgnore
    public Map <String, Object> getTopologyTemplate(){
        return (Map <String, Object>) this.nsDescriptor.get("topology_template");
    }

    @JsonIgnore
    public Map <String, Object> getNodeTemplates(){
        return (Map <String, Object>) getTopologyTemplate().get("node_templates");
    }

    @JsonIgnore
    public String getNsdId(){
        return (String) getMetadata().get("UUID");
    }

    @JsonIgnore
    public Map<String, String> getVFIdentifiers(){
        //Map <nodeName, vfIdentifier>
        Map<String, String> vfIdentifiers = new HashMap<>();
        Map <String, Object> nodeTemplates = getNodeTemplates();
        for(Map.Entry<String, Object> nodeTemplate : nodeTemplates.entrySet()){
            Map <String, Object> node = (Map <String, Object>) nodeTemplate.getValue();
            String nodeType = (String) node.get("type");
            String[] nodeTypeSplitted = new String[0];
            if (nodeType.startsWith("org.openecomp.resource.vf."))
                nodeTypeSplitted = nodeType.split("\\.");
                vfIdentifiers.put(nodeTemplate.getKey(), nodeTypeSplitted[nodeTypeSplitted.length - 1]);
        }
        return vfIdentifiers;
    }
}
