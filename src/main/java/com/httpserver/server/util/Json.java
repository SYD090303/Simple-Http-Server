package com.httpserver.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class Json {
    private static ObjectMapper myObjectMapper = defaultObjectMapper();

    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public static JsonNode parse(String jsonSrc) throws IOException {
        return myObjectMapper.readTree(jsonSrc);
    }

    public static <T> T fromJson(JsonNode node, Class<T> clazz) throws IOException {
        return myObjectMapper.treeToValue(node, clazz);
    }

    public static JsonNode toJson(Object obj) throws IOException {
        return myObjectMapper.valueToTree(obj);
    }

    public static String stringifyPretty(JsonNode node) throws IOException {
        return generateJson(node, true);
    }

    public static String stringify(JsonNode node) throws IOException {
        return generateJson(node, false);
    }

    private static String generateJson(Object obj, boolean pretty) throws IOException {
        ObjectWriter writer = myObjectMapper.writer();
        if (pretty) {
            writer = writer.with(SerializationFeature.INDENT_OUTPUT);
        }
        return writer.writeValueAsString(obj);
    }
}
