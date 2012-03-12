package fr.soat.devoxx.game.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import com.google.common.base.Strings;

import fr.soat.devoxx.game.model.User;
import fr.soat.devoxx.game.services.UserServices;

/**
 * @author aurelien
 */
public class OpenIdUserDetailsService implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    @Autowired
    UserServices userServices;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenIdUserDetailsService.class);

    @Override
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
        User user = null;
        String email = null;
        String firstName = null;
        String lastName = null;
        String fullName = null;
        String urlId = token.getIdentityUrl();

        if (Strings.isNullOrEmpty(urlId)) {
            throw new UsernameNotFoundException("UrlId is null");
        }
        
        try {
            user = userServices.getUserByName(urlId);
        } catch (RuntimeException e) {
            user = null;
            // TODO une exception plus explicite dans le catch
            LOGGER.debug("user not found !", e);
        }

        List<OpenIDAttribute> attributes = token.getAttributes();

        for (OpenIDAttribute attribute : attributes) {
            if (attribute.getName().equals("email")) {
                email = attribute.getValues().get(0);
            }
            if (attribute.getName().equals("firstname")) {
                firstName = attribute.getValues().get(0);
            }
            if (attribute.getName().equals("lastname")) {
                lastName = attribute.getValues().get(0);
            }
            if (attribute.getName().equals("fullname")) {
                fullName = attribute.getValues().get(0);
            }
        }

        if (Strings.isNullOrEmpty(fullName) && (!Strings.isNullOrEmpty(firstName) || !Strings.isNullOrEmpty(lastName))) {
            fullName = firstName + " " + lastName;
        }
        
        if(null == user) {
            user = new User();
            user.setAdmin(false);
            user.setUserName(urlId);
            user.setUserForname(fullName);
            user.setUserEmail(email);
            userServices.createUser(user);
        }
        else {
            user.setUserForname(fullName);
            user.setUserEmail(email);
            userServices.updateUser(user);
        }

        OpenIdUserDetails openIdUser;
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        if (user.isAdmin()) {
            grantedAuthorities.addAll(AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        }
        openIdUser = new OpenIdUserDetails(urlId, grantedAuthorities);
        openIdUser.setUser(user);
        
        return openIdUser;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userServices.getUserByName(username);
        } catch (RuntimeException e) {
            // TODO LOGGER + une exception plus explicite dans le catch
            LOGGER.debug("user not found", e);
            throw new UsernameNotFoundException(username + " not found", e);
        }
    }

}
