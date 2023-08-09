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
 * The enumeration NsdOnboardingStateType shall comply with the provisions
 * defined in Table 5.5.4.5-1 of GS NFV-SOL 005. It indicates the onboarding
 * state of the NSD. CREATED = The NSD information object is created. UPLOADING
 * = The associated NSD content is being uploaded. PROCESSING = The associated
 * NSD content is being processed, e.g. validation. ONBOARDED = The associated
 * NSD content is on-boarded.
 */
public enum NsdOnboardingStateType {

    CREATED("CREATED"),

    UPLOADING("UPLOADING"),

    PROCESSING("PROCESSING"),

    ONBOARDED("ONBOARDED"),

    LOCAL_ONBOARDED("LOCAL_ONBOARDED"),

    SKIPPED("SKIPPED"),

    FAILED("FAILED");

    private String value;

    NsdOnboardingStateType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static NsdOnboardingStateType fromValue(String text) {
        for (NsdOnboardingStateType b : NsdOnboardingStateType.values()) {
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
