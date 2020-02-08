package com.kkb.springframework.beans.converter;

public interface TypeHandler {

    boolean isHandleThisType(Class<?> targetType);

    Object convertValue(String stringValue);

}
