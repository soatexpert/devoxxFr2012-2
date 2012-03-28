package fr.soat.devoxx.game.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.model.UserRoles;
import fr.soat.devoxx.game.services.UserRolesServices;
import fr.soat.devoxx.game.services.UserServices;
import fr.soat.devoxx.game.services.repository.UserRolesRepository;

public class UserServiceTest extends GenericTestCase {

	private static final String USER_EMAIL = "f.ostyn@gmail.com";
	private static final String USER_NAME = "francois";
	
	@Autowired
	UserServices userServices;
	
    @Autowired
    UserRolesRepository userRolesRepo;
	
	@Test
	public void testUserCreation() {
		DevoxxUser user = new DevoxxUser();
		user.setUserEmail(USER_EMAIL);
		user.setUserName(USER_NAME);
		user.setUserForname("ostyn");
		List<UserRoles> userRoles = new ArrayList<UserRoles>();
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

	private UserRoles createUserRole(String roleName) {
		UserRoles roles = new UserRoles();
		roles.setRoleName(roleName);
		userRolesRepo.save(roles);
		return roles;
	}

}
