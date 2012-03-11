package fr.soat.devoxx.game.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

	@Test
	public void testDeleteUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQuestionList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPosition() {
		fail("Not yet implemented");
	}

}
