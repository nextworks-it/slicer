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
import io.swagger.eveportal.client.model.OnboardExpDescriptorRequest;
import org.junit.Test;
import org.junit.Ignore;

/**
 * API tests for ExperimentDescriptorCatalogueApiApi
 */
@Ignore
public class ExperimentDescriptorCatalogueApiApiTest {

    private final ExperimentDescriptorCatalogueApiApi api = new ExperimentDescriptorCatalogueApiApi();

    
    /**
     * Onboard Experiment Descriptor
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void createExpDescriptorUsingPOSTTest() throws ApiException {
        OnboardExpDescriptorRequest request = null;
        Boolean authenticated = null;
        String authorities0Authority = null;
        String response = api.createExpDescriptorUsingPOST(request, authenticated, authorities0Authority);

        // TODO: test validations
    }
    
    /**
     * getAllExpDescriptors
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getAllExpDescriptorsUsingGETTest() throws ApiException {
        Boolean authenticated = null;
        String authorities0Authority = null;
        Object response = api.getAllExpDescriptorsUsingGET(authenticated, authorities0Authority);

        // TODO: test validations
    }
    
    /**
     * getExpDescriptor
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getExpDescriptorUsingGETTest() throws ApiException {
        String expdId = null;
        Boolean authenticated = null;
        String authorities0Authority = null;
        Object response = api.getExpDescriptorUsingGET(expdId, authenticated, authorities0Authority);

        // TODO: test validations
    }
    
}
