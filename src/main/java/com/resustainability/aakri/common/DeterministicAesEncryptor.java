package com.resustainability.aakri.common;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.security.SecureRandom;

public class DeterministicAesEncryptor {

    private static final int AES_KEY_BITS = 256;
    private static final int GCM_TAG_BITS = 128;

    // Generated 32-byte key
    private static final String BASE64_KEY = generate32ByteKey();
    
    // 12-byte IV
    private static final byte[] FIXED_IV = "0123456789AB".getBytes(StandardCharsets.UTF_8);

    private static String generate32ByteKey() {
        byte[] key = new byte[32]; // Exactly 32 bytes
        new SecureRandom().nextBytes(key);
        String base64Key = Base64.getEncoder().encodeToString(key);
        System.out.println("Generated 32-byte key: " + base64Key);
        System.out.println("Key length when decoded: " + Base64.getDecoder().decode(base64Key).length + " bytes");
        return base64Key;
    }

    private static SecretKey getFixedKey() {
        byte[] keyBytes = Base64.getDecoder().decode(BASE64_KEY);
        if (keyBytes.length != 32) {
            throw new IllegalArgumentException("Key must be 32 bytes, but got: " + keyBytes.length);
        }
        return new SecretKeySpec(keyBytes, "AES");
    }

    // ... rest of your encrypt/decrypt methods remain the same
    public static String encrypt(String plaintext) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_BITS, FIXED_IV);
        cipher.init(Cipher.ENCRYPT_MODE, getFixedKey(), spec);
        byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String base64CipherText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_BITS, FIXED_IV);
        cipher.init(Cipher.DECRYPT_MODE, getFixedKey(), spec);
        byte[] decoded = Base64.getDecoder().decode(base64CipherText);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws Exception {
        String plain = "hyderabad";
        String encrypted = encrypt(plain);
        String decrypted = decrypt(encrypted);

        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}