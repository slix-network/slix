package io.natix.slix.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonUtil {

    public static String toString(Object value) {
        try {
            ObjectMapper Obj = new ObjectMapper();
            return Obj.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public static <T> T toObject(String value, Class<T> valueType) {
        try {
            ObjectMapper Obj = new ObjectMapper();
            Obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return Obj.readValue(value, valueType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static <T> T toObject(Object value, Class<T> valueType) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.convertValue(value, valueType);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
