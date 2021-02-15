package com.charlie.course9;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * 创建Spring Bean的方式
 *
 * @author Charlie
 * @date 2021/2/5
 */

/**
 * 1.通过Import注解引入
 */
@Import(Person.class)
@Configuration
public class SpringBeanCreation {

    public static void main(String[] args) {
        annotationContext();
        xmlContext();
    }

    /**
     * XML启动spring
     */
    public static void xmlContext() {
        // 2.xml加载bean
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring-beans.xml");
        Person bean = context.getBean(Person.class);
        System.out.println(bean);
    }

    /**
     * 注解启动spring
     */
    public static void annotationContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringBeanCreation.class);
        context.refresh();

        // 3.通过@component注解创建
        context.scan("com.charlie");

        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
        registerBeanDefinition(beanFactory);

        Map<String, Person> beans = beanFactory.getBeansOfType(Person.class);
        System.out.println(beans);

        context.close();
    }

    /**
     * 4.通过@Bean注解创建
     */
    @Bean
    public Person byBeanAnnotation() {
        return new Person();
    }

    /**
     * 5.通过注册BeanDefinition创建
     */
    private static void registerBeanDefinition(DefaultListableBeanFactory beanFactory) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(Person.class);
        AbstractBeanDefinition bd = builder.addPropertyValue("name", "byBeanDefinition").getBeanDefinition();
        beanFactory.registerBeanDefinition("personByRegisterBeanDefinition", bd);
    }

}
