package com.kkb.springframework.beans.config;

import java.io.InputStream;

/**
 * Classpath路径，获取Resource
 * 因为不清楚location是类路径下，还是磁盘下，还是网络下。所以直接通过资源Resource来获取
 */
public class ClasspathResource implements Resource {

    private String location;

    public ClasspathResource(String location){
        //通过构造方法传参
        this.location = location;
    }

    /**
     * 根据文件路径获取IO流
     * 路径不同，流是通用的
     * @return
     */
    @Override
    public InputStream getInputStream() {
        if(location == null || location.equals("")){
            //路径不能读
            return null;
        }
        //...其他路径的资源判断

        InputStream inputStream = ClasspathResource.class.getClassLoader().getResourceAsStream(location);
        return inputStream;
    }
}
