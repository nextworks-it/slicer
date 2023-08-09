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
package it.nextworks.nfvmano.libs.vs.common.topology;

public enum PowerState {

	POWER_OFF,
	SLEEPING,
	LOW_POWER,
	MEDIUM_POWER,
	HIGH_POWER,
	POWER_ON;

	public static PowerState withName(String name) {
		if (null == name) {
			return null;
		}
		name = name.replace('-', '_');
		name = name.replaceAll("([a-z])([A-Z])", "$1_$2");
		name = name.toUpperCase();
		if (name.equals("READY")) return SLEEPING;
		return PowerState.valueOf(name);
	}
	
}
