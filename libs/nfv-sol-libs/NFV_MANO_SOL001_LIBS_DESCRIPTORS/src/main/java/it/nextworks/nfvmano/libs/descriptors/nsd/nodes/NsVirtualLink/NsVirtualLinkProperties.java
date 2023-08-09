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
package it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NsVirtualLink;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.enums.TestAccess;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.elements.ConnectivityType;
import it.nextworks.nfvmano.libs.descriptors.elements.VlProfile;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class NsVirtualLinkProperties implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private NsVirtualLinkNode nsVirtualLinkNode;

    private String description;

    private boolean mgmtNet;
    private boolean externalNet;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<TestAccess> testAccess = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nsVLProperties", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VlProfile vlProfile;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nsVLProperties", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ConnectivityType connectivityType;

    public NsVirtualLinkProperties() {

    }

    public NsVirtualLinkProperties(NsVirtualLinkNode nsVirtualLinkNode, String description,
                                   VlProfile vlProfile, ConnectivityType connectivityType, List<TestAccess> testAccess) {
        this.nsVirtualLinkNode = nsVirtualLinkNode;
        this.description = description;
        this.vlProfile = vlProfile;
        this.connectivityType = connectivityType;
        this.testAccess = testAccess;
    }

    public Long getId() {
        return id;
    }

    public NsVirtualLinkNode getNsVirtualLinkNode() {
        return nsVirtualLinkNode;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("vlProfile")
    public VlProfile getVlProfile() {
        return vlProfile;
    }

    @JsonProperty("connectivityType")
    public ConnectivityType getConnectivityType() {
        return connectivityType;
    }

    @JsonProperty("testAccess")
    public List<TestAccess> getTestAccess() {
        return testAccess;
    }

    @JsonProperty("mgmt_net")
    public boolean isMgmtNet() {
        return mgmtNet;
    }

    @JsonProperty("external_net")
    public boolean isExternalNet() {
        return externalNet;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public void setVlProfile(VlProfile vlProfile) {
        this.vlProfile = vlProfile;
    }

    public void setConnectivityType(ConnectivityType connectivityType) {
        this.connectivityType = connectivityType;
    }

    public void setExternalNet(boolean externalNet) {
        this.externalNet = externalNet;
    }

    public void setMgmtNet(boolean mgmtNet) {
        this.mgmtNet = mgmtNet;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.vlProfile == null)
            throw new MalformattedElementException("NsVirtualLink Node without vlProfile");
        else
            this.vlProfile.isValid();
        if (this.connectivityType == null)
            throw new MalformattedElementException("NsVirtualLink Node without connectivityType");
        else
            this.connectivityType.isValid();
    }
}
