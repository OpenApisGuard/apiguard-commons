package org.apiguard.commons;

import org.apiguard.commons.utils.EncryptUtil;
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
        Config.class, EncryptUtil.class })
@TestPropertySource(properties = { "basic.auth.password.algorithm=SHA-1", "basic.auth.encryption.iteration=1000" })
public class EncryptUtilTest {
	@Autowired
    protected Environment env;

    @Test
    public void verifyProperty() {
        Assert.assertEquals("SHA-1", env.getProperty("basic.auth.password.algorithm"));
    }

    @Test
    public void verifyInvalidProperty() {
    	Assert.assertNotEquals("MD-5", env.getProperty("basic.auth.password.algorithm"));
    }

	@Test
	public void verifyPasswordTest() {
		String inputPassword = "abc123";
		String encryptedPassword = EncryptUtil.getEncryptedString(inputPassword);
		Assert.assertTrue(EncryptUtil.verify("abc123", encryptedPassword));
	}

	@Test
	public void verifyPasswordTest2() {
		String inputPassword = "helloworld";
		String encryptedPassword = EncryptUtil.getEncryptedString(inputPassword);
		Assert.assertTrue(EncryptUtil.verify("helloworld", encryptedPassword));
	}

	@Test
	public void verifyInvalidPasswordTest() {
		String inputPassword = "abc123";
		String encryptedPassword = EncryptUtil.getEncryptedString(inputPassword);
		Assert.assertFalse(EncryptUtil.verify("abc1231", encryptedPassword));
	}

}
