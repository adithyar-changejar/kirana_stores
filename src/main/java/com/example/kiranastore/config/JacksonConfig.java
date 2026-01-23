package com.example.kiranastore.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new JavaTimeModule());

        // ObjectId serialization
        SimpleModule objectIdModule = new SimpleModule();
        objectIdModule.addSerializer(
                ObjectId.class,
                new com.fasterxml.jackson.databind.JsonSerializer<>() {
                    @Override
                    public void serialize(
                            ObjectId value,
                            com.fasterxml.jackson.core.JsonGenerator gen,
                            com.fasterxml.jackson.databind.SerializerProvider serializers
                    ) throws java.io.IOException {
                        gen.writeString(value.toHexString());
                    }
                }
        );

        mapper.registerModule(objectIdModule);
        return mapper;
    }
}
