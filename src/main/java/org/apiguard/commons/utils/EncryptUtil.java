package org.apiguard.commons.utils;

import org.jasypt.digest.PooledStringDigester;
import org.jasypt.salt.RandomSaltGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EncryptUtil {

	private final static PooledStringDigester digester = new PooledStringDigester();

	@Value("${basic.auth.password.algorithm}")
	private String algorithm = "MD5";

	@Value("${basic.auth.encryption.iteration}")
	private Integer iteration = 1234;

	@PostConstruct
	private void setup() {
		int cores = Runtime.getRuntime().availableProcessors();
		digester.setPoolSize(cores);
		digester.setAlgorithm(algorithm);
		digester.setIterations(iteration);

		RandomSaltGenerator saltGenerator = new RandomSaltGenerator();
		digester.setSaltGenerator(saltGenerator);
		digester.setSaltSizeBytes(32);
	}

	public static String getEncryptedString(String password) {
		return digester.digest(password);
	}

	public static boolean verify(String inputPassword, String encryptedPassword) {
		return digester.matches(inputPassword, encryptedPassword);
	}

}
