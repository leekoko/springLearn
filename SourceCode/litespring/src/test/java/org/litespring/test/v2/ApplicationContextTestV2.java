package org.litespring.test.v2;

import org.junit.Test;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.service.v1.PetStoreService;

public class ApplicationContextTestV2 {

    @Test
    public void testGetBeanProperty(){
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");

        assertNotNull(petStore.getAccountDao());
        assertNotNull(petStore.getItemDao());


    }


}
