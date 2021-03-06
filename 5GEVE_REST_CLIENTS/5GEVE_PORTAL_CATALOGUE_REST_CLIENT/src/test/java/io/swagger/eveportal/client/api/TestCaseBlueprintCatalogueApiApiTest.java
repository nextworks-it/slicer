/*
 * Api Documentation
 * Api Documentation
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.eveportal.client.api;

import io.swagger.eveportal.client.ApiException;
import io.swagger.eveportal.client.model.TestCaseBlueprintInfo;
import org.junit.Test;
import org.junit.Ignore;

import java.util.List;

/**
 * API tests for TestCaseBlueprintCatalogueApiApi
 */
@Ignore
public class TestCaseBlueprintCatalogueApiApiTest {

    private final TestCaseBlueprintCatalogueApiApi api = new TestCaseBlueprintCatalogueApiApi();

    
    /**
     * Get ALL the Test Case Service Blueprints
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getAllTestCaseBlueprintsUsingGETTest() throws ApiException {
        Boolean authenticated = null;
        String authorities0Authority = null;
        List<TestCaseBlueprintInfo> response = api.getAllTestCaseBlueprintsUsingGET(authenticated, authorities0Authority);

        // TODO: test validations
    }
    
    /**
     * Get a Test Case Blueprint with a given ID
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getTcBlueprintUsingGETTest() throws ApiException {
        String tcbId = null;
        Boolean authenticated = null;
        String authorities0Authority = null;
        TestCaseBlueprintInfo response = api.getTcBlueprintUsingGET(tcbId, authenticated, authorities0Authority);

        // TODO: test validations
    }
    
}
