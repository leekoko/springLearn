package com.kkb.springframework.beans.converter;

public class IntegerTypeHandler implements TypeHandler {

    @Override
    public boolean isHandleThisType(Class<?> targetType) {
        return targetType == Integer.class;
    }

    @Override
    public Object convertValue(String stringValue) {
        return Integer.parseInt(stringValue);
    }
}
