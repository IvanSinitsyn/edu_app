package ru.hoff.edu.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class HashUtil {

    private final ObjectMapper objectMapper;

    public String generateHash(Object dto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String json = serialize(dto);
            byte[] hash = digest.digest(json.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка при генерации хеша", e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка при сериализации", e);
        }
    }

    public String serialize(Object dto) throws JsonProcessingException {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка при сериализации", e);
        }
    }
}
