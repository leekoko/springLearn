# IOC依赖

IOC(Inversion of Controller)控制反转，IOC是一种目标，而DI（Dependency Injection）是实现IOC的方式（DL，Dependency Look，依赖拖拽 也是实现IOC的方式）   

控制反转的作用：将对象的产生交给Spring容器去实现，Spring容器还可以帮你在对象上加事务。（不用自己手动添加、维护）

```java
public class InternallyPilotingWebServiceImpl implements InternallyPilotingWebService {

    @Autowired
    private IPolicyCashUserApplyService policyCashUserApplyService;
    ...
```

``InternallyPilotingWebServiceImpl``依赖``policyCashUserApplyService``，没有这个属性，该类就失去了意义。

## Spring DI

Spring注入方式有两种：基于构造函数注入 & Setter依赖注入

- Setter依赖注入

  ```java
  private IndexDao dao;
  
  public void setDao(IndexDao dao) {
      this.dao = dao;
  }
  ```

  xml注入配置

  ```xml
  <bean id="dao" class="com.luban.dao.IndexDaoImpl"></bean>
  
  <bean id="service" class="com.luban.dao.IndexService">
      <property name="dao" ref="dao"></property>
  </bean>
  ```

- 构造函数注入

  ```java
  private IndexDao dao;
  
  public IndexService(IndexDao dao) {
      this.dao = dao;
  }
  ```

  xml注入配置

  ```xml
  <bean id="dao" class="com.luban.dao.IndexDaoImpl"></bean>
  
  <bean id="service" class="com.luban.dao.IndexService">
      <constructor-arg name="dao" ref="dao"></constructor-arg>
  </bean>
  ```

获取Spring容器

```java
ClassPathXmlApplicationContext classPathXmlApplicationContext
                = new ClassPathXmlApplicationContext("classpath:spring.xml");
IndexService service = (IndexService) classPathXmlApplicationContext.getBean("service");
service.service();
```

除了可以注入容器的对象，还可以注入静态值

```xml
    <bean id="dao" class="com.luban.dao.IndexDaoImpl">
        <property name="str" value="this is DI content"></property>
    </bean>
```

spring提供了简化xml配置的xsd，引入xsd，就可以用c，p（c代表constructor，p代表property）等命名空间配置。更多详情，查看[文档](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-p-namespace)  

### 自动装配

自动装配有好几种方式：no，byType，byName

- xml使用自动装配

  在xml配置头部的``<beans>``标签中，添加default-autowire=“byType”属性，容器就会根据注入对象的Type去全局寻找该对象，然后装配到注入对象中。（但是如果该对象属性``private IndexDao dao;``，``IndexDao``存在多个实现类，加载对象到容器时就会报错）

- 注解使用自动装配

  @Autowire默认使用的是byType，如果Type找不到，就找byName

  @Resource默认 使用的是byName，根据属性名来找bean(``private IndexDao indexDaoImpl``的属性名是indexDaoImpl)
  
  也可以给注解添加name，type属性，手动指定要byName还是byType

## 注解

除了可以用xml声明bean对象，还可以使用注解。要使用注解，需要先开启注解，开启注解的方式有两种：

### 使用xml开启注解

使用Autowrite步骤如下：

1. 需要开启Spring注解配置(``<context:annotation-config></context:annotation-config>``)，与添加扫描包``<context:component-scan base-package="com"></context:component-scan>``（已包含开启）
2. 添加``@Component``的类会被添加到Spring容器中
3. 添加``@Autowrite``的对象自动被注入容器的内容，Autowrite是通过反射来设置属性值

### 使用javaConfig开启注解

使用javaConfig替代xml开启注解功能（SpringBoot用的就是javaConfig）

```java
@Configuration
@ComponentScan("com.luban.*")
public class SpringConfig {
    
}
```

使用java配置需要换AnnotationConfigApplicationContext读取

```java
AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
IndexService service = (IndexService) annotationConfigApplicationContext.getBean("indexService");
```

通过``@ImportResource("classpath:spring.xml")``可以加载xml配置文件，实现混合使用

### @Component

现在的@Repository，@Service，@Controller，@Component从功能上来看时一样的，但在未来Spring可能会有所区分。

### @Scope

作用域注解可以指定bean的作用域：

- singleton：单例，注入的对象一直都是一样的（Spring默认是singleton作用域）
- prototype：原型，每次注入的对象都不同

**singleton模式使用prototype引发的失效问题**

如果类A声明了singleton，而其依赖属性类B就算声明了prototype，也是singleton域。因为类A只生成一次。

如果要让类B生成多次，只能再次从Application容器取对象。

Spring也针对这种请客做了简化处理：

1. 将该类改为abstract类

2. 添加抽象方法

   ```java
   @Lookup("【bean名】")
   public abstract IndexDao getDao();
   ```

   去寻找指定bean，而不是一开始就注入  

## 文档属性说明

来自于Spring官网的[Callback](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-factory-lifecycle)文档阅读   

### 生命周期Callback

有三种方式可以实现生命周期回调

#### 1.实现接口

- Initialization Callbacks初始化回调

  继承``org.springframework.beans.factory.InitializingBean``接口，实现的``void afterPropertiesSet() throws Exception;``方法会在bean初始化后执行。不方便在构造方法执行的代码可以在``afterPropertiesSet()``中写。

- Destruction Callbacks销毁回调

  继承`org.springframework.beans.factory.DisposableBean`接口，实现``void destroy() throws Exception;`` 方法会在bean销毁之后执行。

#### 2.xml配置指定`init()` and `destroy()`方法 

#### 3.添加注解

方法上添加 `@PostConstruct` 和 `@PreDestroy`注解

### depends on依赖

添加depends on到bean上，指明依赖。明显的依赖不需要指明，而如果类A用到类B的某个属性，就需要先初始化类B，这个时候就要手动指明依赖类

### @Lazy

添加在类上面，只有在用到该类的时候才调用构造方法进行实例化。



















 

https://shimo.im/docs/uKMbBVwL3dUusKHV/read



https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-factory-lifecycle



子路  视频2   40min  怎么结合文档、源码进行学习