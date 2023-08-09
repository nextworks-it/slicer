/*
 * Copyright 2018 Nextworks s.r.l.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.libs.descriptors.templates;

/*@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = PlacementGroup.class, name = "tosca.groups.nfv.PlacementGroup")})*/
public abstract class Group /*implements DescriptorInformationElement*/ {

    /*@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JsonIgnore
    private TopologyTemplate topologyTemplate;

    private String type;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> members = new ArrayList<>();

    public Group() {

    }

    public Group(String type, List<String> members) {
        this.type = type;
        this.members = members;
    }

    public Group(TopologyTemplate topologyTemplate, String type, List<String> members) {
        this.topologyTemplate = topologyTemplate;
        this.type = type;
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public TopologyTemplate getTopologyTemplate() {
        return topologyTemplate;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("members")
    public List<String> getMembers() {
        return members;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.type == null)
            throw new MalformattedElementException("Group without type");
        if (this.members == null || this.members.isEmpty())
            throw new MalformattedElementException("Group without members");
    }*/
}
