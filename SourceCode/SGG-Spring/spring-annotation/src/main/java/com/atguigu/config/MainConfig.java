package com.atguigu.config;

import com.atguigu.bean.Person;
import com.atguigu.condition.WindowCondition;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * 配置类\
 * 告诉spring，这是一个配置类
 */
@Configuration
@ComponentScan(value = "com.atguigu",
        includeFilters = {
//            @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
//                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = MyTypeFilter.class)
        },useDefaultFilters = false)
public class MainConfig {
    /**
     * 容器中注册bean
     * 类型为返回值的类型，id默认是方法名
     * @return
     */
    @Conditional({WindowCondition.class})   //判断是否加载组件
    @Bean("person")  //指定bean的id
    public Person person01(){
        return new Person("lisi", 21);
    }

}
