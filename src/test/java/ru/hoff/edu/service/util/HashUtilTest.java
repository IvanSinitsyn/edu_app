package ru.hoff.edu.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JsonTest
public class HashUtilTest {

    private HashUtil hashUtil;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        hashUtil = new HashUtil(objectMapper);
    }

    @Test
    void generateHash_shouldReturnValidHash() throws NoSuchAlgorithmException, JsonProcessingException {
        TestDto dto = new TestDto("test", 123);
        String hash = hashUtil.generateHash(dto);

        assertThat(hash).isNotBlank();
        assertThat(hash).doesNotContain("+", "/");

        String expectedJson = objectMapper.writeValueAsString(dto);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] expectedHash = digest.digest(expectedJson.getBytes());
        String expectedBase64Hash = Base64.getUrlEncoder().withoutPadding().encodeToString(expectedHash);

        assertThat(hash).isEqualTo(expectedBase64Hash);
    }

    @Test
    void serialize_shouldReturnValidJson() throws JsonProcessingException {
        TestDto dto = new TestDto("hello", 42);
        String json = hashUtil.serialize(dto);

        assertThat(json).isEqualTo("{\"field1\":\"hello\",\"field2\":42}");
    }

    @Test
    void generateHash_shouldThrowException_whenObjectIsInvalid() {
        assertThatThrownBy(() -> hashUtil.generateHash(new Object()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Ошибка при сериализации");
    }

    static class TestDto {
        public String field1;
        public int field2;

        public TestDto(String field1, int field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
    }
}
