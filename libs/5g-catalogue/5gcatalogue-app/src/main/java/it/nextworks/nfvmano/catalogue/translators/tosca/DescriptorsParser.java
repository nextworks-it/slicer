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
package it.nextworks.nfvmano.catalogue.translators.tosca;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import it.nextworks.nfvmano.libs.descriptors.templates.DescriptorTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class DescriptorsParser {

    private static final Logger log = LoggerFactory.getLogger(DescriptorsParser.class);

    public DescriptorsParser() {
    }

    public static String descriptorTemplateToString(DescriptorTemplate descriptor)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        String dt = mapper.writeValueAsString(descriptor);
        return dt;
    }

    public DescriptorTemplate fileToDescriptorTemplate(String fileName)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        DescriptorTemplate descriptorTemplate = mapper.readValue(new File(fileName), DescriptorTemplate.class);

        return descriptorTemplate;
    }

    public DescriptorTemplate fileToDescriptorTemplate(File file)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        DescriptorTemplate descriptorTemplate = mapper.readValue(file, DescriptorTemplate.class);

        return descriptorTemplate;
    }

    public <T> T fileToSol006(File file, Class<T> type) throws IOException {

        ObjectMapper mapper;

        if(file.getName().endsWith(".yaml") || file.getName().endsWith(".yml"))
            mapper = new ObjectMapper(new YAMLFactory());
        else
            mapper = new ObjectMapper();

        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        return mapper.readValue(file, type);
    }

    public DescriptorTemplate stringToDescriptorTemplate(String descriptor)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        DescriptorTemplate descriptorTemplate = mapper.readValue(descriptor, DescriptorTemplate.class);

        return descriptorTemplate;
    }

    public <T> T stringToSol006(String mstName, String descriptor, Class<T> type) throws IOException {

        ObjectMapper mapper;

        if(mstName.endsWith(".yaml") || mstName.endsWith(".yml"))
            mapper = new ObjectMapper(new YAMLFactory());
        else
            mapper = new ObjectMapper();

        return mapper.readValue(descriptor, type);
    }
}
