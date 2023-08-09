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
package it.nextworks.nfvmano.catalogue.nbi.sol005.nsdmanagement.elements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The enumeration NsdUsageStateType shall comply with the provisions defined in
 * Table 5.5.4.4-1 of GS NFV-SOL 005. It indicates the usage state of the
 * resource. IN_USE = The resource is in use. NOT_IN_USE = The resource is
 * not-in-use.
 */
public enum NsdUsageStateType {

    IN_USE("IN_USE"),

    NOT_IN_USE("NOT_IN_USE");

    private String value;

    NsdUsageStateType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static NsdUsageStateType fromValue(String text) {
        for (NsdUsageStateType b : NsdUsageStateType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }
}
