/*
 * 5GEVE Experiment LCM
 * The API of the 5GEVE Experiment Lifecycle Manager
 *
 * OpenAPI spec version: 1.0
 * Contact: info@nextworks.it
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.elcm.client.auth;

import io.swagger.elcm.client.Pair;

import java.util.Map;
import java.util.List;

public interface Authentication {
    /**
     * Apply authentication settings to header and query params.
     *
     * @param queryParams List of query parameters
     * @param headerParams Map of header parameters
     */
    void applyToParams(List<Pair> queryParams, Map<String, String> headerParams);
}
