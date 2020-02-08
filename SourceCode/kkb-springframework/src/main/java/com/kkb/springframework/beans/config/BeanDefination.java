package com.kkb.springframework.beans.config;

import java.util.ArrayList;
import java.util.List;

public class BeanDefination {

    private String beanName;
    private String beanClassName;
    private String initMethod;

    private List<PropertyValue> propertyValues = new ArrayList<>();

    public BeanDefination(String clazz, String beanName){
        this.beanClassName = clazz;
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void addPropertyValues(PropertyValue propertyValue) {
        this.propertyValues.add(propertyValue);
    }
}
