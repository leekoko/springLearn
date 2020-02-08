package com.kkb.springframework.beans.converter;

public class StringTypeHandler implements TypeHandler {

    @Override
    public boolean isHandleThisType(Class<?> targetType) {
        return targetType == String.class;
    }

    @Override
    public Object convertValue(String stringValue) {
        return stringValue;
    }
}
