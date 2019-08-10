package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanFactory;

public class DefaultBeanFactory implements BeanFactory {
    public DefaultBeanFactory(String configFile) {
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanID) {
        return null;
    }

    @Override
    public Object getBean(String petStore) {
        return null;
    }
}
