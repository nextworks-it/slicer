package it.nextworks.nfvmano.libs.descriptors.groups;

import it.nextworks.nfvmano.libs.descriptors.templates.Group;

/*@Entity
@JsonTypeName("PlacementGroup")*/
public class PlacementGroup extends Group {

    /*@Embedded
    private PlacementGroupProperties properties;

    public PlacementGroup(PlacementGroupProperties properties) {
        this.properties = properties;
    }

    public PlacementGroup(String type, List<String> members, PlacementGroupProperties properties) {
        super(type, members);
        this.properties = properties;
    }

    public PlacementGroup(TopologyTemplate topologyTemplate, String type, List<String> members, PlacementGroupProperties properties) {
        super(topologyTemplate, type, members);
        this.properties = properties;
    }

    @JsonProperty("properties")
    public PlacementGroupProperties getProperties() {
        return properties;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        super.isValid();
        if (this.properties == null)
            throw new MalformattedElementException("PlacementGroup without properties");
        else
            this.properties.isValid();
    }*/


}
