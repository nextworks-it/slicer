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

import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.admin.elements.TenantGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Marco Capitani on 29/05/18.
 *
 * @author Marco Capitani <m.capitani AT nextworks.it>
 */
@Configuration
@EnableWebSecurity
@ComponentScan("it.nextworks.test")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private SlicerUserDetailsService userService;

    @Autowired
    private AdminService tenantService;

    @Autowired
    private AuthSuccessHandler authenticationSuccessHandler;

    @Autowired
    @Qualifier("sPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Value("${sebastian.admin}")
    private String adminTenant;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        log.debug("Starting security configuration.");

        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);

        log.debug("Inserting bootstrap user(s).");

        // Add bootstrap user & group

        tenantService.createGroup(adminTenant);
        tenantService.createGroup("user");
        Tenant admin = new Tenant(
                null,
                adminTenant,
                adminTenant
        );
        Tenant user = new Tenant(
                null,
                "user",
                "user"
        );
        tenantService.createTenant(admin, adminTenant);
        tenantService.createTenant(user, "user");
        log.info("Security configuration complete.");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/vs/admin/**").hasAuthority(adminTenant)
                // TODO add permission for /vs/admin/group/<g_name> ???
                .antMatchers("/vs/**").authenticated()
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout();
    }

    @Bean
    public AuthSuccessHandler sSuccessHandler(){
        return new AuthSuccessHandler();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler sFailureHandler(){
        return new SimpleUrlAuthenticationFailureHandler();
    }
}