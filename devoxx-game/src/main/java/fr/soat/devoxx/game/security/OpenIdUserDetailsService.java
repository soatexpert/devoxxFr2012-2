package fr.soat.devoxx.game.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import fr.soat.devoxx.game.model.User;
import fr.soat.devoxx.game.services.UserServices;

/**
 * @author aurelien
 */
public class OpenIdUserDetailsService implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

	@Autowired
	UserServices userServices;

	public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
		String email = null;

		List<OpenIDAttribute> attributes = token.getAttributes();

		for (OpenIDAttribute attribute : attributes) {
			if (attribute.getName().equals("email")) {
				email = attribute.getValues().get(0);
			}
		}

		if (email == null) {
			return null;
		}
		
		if (((User) userServices.getUserByName(email)).isAdmin()) {
			return new OpenIdUserDetails(email, AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
		}
		return new OpenIdUserDetails(email, AuthorityUtils.createAuthorityList("ROLE_USER"));
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userServices.getUserByName(username);
	}

}
