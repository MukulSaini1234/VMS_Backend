package com.transline.VMS.config;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128; // Use 128, 192, or 256 (requires JCE policy for >128)

    /**
     * Generates a new AES key.
     * 
     * @return The generated AES key as a Base64-encoded string.
     */
    public static String generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(KEY_SIZE);
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * Encrypts the given plaintext using AES and the provided key.
     * 
     * @param plaintext The plaintext to encrypt.
     * @param key       The AES key (Base64-encoded).
     * @return The encrypted text (Base64-encoded).
     */
    public static String encrypt(String plaintext, String key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts the given ciphertext using AES and the provided key.
     * 
     * @param ciphertext The encrypted text (Base64-encoded).
     * @param key        The AES key (Base64-encoded).
     * @return The decrypted plaintext.
     */
    public static String decrypt(String ciphertext, String key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes);
    }

    // Example Usage
    public static void main(String[] args) {
        try {
            // Generate a new AES key
            String secretKey = generateKey();
            System.out.println("Generated Key: " + secretKey);

            // Encrypt data
            String plaintext = "Sensitive Data";
            String encrypted = encrypt(plaintext, secretKey);
            System.out.println("Encrypted: " + encrypted);

            // Decrypt data
            String decrypted = decrypt(encrypted, secretKey);
            System.out.println("Decrypted: " + decrypted);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
