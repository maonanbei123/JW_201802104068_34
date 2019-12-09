package com.example.demo3.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonChange {
    public static Object jsonToObj(Object object,String jsonStr) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return object = mapper.readValue(jsonStr, object.getClass());
    }
    public static String objToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
