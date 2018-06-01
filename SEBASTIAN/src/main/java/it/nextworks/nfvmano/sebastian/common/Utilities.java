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
package it.nextworks.nfvmano.sebastian.common;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import it.nextworks.nfvmano.libs.common.elements.Filter;

public class Utilities {

	public Utilities() { }
	
	public static ObjectMapper buildObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		return mapper;
	}
	
	public static Filter buildNsdFilter(String nsdId, String nsdVersion) {
		//NSD_ID
		//NSD_VERSION
		Map<String, String> filterParams = new HashMap<>();
		filterParams.put("NSD_ID", nsdId);
		filterParams.put("NSD_VERSION", nsdVersion);
		return new Filter(filterParams);
	}
	
	public static Filter buildVsBlueprintFilter(String vsbId) {
		//VSB_ID
		Map<String, String> filterParams = new HashMap<>();
		filterParams.put("VSB_ID", vsbId);
		return new Filter(filterParams);
	}
	
	public static Filter buildVsDescriptorFilter(String vsdId, String tenantId) {
		//VSD_ID & TENANT_ID
		Map<String, String> filterParams = new HashMap<>();
		filterParams.put("VSD_ID", vsdId);
		filterParams.put("TENANT_ID", tenantId);
		return new Filter(filterParams);
	}
	
	public static Filter buildVsInstanceFilter(String vsiId, String tenantId) {
		//VSI_ID & TENANT_ID
		Map<String, String> filterParams = new HashMap<>();
		filterParams.put("VSI_ID", vsiId);
		filterParams.put("TENANT_ID", tenantId);
		return new Filter(filterParams);
	}
	
	public static Filter buildTenantFilter(String tenantId) {
		//TENANT_ID
		Map<String, String> filterParams = new HashMap<>();
		filterParams.put("TENANT_ID", tenantId);
		return new Filter(filterParams);
	}
	
	public static Filter buildVnfPackageInfoFilter(String vnfProductName, String swVersion, String provider) {
		//VNF_PACKAGE_PRODUCT_NAME
		//VNF_PACKAGE_SW_VERSION
		//VNF_PACKAGE_PROVIDER
		Map<String, String> filterParams = new HashMap<>();
		filterParams.put("VNF_PACKAGE_PRODUCT_NAME", vnfProductName);
		filterParams.put("VNF_PACKAGE_SW_VERSION", swVersion);
		filterParams.put("VNF_PACKAGE_PROVIDER", provider);
		return new Filter(filterParams);
	}
	
	public static Filter buildVnfPackageInfoFilter(String vnfPackageId) {
		//VNF_PACKAGE_ID
		Map<String, String> filterParams = new HashMap<>();
		filterParams.put("VNF_PACKAGE_ID", vnfPackageId);
		return new Filter(filterParams);
	}
	
	public static Filter buildVnfPackageInfoFilterFromVnfdId(String vnfdId) {
		//VNFD_ID
		Map<String, String> filterParams = new HashMap<>();
		filterParams.put("VNFD_ID", vnfdId);
		return new Filter(filterParams);
	}
	
	public static Filter buildNsdInfoFilter(String nsdId, String nsdVersion) {
		//NSD_ID
		//NSD_VERSION
		Map<String, String> filterParams = new HashMap<>();
		filterParams.put("NSD_ID", nsdId);
		filterParams.put("NSD_VERSION", nsdVersion);
		return new Filter(filterParams);
	}
	
	public static Filter buildMecAppPackageInfoFilter(String appdId, String version) {
		//APPD_ID
	    //APPD_VERSION
		Map<String, String> filterParams = new HashMap<>();
		filterParams.put("APPD_ID", appdId);
		filterParams.put("APPD_VERSION", version);
		return new Filter(filterParams);
	}
	
	public static Filter buildNfvNsiFilter(String nfvNsiId) {
		//NS_ID
		Map<String, String> filterParams = new HashMap<>();
		filterParams.put("NS_ID", nfvNsiId);
		return new Filter(filterParams);
	}

}
