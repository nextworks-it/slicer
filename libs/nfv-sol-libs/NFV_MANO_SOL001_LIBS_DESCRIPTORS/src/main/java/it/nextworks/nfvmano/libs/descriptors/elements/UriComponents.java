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
package it.nextworks.nfvmano.libs.descriptors.elements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class UriComponents implements DescriptorInformationElement {

    private String scheme;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Embedded
    private UriAuthority authority;
    private String path;
    private String query;
    private String fragment;

    public UriComponents() {
    }

    public UriComponents(String scheme, UriAuthority authority, String path, String query, String fragment) {
        this.scheme = scheme;
        this.authority = authority;
        this.path = path;
        this.query = query;
        this.fragment = fragment;
    }

    @JsonProperty("scheme")
    public String getScheme() {
        return scheme;
    }

    @JsonProperty("authority")
    public UriAuthority getAuthority() {
        return authority;
    }

    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    @JsonProperty("query")
    public String getQuery() {
        return query;
    }

    @JsonProperty("fragment")
    public String getFragment() {
        return fragment;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.scheme == null)
            throw new MalformattedElementException("UriComponents without scheme");
    }
}
