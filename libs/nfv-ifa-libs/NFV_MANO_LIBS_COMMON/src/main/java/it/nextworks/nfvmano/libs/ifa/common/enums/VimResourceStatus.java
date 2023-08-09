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
package it.nextworks.nfvmano.libs.ifa.common.enums;

public enum VimResourceStatus {
	
	INSTANTIATING("INSTANTIATING"),
	INSTANTIATED("INSTANTIATED"),
	FAILED("FAILED"),
	TERMINATING("TERMINATING"),
	TERMINATED("TERMINATED");
	
	private String text;
	
	VimResourceStatus(String text) {
		this.text = text;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	public static VimResourceStatus fromString(String text) {
		for (VimResourceStatus x : VimResourceStatus.values()) {
			if (x.text.equalsIgnoreCase(text)) {
				return x;
			}
		}
		return null;
	}
	
}
