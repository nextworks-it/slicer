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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.admin.elements.TenantGroup;
import it.nextworks.nfvmano.sebastian.admin.repo.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by Marco Capitani on 30/05/18.
 *
 * @author Marco Capitani <m.capitani AT nextworks.it>
 */
@Service("userDetailsService")
public class SlicerUserDetailsService implements UserDetailsService {

    //get user from the database, via Hibernate
    @Autowired
    private TenantRepository userRepo;

    @Value("${sebastian.admin}")
    private String adminTenant;

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {

        Optional<Tenant> optUser = userRepo.findByUsername(username);
        if (!optUser.isPresent()) {
            throw new UsernameNotFoundException(
                    String.format("Username %s not found", username)
            );
        }
        Tenant user = optUser.get();
        List<GrantedAuthority> authorities = buildUserAuthority(user.getGroup());

        return buildUserForAuthentication(user, authorities);
    }

    // converts Tenant to Spring User
    private User buildUserForAuthentication(Tenant user,
                                            List<GrantedAuthority> authorities) {
        return new User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities
        );
    }

    private List<GrantedAuthority> buildUserAuthority(TenantGroup group) {

        Set<GrantedAuthority> setAuths = new HashSet<>();
        if (group.getName().equals(adminTenant)) {
            setAuths.add(new SimpleGrantedAuthority("ADMIN"));
        } else {
            setAuths.add(new SimpleGrantedAuthority("TENANT"));
        }

        return new ArrayList<>(setAuths);
    }
}
