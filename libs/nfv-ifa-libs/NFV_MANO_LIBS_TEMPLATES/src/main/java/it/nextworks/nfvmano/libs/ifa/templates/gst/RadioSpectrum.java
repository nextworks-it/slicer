/*
 * Copyright (c) 2021 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package it.nextworks.nfvmano.libs.ifa.templates.gst;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

@Entity
public class RadioSpectrum {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<NROperatingBand> nrOperatingBands;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<EUTRAOperatingBand> eutraOperatingBands;

    public RadioSpectrum(){}

    public void setNrOperatingBands(List<NROperatingBand> nrOperatingBands) {
        this.nrOperatingBands = nrOperatingBands;
    }

    public void setEutraOperatingBands(List<EUTRAOperatingBand> eutraOperatingBands) {
        this.eutraOperatingBands = eutraOperatingBands;
    }

    public void isValid() throws MalformattedElementException {
        if(this.nrOperatingBands!=null && this.eutraOperatingBands!=null)
            throw new MalformattedElementException("Only one bands list permitted");
    }

    public List<NROperatingBand> getNrOperatingBands() {
        return nrOperatingBands;
    }

    public List<EUTRAOperatingBand> getEutraOperatingBands() {
        return eutraOperatingBands;
    }
}
