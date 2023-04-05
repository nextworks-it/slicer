/*
 * Copyright (c) 2021 Nextworks s.r.l.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.oran.recordservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.nextworks.nfvmano.oran.policies.enums.PolicyTypeIdEnum;
import org.json.JSONObject;

import javax.persistence.*;

@Entity
public class PolicyTypeJsonSchema {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private PolicyTypeIdEnum policyTypeId;

    /**
     * This field contains the JSON schema that has to be onboarded towards the Near RT Ric
     * if it is not onboarded yet to be compliant with the O-RAN Standard
     */
    @Column(columnDefinition = "TEXT")
    private String jsonSchema;

    public PolicyTypeJsonSchema(){}

    public PolicyTypeJsonSchema(PolicyTypeIdEnum policyTypeId, String jsonSchema) {
        this.policyTypeId = policyTypeId;
        this.jsonSchema = jsonSchema;
    }

    public PolicyTypeIdEnum getPolicyTypeId() {
        return policyTypeId;
    }

    public void setPolicyTypeId(PolicyTypeIdEnum policyTypeId) {
        this.policyTypeId = policyTypeId;
    }

    public JSONObject getJsonSchema() {
        return new JSONObject(jsonSchema);
    }

    public void setJsonSchema(String jsonSchema) {
        this.jsonSchema = jsonSchema;
    }
}
