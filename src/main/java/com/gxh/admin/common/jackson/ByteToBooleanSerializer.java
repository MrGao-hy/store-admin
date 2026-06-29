package com.gxh.admin.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class ByteToBooleanSerializer extends JsonSerializer<Byte> {
    @Override
    public void serialize(Byte value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            // 1 → true，0 → false
            gen.writeBoolean(value == 1);
        }
    }
}