package com.gxh.admin.common.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class BooleanToByteDeserializer extends JsonDeserializer<Byte> {
    @Override
    public Byte deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Boolean bool = p.readValueAs(Boolean.class);
        return Boolean.TRUE.equals(bool) ? (byte) 1 : (byte) 0;
    }
}