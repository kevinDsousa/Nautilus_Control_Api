package com.nautilus_control_api.infra.persistence.converter;

import com.nautilus_control_api.infra.security.service.CryptoService;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Converter
@Component
@RequiredArgsConstructor
public class EncryptedStringConverter implements AttributeConverter<String, String> {
    private final CryptoService cryptoService;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute == null ? null : cryptoService.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData == null ? null : cryptoService.decrypt(dbData);
    }
}
