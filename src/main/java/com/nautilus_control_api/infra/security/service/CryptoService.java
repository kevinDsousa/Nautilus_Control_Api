package com.nautilus_control_api.infra.security.service;

public interface CryptoService {
    String encrypt(String plainText);
    String decrypt(String cipherText);
}
