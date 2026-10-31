package com.nautilus_control_api.infra.security.service.impl;

import com.nautilus_control_api.infra.security.service.CryptoService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class CryptoServiceImpl implements CryptoService {
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH_BITS = 128;
    private static final int IV_LENGTH_BYTES = 12;

    @Value("${app.encryption.key}")
    private String encryptionKey;

    private SecretKeySpec secretKey;
    private final SecureRandom secureRandom = new SecureRandom();

    @PostConstruct
    void init() {
        if (encryptionKey == null || encryptionKey.isBlank()) {
            throw new IllegalStateException(
                    "app.encryption.key (APP_ENCRYPTION_KEY) nao foi definida. " +
                    "Gere uma chave com: openssl rand -base64 32");
        }
        byte[] keyBytes = Base64.getDecoder().decode(encryptionKey);
        if (keyBytes.length != 32) {
            throw new IllegalStateException("app.encryption.key deve ter 32 bytes (AES-256) apos decodificar de base64");
        }
        this.secretKey = new SecretKeySpec(keyBytes, "AES");
    }

    @Override
    public String encrypt(String plainText) {
        if (plainText == null) {
            return null;
        }
        try {
            byte[] iv = new byte[IV_LENGTH_BYTES];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv));
            byte[] cipherBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            byte[] result = new byte[iv.length + cipherBytes.length];
            System.arraycopy(iv, 0, result, 0, iv.length);
            System.arraycopy(cipherBytes, 0, result, iv.length, cipherBytes.length);

            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao criptografar valor", e);
        }
    }

    @Override
    public String decrypt(String cipherText) {
        if (cipherText == null) {
            return null;
        }
        try {
            byte[] decoded = Base64.getDecoder().decode(cipherText);

            byte[] iv = new byte[IV_LENGTH_BYTES];
            byte[] cipherBytes = new byte[decoded.length - IV_LENGTH_BYTES];
            System.arraycopy(decoded, 0, iv, 0, IV_LENGTH_BYTES);
            System.arraycopy(decoded, IV_LENGTH_BYTES, cipherBytes, 0, cipherBytes.length);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv));
            byte[] plainBytes = cipher.doFinal(cipherBytes);

            return new String(plainBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao descriptografar valor", e);
        }
    }
}
