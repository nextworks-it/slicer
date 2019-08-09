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
package it.nextworks.nfvmano.sebastian.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Marco Capitani on 31/05/18.
 *
 * @author Marco Capitani <m.capitani AT nextworks.it>
 */
@Configuration
public class PasswordEncoderConfig {

    @Value("${security.passwordEncoder}")
    private PwEncoderType encoderType;

    @Bean
    public PasswordEncoder sPasswordEncoder(){
        switch (encoderType) {
            case BCRYPT:
                return new BCryptPasswordEncoder(10);
            case NOOP:
                return NoOpPasswordEncoder.getInstance();
            default:
                throw new IllegalArgumentException("No pw encoder " + encoderType);
        }
    }

    public enum PwEncoderType {
        BCRYPT,
        NOOP
    }
}
