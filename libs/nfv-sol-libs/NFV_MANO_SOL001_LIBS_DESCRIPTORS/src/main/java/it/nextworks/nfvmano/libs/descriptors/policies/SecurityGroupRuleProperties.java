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
package it.nextworks.nfvmano.libs.descriptors.policies;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.enums.DirectionType;
import it.nextworks.nfvmano.libs.common.enums.IpVersion;
import it.nextworks.nfvmano.libs.common.enums.LayerProtocol;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class SecurityGroupRuleProperties implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private SecurityGroupRule securityGroupRule;

    private String description;
    private DirectionType direction;
    private IpVersion etherType;
    private LayerProtocol protocol;
    private int portRangeMin;
    private int portRangeMax;

    public SecurityGroupRuleProperties() {
    }

    public SecurityGroupRuleProperties(String description, DirectionType direction, IpVersion etherType, LayerProtocol protocol, int portRangeMin, int portRangeMax) {
        this.description = description;
        this.direction = direction;
        this.etherType = etherType;
        this.protocol = protocol;
        this.portRangeMin = portRangeMin;
        this.portRangeMax = portRangeMax;
    }

    public SecurityGroupRuleProperties(SecurityGroupRule securityGroupRule, String description, DirectionType direction, IpVersion etherType, LayerProtocol protocol, int portRangeMin, int portRangeMax) {
        this.securityGroupRule = securityGroupRule;
        this.description = description;
        this.direction = direction;
        this.etherType = etherType;
        this.protocol = protocol;
        this.portRangeMin = portRangeMin;
        this.portRangeMax = portRangeMax;
    }

    public Long getId() {
        return id;
    }

    public SecurityGroupRule getSecurityGroupRule() {
        return securityGroupRule;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("direction")
    public DirectionType getDirection() {
        return direction;
    }

    @JsonProperty("etherType")
    public IpVersion getEtherType() {
        return etherType;
    }

    @JsonProperty("protocol")
    public LayerProtocol getProtocol() {
        return protocol;
    }

    @JsonProperty("portRangeMin")
    public int getPortRangeMin() {
        return portRangeMin;
    }

    @JsonProperty("portRangeMax")
    public int getPortRangeMax() {
        return portRangeMax;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
