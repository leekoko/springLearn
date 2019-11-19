import com.atguigu.bean.Person;
import com.atguigu.config.MainConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {
    /**
     * 通过配置文件注册Bean
     */
    @Test
    public void xmlBeanTest(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Person person = (Person)applicationContext.getBean("person");
        System.out.println(person);
    }

    /**
     * 通过java配置注册Bean
     */
        @Test
        public void configBeanTest(){
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
            Person person = (Person)applicationContext.getBean("person");
            System.out.println(person);

        String[] namesFoeType = applicationContext.getBeanNamesForType(Person.class);
        for (String name : namesFoeType) {
            System.out.println(name);
        }
    }



}
