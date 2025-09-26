package mb.testframeworks.java.unit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;

public class DeserializationTests {
    public static Logger log = LoggerFactory.getLogger(DeserializationTests.class);

    public static ObjectMapper createObjectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, SerializationFeature.WRITE_ENUMS_USING_TO_STRING, SerializationFeature.INDENT_OUTPUT, DeserializationFeature.READ_ENUMS_USING_TO_STRING)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }

    @Test
    public void testBasicDeserialization() throws JsonProcessingException {
        ObjectMapper mapper = createObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.registerModule(new JavaTimeModule());


        String json = "{\"dateTime\":\"2025-04-23T11:45:54\"}";
        MyDto dto = mapper.readValue(json, MyDto.class);
        log.info(String.valueOf(dto.getDateTime()));
    }

    @NoArgsConstructor
    @Getter
    @JsonPropertyOrder({"dateTime"})
    static
    class MyDto {

        @Nullable
        private LocalDateTime dateTime;
    }

}
