package fr.soat.devoxx.game.services.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.soat.devoxx.game.services.UserServices;


public class UserServicesImplTest extends GenericTestCase {

	@Autowired
	UserServices userServices;

	@Test
	public void testCreateUser() {
		assertEquals(userServices.getUser(1).getUserName(), "khanh");
	}

}
