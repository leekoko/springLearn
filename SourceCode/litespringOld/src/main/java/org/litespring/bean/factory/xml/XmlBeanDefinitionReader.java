package org.litespring.bean.factory.xml;

import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.bean.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;

public class XmlBeanDefinitionReader {
	
	public static final String ID_ATTRIBUTE = "id";
	public static final String CLASS_ATTRIBUTE = "class";
	
	BeanDefinitionRegistry registry;
	
	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
		this.registry = registry;
	}
	/**
	 * ����xml(ְ����룬Ų��xml��������)
	 * @param configFile
	 */
	public void loadBeanDefinition(Resource resource) {
		InputStream is = null;
		try {
//			ClassLoader cl = ClassUtils.getDefaultClassLoader();   //��ȡclassLoader
//			is = cl.getResourceAsStream(configFile); //��ȡ�����ļ�
			is = resource.getInputStream();
			SAXReader reader = new SAXReader();  //dom4j����xml�ļ�
			Document doc = reader.read(is);   //��ȡ��Document�ļ�
			
			Element root = doc.getRootElement();  //<beans>
			Iterator<Element> iter = root.elementIterator();
			while(iter.hasNext()){
				Element ele = (Element)iter.next();
				String id = ele.attributeValue(ID_ATTRIBUTE);
				String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
				BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
				this.registry.registerBeanDefinition(id, bd);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BeanDefinitionStoreException("IOException parsing XML document", e);
		}finally {
			if(is != null){
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
