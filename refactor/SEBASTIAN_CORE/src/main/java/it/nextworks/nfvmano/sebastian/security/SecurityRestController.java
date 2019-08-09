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

import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.admin.repo.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/vs")
public class SecurityRestController {

	private static final Logger log = LoggerFactory.getLogger(SecurityRestController.class);

	@Autowired
	private TenantRepository tenantRepository;

	public SecurityRestController() {}

	private static UserDetails getDetailsFromAuth(Authentication auth) {
		Object principal = auth.getPrincipal();
		if (!UserDetails.class.isAssignableFrom(principal.getClass())) {
			throw new IllegalArgumentException("Auth.getPrincipal() does not implement UserDetails");
		}
		return (UserDetails) principal;
	}

	@RequestMapping(value = "/whoami", method = RequestMethod.GET)
	public ResponseEntity<?> whoami(Authentication auth) {
		log.debug("Received request to get logged user info");
		try {
			UserDetails details = getDetailsFromAuth(auth);
			String user = details.getUsername();
			Optional<Tenant> tenant = tenantRepository.findByUsername(user);
			if (!tenant.isPresent()) {
				throw new NotExistingEntityException(String.format("No tenant named %s", user));
			} else {
				Collection<? extends GrantedAuthority> authorities = details.getAuthorities();
				if (authorities.size() != 1) {
					log.error(
							"Incorrectly stored tenant {}: wanted 1 authority, got {}",
							user,
							authorities.size()
					);
					throw new IllegalArgumentException("");
				}
				String authority = authorities.iterator().next().getAuthority();
				return new ResponseEntity<>(new SecurityTenant(tenant.get(), authority), HttpStatus.OK);
			}
		} catch (NotExistingEntityException e) {
			log.debug("Group not found");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
