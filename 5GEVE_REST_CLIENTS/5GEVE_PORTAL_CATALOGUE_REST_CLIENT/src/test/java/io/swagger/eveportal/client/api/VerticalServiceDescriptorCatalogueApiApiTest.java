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
import io.swagger.eveportal.client.model.OnboardVsDescriptorRequest;
import io.swagger.eveportal.client.model.VsDescriptor;
import org.junit.Test;
import org.junit.Ignore;

import java.util.List;

/**
 * API tests for VerticalServiceDescriptorCatalogueApiApi
 */
@Ignore
public class VerticalServiceDescriptorCatalogueApiApiTest {

    private final VerticalServiceDescriptorCatalogueApiApi api = new VerticalServiceDescriptorCatalogueApiApi();

    
    /**
     * Onboard a new Vertical Service Descriptor
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void createVsDescriptorUsingPOSTTest() throws ApiException {
        OnboardVsDescriptorRequest request = null;
        Boolean authenticated = null;
        String authorities0Authority = null;
        String response = api.createVsDescriptorUsingPOST(request, authenticated, authorities0Authority);

        // TODO: test validations
    }
    
    /**
     * Query ALL the Vertical Service Descriptor
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getAllVsDescriptorsUsingGETTest() throws ApiException {
        Boolean authenticated = null;
        String authorities0Authority = null;
        List<VsDescriptor> response = api.getAllVsDescriptorsUsingGET(authenticated, authorities0Authority);

        // TODO: test validations
    }
    
    /**
     * Query a Vertical Service Descriptor with a given ID
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getVsDescriptorUsingGETTest() throws ApiException {
        String vsdId = null;
        Boolean authenticated = null;
        String authorities0Authority = null;
        VsDescriptor response = api.getVsDescriptorUsingGET(vsdId, authenticated, authorities0Authority);

        // TODO: test validations
    }
    
}
