package com.kkb.spring.test;

import com.kkb.spring.po.Student;
import com.kkb.springframework.beans.config.ClasspathResource;
import com.kkb.springframework.beans.config.Resource;
import com.kkb.springframework.beans.factory.BeanFactory;
import com.kkb.springframework.beans.factory.DefaultListableBeanFactory;
import org.junit.Test;

public class TestSpringFramework {

    @Test
    public void test() throws Exception{
        //1.指定xml配置文件路径？是 磁盘文件还是URL网络路径
        String location = "beans.xml";
        //2.将资源封装成Resource，通过该对象获取InputStream
        Resource resource = new ClasspathResource(location);
        //3.创建BeanFactory工厂
        BeanFactory beanFactory = new DefaultListableBeanFactory(resource);
        //4.XmlBeanDefinationReader读取xml文件，并解析，将解析出来的BeanDefinition注册到工厂中的集合
        Student student = (Student)beanFactory.getBean("student");
        System.out.println(student);
    }



}
