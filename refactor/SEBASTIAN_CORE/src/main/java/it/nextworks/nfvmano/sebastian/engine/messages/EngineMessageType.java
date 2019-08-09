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
package it.nextworks.nfvmano.sebastian.engine.messages;

public enum EngineMessageType {

	INSTANTIATE_VSI_REQUEST,
	TERMINATE_VSI_REQUEST,
	MODIFY_VSI_REQUEST,
	INSTANTIATE_NSI_REQUEST,
	MODIFY_NSI_REQUEST,
	TERMINATE_NSI_REQUEST,
	NOTIFY_NSI_STATUS_CHANGE,
	NOTIFY_NFV_NSI_STATUS_CHANGE,
	COORDINATE_VSI_REQUEST,
	NOTIFY_TERMINATION,
	NOTIFY_UPDATE,
	RESOURCES_GRANTED
	
}
