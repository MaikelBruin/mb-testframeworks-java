package mb.testframeworks.java.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class ObjectMapperConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ObjectMapperConfiguration.class);

    public static ObjectMapper createObjectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, SerializationFeature.WRITE_ENUMS_USING_TO_STRING, SerializationFeature.INDENT_OUTPUT, DeserializationFeature.READ_ENUMS_USING_TO_STRING)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }

    @Bean(name = "default-object-mapper")
    public ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = createObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.registerModule(new JavaTimeModule());


        SimpleModule customModule = new SimpleModule();
        customModule.addSerializer(LocalDateTime.class, new JsonSerializer<>() {
            private static final DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

            @Override
            public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                String formattedString = formatter.format(value.atOffset(ZoneOffset.UTC));
                gen.writeString(formattedString);
            }
        });
        mapper.registerModule(customModule);
        return mapper;
    }
}