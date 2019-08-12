# IOC依赖

IOC(Inversion of Controller)控制反转，IOC是一种目标，而DI（Dependency Injection）是实现IOC的方式（DL（Dependency Look），依赖拖拽 也是实现IOC的方式）   

控制反转的作用：将对象的产生交给Spring容器去实现，Spring容器还可以帮你在对象上加事务。（不用自己手动添加、维护）

```java
public class InternallyPilotingWebServiceImpl implements InternallyPilotingWebService {

    @Autowired
    private IPolicyCashUserApplyService policyCashUserApplyService;
    ...
```

``InternallyPilotingWebServiceImpl``依赖``policyCashUserApplyService``，没有这个属性，该类就失去了意义。

## Spring DI

Spring注入方式有两种：基于构造函数注入（） & Setter依赖注入

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

## Autowrite

使用Autowrite步骤如下：

1. 需要开启Spring注解配置(``<context:annotation-config></context:annotation-config>``)，与添加扫描包``<context:component-scan base-package="com"></context:component-scan>``（已包含开启）
2. 添加``@Component``的类会被添加到Spring容器中
3. 添加``@Autowrite``的对象自动被注入容器的内容

### 使用java替代xml配置

添加配置注解

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













子路  视频1 1:02