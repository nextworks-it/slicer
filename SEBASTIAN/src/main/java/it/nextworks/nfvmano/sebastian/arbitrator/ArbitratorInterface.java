package it.nextworks.nfvmano.sebastian.arbitrator;

import java.util.List;

import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;

/**
 * This is the interface of the Vertical Slicer Arbitrator component.
 * The arbitrator takes decisions about how to map new VS instances, 
 * described through their NSD, DF, IL, to network slices, based on 
 * the SLAs established for the tenant requesting the VS instance and 
 * the current resources utilized by the tenant itself. 
 * In particular, the arbitrator decides the following:
 * 1. if the request for the new VS instance can be satisfied, based on 
 * the SLA
 * 2. if the new VS instance must be mapped on new network slices or
 * existing ones (at the moment only new network slices are supported)
 * 2.a. in case of re-usage of existing slices, if and how they need to be
 * modified (future feature)
 * 3. if existing VS instances needs to be scaled down (future feature)
 * 
 * @author nextworks
 *
 */
public interface ArbitratorInterface {

	/**
	 * This method computes a solution to allocate the requested VSIs in
	 * new or existing network slices. All the requests must be processed 
	 * concurrently.
	 * 
	 * @param requests list of requests.
	 * @return list of response mapping VSI to NSIs
	 */
	List<ArbitratorResponse> computeArbitratorSolution(List<ArbitratorRequest> requests) 
			throws FailedOperationException, NotExistingEntityException;
	
}
