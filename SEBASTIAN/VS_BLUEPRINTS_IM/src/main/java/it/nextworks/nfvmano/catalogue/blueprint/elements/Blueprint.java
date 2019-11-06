package it.nextworks.nfvmano.catalogue.blueprint.elements;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public abstract class Blueprint implements DescriptorInformationElement {



    @NotBlank
    protected String version;
    @NotBlank
    protected String name;
    protected String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String imgUrl;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<@Valid VsBlueprintParameter> parameters = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "vsb", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotEmpty
    protected List<@Valid VsComponent> atomicComponents = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @NotEmpty
    protected List<@Valid VsbEndpoint> endPoints = new ArrayList<>();

    public Blueprint() {
    }

    public Blueprint(String version, String name, String description, String imgUrl,
                     List<VsBlueprintParameter> parameters,
                     List<VsComponent> atomicComponents,
                     List<VsbEndpoint> endPoints) {
        this.version = version;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        if (parameters != null) {
            this.parameters = parameters;
        }
        if (atomicComponents != null) {
            this.atomicComponents = atomicComponents;
        }
        if (endPoints != null) {
            this.endPoints = endPoints;
        }
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<VsBlueprintParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<VsBlueprintParameter> parameters) {
        this.parameters = parameters;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<VsComponent> getAtomicComponents() {
        return atomicComponents;
    }

    public void setAtomicComponents(List<VsComponent> atomicComponents) {
        this.atomicComponents = atomicComponents;
    }

    public List<VsbEndpoint> getEndPoints() {
        return endPoints;
    }

    public void setEndPoints(List<VsbEndpoint> endPoints) {
        this.endPoints = endPoints;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        for (VsBlueprintParameter p : parameters) {
            p.isValid();
        }
        if (version == null) {
            throw new MalformattedElementException("VS blueprint without version");
        }
        if (name == null) {
            throw new MalformattedElementException("VS blueprint without name");
        }
        if (atomicComponents != null) {
            for (VsComponent c : atomicComponents) {
                c.isValid();
            }
        }
        if (endPoints != null) {
            for (VsbEndpoint e : endPoints) {
                e.isValid();
            }
        }
    }
}