package com.fengling;

import org.apache.catalina.mbeans.MBeanFactory;
import org.apache.commons.configuration.beanutils.BeanDeclaration;
import org.apache.commons.configuration.beanutils.BeanFactory;

import java.util.Collections;
import java.util.HashMap;

public class test implements BeanFactory{
    public static void main(String[] args) {
//        new HashMap<>().put()
//        new Collections.unmodifiableCollection()
        String s1 = new String("1") + new String("1");
        System.out.println(s1);
        String s2 = "11";
        System.out.println(s1 == s2);
//        BeanFactory
    }

    @Override
    public Object createBean(Class<?> aClass, BeanDeclaration beanDeclaration, Object o) throws Exception {
        return null;
    }

    @Override
    public Class<?> getDefaultBeanClass() {
        return null;
    }
}
