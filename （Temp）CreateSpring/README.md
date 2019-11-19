# 从零开始造Spring  

Z：《从零开始造Spring》这是公众号 **码农翻身** 作者刘欣于2018.06.08开的一门课程，利用九周的时间创造一个简单的Spring案例。出于尊重作者的劳动成果考虑，这里将不会公布作者的原版资料，仅做二次解读。这也是对本人学习的复盘和总结，由于本人能力有限，诸多出错，希望能获得各位的指教。   

| **第一周**概述                                  | 加餐：单元测试课程介绍介绍Spring IoC, AOP介绍TDD开发方式，重构的方法Basic BeanFactory(上) |
| ----------------------------------------------- | ------------------------------------------------------------ |
| **第二周**Basic BeanFactory和ApplicationContext | 最简单的结构，基于XML的BeanFactory缺省构造函数的BeanResource，BeanDefinitionRegistry 的抽象Exception的处理BeanDefinition接口单一职责 |
| **第三周**实现setter注入                        | Bean的scope问题，SingletonBeanRegistry接口PropertyValue，RuntimeBeanReference，BeanDefinitionValueResolver的抽象使用IntrospectorTypeConverter 实现从字符串到特定类型的转换从createBean到initiateBean和populateBean的拆分。 |
| **第四周**实现构造函数注入                      | 引入ConstructorArgument如何找到合适的构造器： ConstructorResolverTestCase的整理，引入TestSuite |
| **第五、六周**实现注解和auto-scan               | 注解的讲解使用ASM读取类的Metadata读取一个包下所有的class 作为Resource 新的BeanDefinition实现类引入给auto-scan的Bean 命名： BeanNameGenerator新的抽象： DependencyDescriptor，InjectionMetadata ，InjectedElement用AutowiredAnnotationProcessor实现注入Bean的生命周期BeanPostProcessor接口及其实现和调用 |
| **第七、八、九周**实现AOP                       | 准备工作：讲解CGLib和Java动态代理的原理，讲解PointCut， Advice....等AOP概念实现Pointcut 和 MethodMatcher，MethodLocatingFactory给定一个对象及相应的方法和一系列拦截器(beforeAdvice,afterAdvice)，需要实现正确的调用次序实现CGLibProxyFactory实现“合成”Bean实现对resolveInnerBean使用AspectJAutoProxyCreator 进行封装用Java 动态代理实现AOP课程总结。 |

目录将以周的形式排列：

### 第一周：概述   

#### 1.Junit单元测试   

#### 2.[TDD的Demo](doc/TDDDemo.md)     

#### 3.[Basic BeanFactory（上）](doc/BasicBeanFactoryA.md)  

#### 4.[Basic BeanFactory（下）](doc/BasicBeanFactoryB.md)  



loading