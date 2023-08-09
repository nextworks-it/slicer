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
package it.nextworks.nfvmano.libs.descriptors.elements.lcm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ScaleVnfOpConfig implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    @JoinColumn(name = "scale_op_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VnfLcmOperationsConfiguration config;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> parameter = new ArrayList<>();

    private boolean scalingByMoreThanOneStepSupported;

    public ScaleVnfOpConfig() {
    }

    public ScaleVnfOpConfig(VnfLcmOperationsConfiguration config, List<String> parameter,
                            boolean scalingByMoreThanOneStepSupported) {
        this.config = config;
        if (parameter != null)
            this.parameter = parameter;
        this.scalingByMoreThanOneStepSupported = scalingByMoreThanOneStepSupported;
    }

    @JsonProperty("parameter")
    public List<String> getParameter() {
        return parameter;
    }

    @JsonProperty("scalingByMoreThanOneStepSupported")
    public boolean isScalingByMoreThanOneStepSupported() {
        return scalingByMoreThanOneStepSupported;
    }

    @Override
    public void isValid() throws MalformattedElementException {
    }

}
