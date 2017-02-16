package org.apiguard.commons;

import org.apiguard.commons.utils.PasswordUtil;
import org.jgroups.tests.perf.UPerf.Config;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class,
        Config.class })
@TestPropertySource(properties = { "password.algorithm=SHA-1" })
public class PasswordUtilTest {
	@Autowired
    protected Environment env;
	
    @Test
    public void verifyProperty() {
        System.out.println(env.getProperty("password.algorithm"));
        Assert.assertEquals("SHA-1", env.getProperty("password.algorithm"));
    }

    @Test
    public void verifyInvalidProperty() {
    	System.out.println(env.getProperty("password.algorithm"));
    	Assert.assertNotEquals("MD-5", env.getProperty("password.algorithm"));
    }

	@Test
	public void verifyPasswordTest() {
		String inputPassword = "abc123";
		String encryptedPassword = PasswordUtil.getEncryptedPassword(inputPassword);
		Assert.assertTrue(PasswordUtil.verifyPassword("abc123", encryptedPassword));
	}

	@Test
	public void verifyInvalidPasswordTest() {
		String inputPassword = "abc123";
		String encryptedPassword = PasswordUtil.getEncryptedPassword(inputPassword);
		Assert.assertFalse(PasswordUtil.verifyPassword("abc1231", encryptedPassword));
	}

}
