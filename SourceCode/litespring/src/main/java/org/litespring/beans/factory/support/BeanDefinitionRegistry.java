package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * 负责从xml解析bean
 */
public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String beanID);

    void registerBeanDefinition(String beanID, BeanDefinition bd);

}
