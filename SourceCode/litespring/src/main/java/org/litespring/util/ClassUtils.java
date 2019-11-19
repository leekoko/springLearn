package org.litespring.util;

public class ClassUtils {
    /**
     * 获取ClassLoader，用来装载类
     * 此工具类来源于Spring
     * @return
     */
    public static ClassLoader getDefaultClassLoader(){
        ClassLoader cl = null;
        try{
            cl = Thread.currentThread().getContextClassLoader();
        }catch (Throwable ex){

        }
        if(cl == null){
            cl = ClassUtils.class.getClassLoader();
            if(cl == null){
                try{
                    cl = ClassLoader.getSystemClassLoader();
                }catch (Throwable ex){

                }
            }

        }
        return  cl;
    }


}
