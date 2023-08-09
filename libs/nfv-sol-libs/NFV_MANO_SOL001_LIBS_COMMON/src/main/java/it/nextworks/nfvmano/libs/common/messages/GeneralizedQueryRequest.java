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
package it.nextworks.nfvmano.libs.common.messages;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.common.elements.Filter;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

/**
 * Generalized query request message
 * 
 * @author nextworks
 *
 */
public class GeneralizedQueryRequest implements InterfaceMessage {

	private Filter filter;
	private List<String> attributeSelector = new ArrayList<>();

	public GeneralizedQueryRequest() {
	}

	/**
	 * Constructor
	 * 
	 * @param filter            Filter defining the entities on which the query
	 *                          applies, based on their attributes or identifiers.
	 * @param attributeSelector Provides a list of attribute names of the entity. If
	 *                          present, only these attributes are returned.
	 */
	public GeneralizedQueryRequest(Filter filter, List<String> attributeSelector) {
		this.filter = filter;
		if (attributeSelector != null)
			this.attributeSelector = attributeSelector;
	}

	/**
	 * @return the filter
	 */
	public Filter getFilter() {
		return filter;
	}

	/**
	 * @return the attributeSelector
	 */
	public List<String> getAttributeSelector() {
		return attributeSelector;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.filter == null)
			throw new MalformattedElementException("NSD query request without filter");
	}

}
