package com.kkb.springframework.beans.config;

import com.kkb.springframework.beans.factory.DefaultListableBeanFactory;
import com.kkb.springframework.beans.utils.DocumentReader;
import org.dom4j.Document;

import java.io.InputStream;

/**
 * 专门读取XML，封装BeanDefination
 * XML的bean定义读取
 */
public class XmlBeanDefinationReader {

    private DefaultListableBeanFactory beanFactory;

    public XmlBeanDefinationReader(DefaultListableBeanFactory beanFactory){
        super();
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinations(Resource resource) {
        //1.定位配置文件(使用面向接口方式)
        InputStream inputStream = resource.getInputStream();
        //2.加载xml配置文件，生产了document
        Document document = DocumentReader.createDocument(inputStream);
        //3.解析xml配置文件，并注册BeanDefinition(专门对document进行解析)
        XmlBeanDefinationDocumentReader beanDefinationDocumentReader = new XmlBeanDefinationDocumentReader(beanFactory);
        beanDefinationDocumentReader.loadBeanDefinations(document.getRootElement()); //从根部开始阅读
    }
}
