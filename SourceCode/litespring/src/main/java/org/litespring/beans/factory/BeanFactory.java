package org.litespring.beans.factory;

/**
 * 负责获取bean
 */
public interface BeanFactory {
    Object getBean(String petStore);
}
