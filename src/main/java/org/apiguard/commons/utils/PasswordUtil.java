package org.apiguard.commons.utils;

import org.jasypt.digest.PooledStringDigester;
import org.jasypt.salt.RandomSaltGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

public class PasswordUtil {

	private final static PooledStringDigester digester = new PooledStringDigester();

	@Value("${password.algorithm}")
	private static String algorithm = "MD5";

	@Value("${password.encryption.iteration}")
	private static Integer iteration = 1234;

	static {
		int cores = Runtime.getRuntime().availableProcessors();
		digester.setPoolSize(cores);
		digester.setAlgorithm(algorithm);
		digester.setIterations(iteration);

		RandomSaltGenerator saltGenerator = new RandomSaltGenerator();
		digester.setSaltGenerator(saltGenerator);
		digester.setSaltSizeBytes(16);
	}

	public static String getEncryptedPassword(String password) {
		return digester.digest(password);
	}

	public static boolean verifyPassword(String inputPassword, String encryptedPassword) {
		return digester.matches(inputPassword, encryptedPassword);
	}

}
