package it.nextworks.nfvmano.sebastian.vsnbi;

import java.util.List;

import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.InstantiateVsRequest;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.ModifyVsRequest;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.QueryVsResponse;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.TerminateVsRequest;


/**
 * This interface allows to invoke lifecycle management 
 * requests for Vertical Services.
 * 
 * The following operations are defined for this interface:
 * 		Instantiate VS instance;
 * 		Query VS instance;
 * 		Terminate VS instance;
 * 		Modify VS instance.
 * 
 * This interface must be implemented by the Vertical Slicer core and 
 * it must be invoked by the plugins that manage its external interface 
 * (e.g. a REST Controller)
 * 
 * @author nextworks
 *
 */
public interface VsLcmProviderInterface {

	/**
	 * This method instantiates a new Vertical Service instance. 
	 * The method returns the ID of the vertical service instance and operates in asynchronous manner.
	 * In order to retrieve the actual status of the vertical service instance, the query method must be used.
	 *
     *
	 * @param request request to instantiate a new VS instance.
	 * @return The identifier of the VS instance.
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws NotExistingEntityException if the VSD does not exist.
	 * @throws FailedOperationException if the operation fails.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws NotPermittedOperationException if the operation is not permitted for the given tenant.
	 */
	public String instantiateVs(InstantiateVsRequest request) 
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException;
	
	/**
	 * This method allows to query the Vertical Slicer for information about one or more Vertical Service instance(s). 
	 * 
	 * 
	 * @param request query
	 * @return query response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the VS instance does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws NotPermittedOperationException if the operation is not permitted for the given tenant.
	 */
	public QueryVsResponse queryVs(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException;
	
	/**
	 * This method queries the IDs of all the VSs matching the request filter
	 * 
	 * @param request query
	 * @return List of IDs matching the request filter
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the IDs are not found
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public List<String> queryAllVsIds(GeneralizedQueryRequest request)
		throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This method terminates an existing Vertical Service instance.
	 * This method operates in asynchronous manner.
	 * In order to retrieve the actual status of the vertical service instance, the query method must be used.
	 * 
	 * @param request request to terminate an existing VS instance
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the VS instance does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws NotPermittedOperationException if the operation is not permitted for the given tenant.
	 */
	public void terminateVs(TerminateVsRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException;
	
	/**
	 * This method modifies an existing Vertical Service instance.
	 * This method operates in asynchronous manner.
	 * In order to retrieve the actual status of the vertical service instance, the query method must be used.
	 * 
	 * @param request request to modify an existing VS instance
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the VS instance or the new VSD do not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws NotPermittedOperationException if the operation is not permitted for the given tenant.
	 */
	public void modifyVs(ModifyVsRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException;
}
