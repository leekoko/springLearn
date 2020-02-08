package com.kkb.springframework.beans.factory;

public interface TypeHandler {
    boolean isHandleThisType(Class<?> targetType);

    Object convertValue(String stringValue);
}
