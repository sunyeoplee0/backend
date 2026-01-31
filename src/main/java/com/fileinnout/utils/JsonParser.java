package com.fileinnout.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fileinnout.global.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class JsonParser {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule()) // Java 8 날짜/시간 모듈 등록
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    public static<T> T from(HttpServletRequest req, Class<T> clazz) {
        try {
            return objectMapper.readValue(req.getReader(), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String from(BaseResponse res) {
        try {
            return objectMapper.writeValueAsString(res);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
