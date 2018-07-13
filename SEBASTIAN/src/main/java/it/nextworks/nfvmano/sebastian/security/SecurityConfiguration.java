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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Marco Capitani on 29/05/18.
 *
 * @author Marco Capitani <m.capitani AT nextworks.it>
 */
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

    @Autowired
    private SebCorsFilter sebCorsFilter;

    @Bean
    CorsFilter cFilter() {
        return new CorsFilter(corsConfigurationSource());
    }

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
                //.addFilterBefore(sebCorsFilter, CorsFilter.class)
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/vs/admin/**").hasAuthority("ADMIN")
                .antMatchers("/vs/whoami").authenticated()
                .antMatchers("/vs/**").hasAnyAuthority("TENANT", "ADMIN")
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
