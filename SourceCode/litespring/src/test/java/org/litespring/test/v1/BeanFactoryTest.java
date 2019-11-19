package org.litespring.test.v1;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.service.v1.PetStoreService;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * 从XML中获取bean对象
 */
public class BeanFactoryTest {

    DefaultBeanFactory factory = null;
    XmlBeanDefinitionReader reader = null;

    @Before
    public void setUP(){
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }

	@Test
	public void testGetBean() {


		reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));

//		BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");

		BeanDefinition bd = factory.getBeanDefinition("petStore");

		assertTrue(bd.isSingleton());
		assertFalse(bd.isPrototype());
		assertEquals(BeanDefinition.SCOPE_DEFAULT, bd.getScope());

		assertEquals("org.litespring.service.v1.PetStoreService",bd.getBeanClassName());

		PetStoreService petStore = (PetStoreService)factory.getBean("petStore");
		assertNotNull(petStore);

		PetStoreService petStore1 = (PetStoreService)factory.getBean("petStore");

		assertTrue(petStore.equals(petStore));
	}

	@Test
	public void testInvalidBean(){
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));
		try {
			factory.getBean("invalidBean");
		}catch (BeanCreationException e){
			//按照期待抛出了异常
			return;
		}
		Assert.fail("expect BeanCreationException");
	}

	@Test
	public void testInvalidXML(){
		try {
            reader.loadBeanDefinitions(new ClassPathResource("xxxx.xml"));
		}catch (BeanDefinitionStoreException e){
			//按照期待抛出了异常
			return;
		}
		Assert.fail("expect BeanCreationException");
	}


}
