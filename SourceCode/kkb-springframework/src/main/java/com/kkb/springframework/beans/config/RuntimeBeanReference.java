package com.kkb.springframework.beans.config;

public class RuntimeBeanReference {

    private String ref;

    public RuntimeBeanReference(String ref){
        super();
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
