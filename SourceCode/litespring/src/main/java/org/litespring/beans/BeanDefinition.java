package org.litespring.beans;

public interface BeanDefinition {
    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "prototype";
    public static final String SCOPE_DEFAULT = "singleton";
    public boolean isSingleton();
    public boolean isPrototype();
    String getScope();
    void setScope(String scope);
    String getBeanClassName();
}
