# Spring深度解析   

## @Configuration

### 添加bean到容器中

1. 通过配置文件加载

   ```xml
   	<bean id="person" class="com.atguigu.bean.Person">
           <property name="age" value="18"></property>
           <property name="name" value="zhangsan"></property>
       </bean>
   ```

   ```java
           ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
           Person person = (Person)applicationContext.getBean("person");
           System.out.println(person);
   ```

2. 通过配置类加载

   ```java
   @Configuration
   public class MainConfig {
       /**
        * 容器中注册bean
        * 类型为返回值的类型，id默认是方法名
        * @return
        */
       @Bean("person")  //指定bean的id
       public Person person01(){
           return new Person("lisi", 21);
       }
   
   }
   ```

   ```java
       @Test
       public void configBeanTest(){
           AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
           Person person = (Person)applicationContext.getBean("person");
           System.out.println(person);
       }
   ```

## @ComponentScan

1. 通过配置xml指定包扫描

   ```xml
   <bean id="person" class="com.atguigu.bean.Person"/>
   ```

2. 通过@ComponentScan注解指定包扫描

   再MainConfig类上添加``@ComponentScan(value = "com.atguigu")``

### 指定/排除组件

1. excludeFilters指定排除哪些

   ```java
   @ComponentScan(value = "com.atguigu", excludeFilters = {
           @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
   })
   public class MainConfig {
       ...
   ```

   具体用法可以看源码

2. includeFilters指定包含哪些

   需要将useDefaultFilters设置为false

   ```java
   @ComponentScan(value = "com.atguigu",
           includeFilters = {
               @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
           },useDefaultFilters = false)
   public class MainConfig {
       ...
   ```

### Filter

@Filter的规则有以下多种，还可以自定义Filter规则 ``@ Filter(type=FilterType.XXX``

- ASSIGNABLE_TYPE：指定的类型

- ASPECTJ：使用ASPECTJ表达式（少用）

- REGEX：正则表达式

- CUSTOM：自定义过滤规则

  - 自定义的规则必须是TypeFilter的实现类

    实现类的参数：metadataReader读取到当前类的信息，metadataReaderFactory可以获取其他类信息

    ```java
    public class MyTypeFilter implements TypeFilter {
        @Override
        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
    
            //获取当前类注解对象
    //        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
            //获取当前类资源
    //        Resource resource = metadataReader.getResource();
    
            //获取当前正在扫描类信息
            ClassMetadata classMetadata = metadataReader.getClassMetadata();
            String className = classMetadata.getClassName();
            if(className.contains("er")){
                return true;
            }
            return false;
        }
    }
    ```

    测试类:指定包下的所有文件都会被扫描到

    ```java
    @ComponentScan(value = "com.atguigu",
            includeFilters = {
    //            @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
                    @ComponentScan.Filter(type = FilterType.CUSTOM, classes = MyTypeFilter.class)
            },useDefaultFilters = false)
    ```

## @Scope

作用域注解添加在Bean类上面，可以指定以下类型：

- singleton：默认方式，单实例方式；属于饿汉单例模式。
  - 懒加载模式：添加注解@Lazy
- prototype：多实例；获取的时候创建；
- request：同一次请求一个实例；
- session：同一个session一个实例；

## @Conditional 

条件判断加载组件注解，可以加载方法上面、也可以加在类上面。



```java
    @Conditional({WindowCondition.class})   //判断是否加载组件
    @Bean("person")  //指定bean的id
    public Person person01(){
        return new Person("lisi", 21);
    }
```

判断条件的类实现Condition接口，实现类的方法参数：

- context条件的上下文环境：可以获取ioc使用的beanFactory，类加载器，当前环境信息，bean定义的注册类
- AnnotatedTypeMetadata注释信息

```java
public class WindowCondition implements Condition {
    
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();
        String property = environment.getProperty("os.name");
        if(property.contains("Windows")){
            return true;
        }
        return false;
    }
}
```

## @ Import

### 直接引入Bean

@ Import({Color.class,Red.class})

### 批量导入Bean

定义类实现ImportSelector，实现方法返回数组，数组中带全类名。@ Import注解中导入定义类

### 手动注册Bean

定义类实现ImportBeanDefinitionRegistrar，实现方法手动注册Bean。可以指定Bean名，作用域，Bean类型。@ Import注解中导入定义类



