/*
 * Copyright (c) 2021 Nextworks s.r.l.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.nextworks.nfvmano.nssmf.record;

import it.nextworks.nfvmano.libs.vs.common.utils.AppContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceFactory {
    private static final Logger log = LoggerFactory.getLogger(RecordServiceFactory.class);

    @Autowired
    private
    AppContextService appContextService;

    public Object getRecordService(String recordServiceName) throws NoSuchBeanDefinitionException {
        ApplicationContext applicationContext= appContextService.getAppContext();

        return applicationContext.getBean(recordServiceName);
    }
}
