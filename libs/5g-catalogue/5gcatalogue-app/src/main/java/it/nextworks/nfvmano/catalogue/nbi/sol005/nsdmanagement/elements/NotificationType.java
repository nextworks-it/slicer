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
 * Match particular notification types. Permitted values:
 * NsdOnBoardingNotification, NsdOnboardingFailureNotification,
 * NsdChangeNotification, NsdDeletionNotification PnfdOnBoardingNotification,
 * PnfdOnBoardingFailureNotification, PnfdDeletionNotification. The permitted
 * values of the \"notificationTypes\" ] attribute are spelled exactly as the
 * names of the notification types to facilitate automated code generation
 * systems.
 */
public enum NotificationType {

    NSDONBOARDINGNOTIFICATION("NsdOnBoardingNotification"),

    NSDONBOARDINGFAILURENOTIFICATION("NsdOnboardingFailureNotification"),

    NSDCHANGENOTIFICATION("NsdChangeNotification"),

    NSDDELETIONNOTIFICATION("NsdDeletionNotification"),

    PNFDONBOARDINGNOTIFICATION("PnfdOnBoardingNotification"),

    PNFDONBOARDINGFAILURENOTIFICATION("PnfdOnBoardingFailureNotification"),

    PNFDDELETIONNOTIFICATION("PnfdDeletionNotification");

    private String value;

    NotificationType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static NotificationType fromValue(String text) {
        for (NotificationType b : NotificationType.values()) {
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
