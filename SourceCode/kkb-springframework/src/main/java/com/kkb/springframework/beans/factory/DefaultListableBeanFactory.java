package com.kkb.springframework.beans.factory;

import com.kkb.springframework.beans.config.*;
import com.kkb.springframework.beans.converter.IntegerTypeHandler;
import com.kkb.springframework.beans.converter.StringTypeHandler;
import com.kkb.springframework.beans.converter.TypeHandler;
import com.kkb.springframework.beans.utils.ReflectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * bean工厂类
 */
public class DefaultListableBeanFactory implements BeanFactory {

    private Map<String, Object> singletonObjects = new HashMap<>();
    private Map<String, BeanDefination> beanDefinations = new HashMap<>();

    private List<TypeHandler> typeHandlers = new ArrayList<>();

    /**
     * 添加封装后BeanDefination对象到集合中
     * @param beanName
     * @param bd
     */
    public void registerBeanDefination(String beanName, BeanDefination bd){
        this.beanDefinations.put(beanName, bd);
    }

    public DefaultListableBeanFactory(Resource resource){
        //加载xml，读取bean信息，并将bean对象存储起来
        XmlBeanDefinationReader beanDefinationReader = new XmlBeanDefinationReader(this);
        beanDefinationReader.loadBeanDefinations(resource);

        //初始化TypeHandler
        initTypeHandler();
    }

    private void initTypeHandler() {
        //此处需要使用配置文件动态配置，需要对配置文件进行解析
        typeHandlers.add(new IntegerTypeHandler());
        typeHandlers.add(new StringTypeHandler());

    }

    @Override
    public Object getBean(String beanName) {
        //单例工厂：判断是否已存在bean实例
        Object beanInstance = singletonObjects.get(beanName);
        if(beanInstance != null){
            return beanInstance;
        }
        //bean实例为空，进行对象创建流程
        //获取BeanDefinition信息
        BeanDefination beanDefination = this.getBeanDefinations().get(beanName);
        if(beanDefination == null){
            return null;
        }
        beanInstance = createBean(beanDefination);
        //将创建好的对象，放入singletonObjects集合中

        return beanInstance;
    }

    @Override
    public Object getBean(Class<?> clazz) {
        return null;
    }

    private Object createBean(BeanDefination beanDefination) {
        //创建对象，分为三步：实例化、属性填充、初始化
        //实例化：new 对象（使用反射去创建实例）

        Object instanceObject =  newObject(beanDefination, null);
        //属性填充：set方法
        setProperties(beanDefination, instanceObject);
        //初始化：调用初始化方法
        initObject(beanDefination, instanceObject);

        return instanceObject;
    }

    private void initObject(BeanDefination beanDefination, Object instanceObject) {
        String initMethod = beanDefination.getInitMethod();

        if(initMethod == null || "".equals(initMethod)){
            return;
        }
        ReflectUtils.invokeMethod(instanceObject, initMethod);
    }

    private void setProperties(BeanDefination beanDefination, Object instanceObject) {
        //获取PropertyValue集合
        List<PropertyValue> propertyValues = beanDefination.getPropertyValues();
        //遍历集合，每个PropertyValue进行赋值操作
        if(propertyValues != null && propertyValues.size() > 0){
            for (PropertyValue propertyValue : propertyValues) {
                setProperty(propertyValue, instanceObject);
            }
        }
    }

    private Object newObject(BeanDefination beanDefination, Object[] initargs) {
        Object object = ReflectUtils.createObject(beanDefination.getBeanClassName(), initargs);
        return object;
    }


    /**
     * 设置参数
     */
    private void setProperty(PropertyValue propertyValue, Object instanceObject) {
        //取出name和value属性，只是说value是Object类型的
        String name = propertyValue.getName();
        Object value = propertyValue.getValue();
        //真正的value，包括TypedStringValue、RuntimeBeanReference
        //以上的value值，无法直接赋值操作，需要先处理
        //调用反射进行赋值操作
        if(name == null || "".equals(name) || value == null || "".equals(value)){
            return;
        }
        //此值是进行类型转换之后的值
        Object valueToUse = null;
        if(value instanceof TypedStringValue){
            TypedStringValue typedStringValue = (TypedStringValue) value;
            String stringValue = typedStringValue.getValue();
            Class<?> targetType = typedStringValue.getTargetType();
            valueToUse = getConvertedValue(stringValue, targetType, valueToUse);

        }else if(value instanceof RuntimeBeanReference){
            RuntimeBeanReference runtimeBeanReference = (RuntimeBeanReference) value;
            String ref = runtimeBeanReference.getRef();
            //递归调用获取依赖对象
            valueToUse = getBean(ref);
        }
        //利用反射进行属性赋值操作
        ReflectUtils.setProperty(instanceObject, name, valueToUse);
    }

    private Object getConvertedValue(String stringValue, Class<?> targetType, Object valueToUse) {
/*        if(targetType == Integer.class){
            valueToUse = Integer.parseInt(stringValue);
        }else if(targetType == String.class){
            valueToUse = stringValue;
        }*/
        //......

        //使用策略模式改造if...else
        for (TypeHandler typeHandler : typeHandlers) {
            if(typeHandler.isHandleThisType(targetType)){
                valueToUse = typeHandler.convertValue(stringValue);
            }
        }
        return valueToUse;
    }

    public Map<String, BeanDefination> getBeanDefinations() {
        return beanDefinations;
    }
}
