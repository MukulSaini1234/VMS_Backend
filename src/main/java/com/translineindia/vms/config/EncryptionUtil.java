//package com.translineindia.vms.config;
//
//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.util.Base64;
//
//public class EncryptionUtil {
//
//    private static final String ALGORITHM = "AES";
//    private static final int KEY_SIZE = 128; // Use 128, 192, or 256 (requires JCE policy for >128)
//
//    /**
//     * Generates a new AES key.
//     * 
//     * @return The generated AES key as a Base64-encoded string.
//     */
//    public static String generateKey() throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
//        keyGenerator.init(KEY_SIZE);
//        SecretKey secretKey = keyGenerator.generateKey();
//        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
//    }
//
//    /**
//     * Encrypts the given plaintext using AES and the provided key.
//     * 
//     * @param plaintext The plaintext to encrypt.
//     * @param key       The AES key (Base64-encoded).
//     * @return The encrypted text (Base64-encoded).
//     */
//    public static String encrypt(String plaintext, String key) throws Exception {
//        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), ALGORITHM);
//        Cipher cipher = Cipher.getInstance(ALGORITHM);
//        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
//        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
//        return Base64.getEncoder().encodeToString(encryptedBytes);
//    }
//
//    /**
//     * Decrypts the given ciphertext using AES and the provided key.
//     * 
//     * @param ciphertext The encrypted text (Base64-encoded).
//     * @param key        The AES key (Base64-encoded).
//     * @return The decrypted plaintext.
//     */
//    public static String decrypt(String ciphertext, String key) throws Exception {
//        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), ALGORITHM);
//        Cipher cipher = Cipher.getInstance(ALGORITHM);
//        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
//        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
//        return new String(decryptedBytes);
//    }
//
//    // Example Usage
//    public static void main(String[] args) {
//        try {
//            // Generate a new AES key
//            String secretKey = generateKey();
//            System.out.println("Generated Key: " + secretKey);
//
//            // Encrypt data
//            String plaintext = "Sensitive Data";
//            String encrypted = encrypt(plaintext, secretKey);
//            System.out.println("Encrypted: " + encrypted);
//
//            // Decrypt data
//            String decrypted = decrypt(encrypted, secretKey);
//            System.out.println("Decrypted: " + decrypted);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}


package com.translineindia.vms.config;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionUtil {

	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
	private static final byte[] KEY = "MySuperSecretKey".getBytes();

	public static String encrypt(String data) {
		if (data == null) {
			return null;
		}
		try {
			byte[] iv = new byte[16];
			SecureRandom secureRandom = new SecureRandom();
			secureRandom.nextBytes(iv);

			SecretKey secretKey = new SecretKeySpec(KEY, "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

			byte[] encryptedData = cipher.doFinal(data.getBytes());
			String ivBase64 = Base64.getEncoder().encodeToString(iv);
			String encryptedBase64 = Base64.getEncoder().encodeToString(encryptedData);
			String res = ivBase64 + ":" + encryptedBase64;
			return res;

		} catch (Exception e) {
			throw new RuntimeException("Error while encrypting data", e);
		}
	}

	public static String decrypt(String encryptedDataWithIv) {
		if (encryptedDataWithIv == null || !encryptedDataWithIv.contains(":")) {
			System.out.println("Received Data: " + encryptedDataWithIv);
			throw new IllegalArgumentException(
					"Invalid encrypted data format. Expected format: ivBase64:encryptedBase64");
		}

		try {
			String[] parts = encryptedDataWithIv.split(":");
			if (parts.length != 2) {
				throw new IllegalArgumentException(
						"Invalid encrypted data format. Expected two parts separated by ':'.");
			}

			String ivBase64 = parts[0];
			String encryptedBase64 = parts[1];
			byte[] iv = Base64.getDecoder().decode(ivBase64);
			byte[] encryptedData = Base64.getDecoder().decode(encryptedBase64);
			SecretKey secretKey = new SecretKeySpec(KEY, "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
			byte[] decryptedData = cipher.doFinal(encryptedData);
			return new String(decryptedData);

		} catch (Exception e) {
			throw new RuntimeException("Error while decrypting data", e);
		}
	}
	

    public static String encrypt2(String attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey key = new javax.crypto.spec.SecretKeySpec(KEY, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedValue = cipher.doFinal(attribute.getBytes());
            return Base64.getEncoder().encodeToString(encryptedValue); // Convert encrypted bytes to base64 string
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting value", e);
        }
    }

    public static String decrypt2(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey key = new javax.crypto.spec.SecretKeySpec(KEY, "AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedValue = cipher.doFinal(Base64.getDecoder().decode(dbData)); // Decrypt the base64 string
            return new String(decryptedValue);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting value", e);
        }
    }
    

}

