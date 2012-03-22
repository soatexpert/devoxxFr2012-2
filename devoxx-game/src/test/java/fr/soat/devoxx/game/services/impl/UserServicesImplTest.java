package fr.soat.devoxx.game.services.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.soat.devoxx.game.services.UserServices;


public class UserServicesImplTest extends GenericTestCase {

	@Autowired
	UserServices userServices;

	@Test
	public void testCreateUser() {
		assertEquals(userServices.getUser(1L).getUserName(), "khanh");
	}

}
