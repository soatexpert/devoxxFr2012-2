package fr.soat.devoxx.game.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.model.UserRole;
import fr.soat.devoxx.game.services.UserServices;
import fr.soat.devoxx.game.services.repository.UserRoleRepository;

public class UserServiceTest extends GenericTestCase {

	private static final String USER_EMAIL = "f.ostyn@gmail.com";
	private static final String USER_NAME = "francois";
	
	@Autowired
	UserServices userServices;
	
    @Autowired
    UserRoleRepository userRolesRepo;
	
	@Test
	public void testUserCreation() {
		DevoxxUser user = new DevoxxUser();
		user.setUserEmail(USER_EMAIL);
		user.setUsername(USER_NAME);
		user.setUserForname("ostyn");
		Set<UserRole> userRoles = new HashSet<UserRole>();
		userRoles.add(createUserRole("ROLE_ADMIN"));
		userRoles.add(createUserRole("ROLE_FAKE"));
		user.setUserRoles(userRoles);
		userServices.createUser(user);
	}

	@Test
	public void testUserSelect() {
		DevoxxUser userByName = userServices.getUserByName(USER_NAME);
		assertNotNull(userByName);
		assertEquals(userByName.getUserEmail(), USER_EMAIL);
		assertEquals(userByName.getUserRoles().size(), 2);
	}

	private UserRole createUserRole(String roleName) {
		UserRole roles = new UserRole();
		roles.setRoleName(roleName);
		userRolesRepo.save(roles);
		return roles;
	}

}
