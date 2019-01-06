package com.food.sas.security;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Created by ygdxd_admin at 2019-01-06 9:52 PM
 */
public class PasswordSerializer extends JsonSerializer<String> {


    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeBinary(new String("****").getBytes());
    }
}
