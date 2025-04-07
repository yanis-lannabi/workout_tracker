package com.workout.tracker.util;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class JwtKeyGenerator {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // Generate a 256-bit (32-byte) key
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        keyGen.init(256); // Initialize with 256 bits
        SecretKey secretKey = keyGen.generateKey();
        
        // Convert to Base64
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Generated Base64 key: " + base64Key);
        System.out.println("Key length: " + base64Key.length() + " characters");
    }
} 