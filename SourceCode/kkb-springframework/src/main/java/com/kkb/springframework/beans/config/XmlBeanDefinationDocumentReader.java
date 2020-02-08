package com.kkb.springframework.beans.config;

import com.kkb.springframework.beans.factory.DefaultListableBeanFactory;
import com.kkb.springframework.beans.utils.ReflectUtils;
import org.dom4j.Element;

import java.util.List;

/**
 * 专门根据spring的语义，来解析document对象
 */
public class XmlBeanDefinationDocumentReader {

    private DefaultListableBeanFactory beanFactory;

    //逐级传递，拿到工厂进行注册
    public XmlBeanDefinationDocumentReader(DefaultListableBeanFactory factory){
        this.beanFactory = factory;
    }

    public void loadBeanDefinations(Element rootElement) {
        //获取bean标签和自定义标签（比如mvc:interceptors）
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            //获取标签名称
            String name = element.getName();
            if(name.equals("bean")){
                parseDefaultElement(element);
            }else{
                parseCustomElement(element);
            }
        }
    }

    private void parseCustomElement(Element element) {

    }


    private void parseDefaultElement(Element beanElement){
        try {
            if(beanElement == null){
                return;
            }

            String id = beanElement.attributeValue("id");
            String name = beanElement.attributeValue("name");
            String clazzName = beanElement.attributeValue("class");
            Class<?> clazzType = Class.forName(clazzName);
            //获取init-method属性
            String initMethod = beanElement.attributeValue("init-method");

            String beanName = id == null ? name : id;
            beanName = beanName == null ? clazzType.getSimpleName() : beanName;
            //创建BeanDefinition对象
            BeanDefination beanDefination = new BeanDefination(clazzName, beanName);
            beanDefination.setInitMethod(initMethod);
            //获取property子标签集合
            List<Element> propertyElements = beanElement.elements();
            for (Element propertyElement : propertyElements) {
                parsePropertyElement(beanDefination, propertyElement);
            }
            //注册BeanDefinition信息
            registerBeanDefinition(beanName, beanDefination);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    private void parsePropertyElement(BeanDefination beanDefination, Element propertyElement) {
        if(propertyElement == null){
            return;
        }

        String name = propertyElement.attributeValue("name");
        String value = propertyElement.attributeValue("value");
        String ref = propertyElement.attributeValue("ref");

        //如果value和ref都有值，则返回
        if(value != null && !value.equals("") && ref != null && !ref.equals("")){
            return;
        }

        PropertyValue pv = null;

        if(value != null && !value.equals("")){
            //因为Spring配置中的value是String类型，而对象中的属性是各种各样的，所以需要存储类型
            TypedStringValue typedStringValue = new TypedStringValue(value);

            Class<?> targetType = ReflectUtils.getTypeByFieldName(beanDefination.getBeanClassName(), name);
            typedStringValue.setTargetType(targetType);

            pv = new PropertyValue(name, typedStringValue);
            beanDefination.addPropertyValues(pv);
        }else if(ref != null && !ref.equals("")){
            RuntimeBeanReference reference = new RuntimeBeanReference(ref);
            pv = new PropertyValue(name, reference);
            beanDefination.addPropertyValues(pv);
        }else{
            return;
        }
    }

    private void registerBeanDefinition(String beanName, BeanDefination beanDefination) {
        beanFactory.registerBeanDefination(beanName, beanDefination);
    }
}
