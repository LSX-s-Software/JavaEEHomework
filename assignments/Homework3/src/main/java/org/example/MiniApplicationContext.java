package org.example;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.example.exceptions.IllegalBeanException;

public class MiniApplicationContext {
    private Map beans = new HashMap<String, Object>();
    private Map beanConfigs = new HashMap<String, Element>();

    public Object getBean(String name) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        Object bean = beans.get(name);
        if (bean != null) {
            return bean;
        }
        Object beanConfig = beanConfigs.get(name);
        if (beanConfig == null) {
            return null;
        }
        Object newBean = initBean((Element) beanConfig);
        beans.put(name, newBean);
        return newBean;
    }

    private Object initBean(Element element) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        String targetClass = element.attributeValue("class");
        if (targetClass == null) {
            throw new IllegalBeanException("class attribute is required");
        }
        Class beanClass = Class.forName(targetClass);
        Object beanInstance = beanClass.getConstructor().newInstance();
        for (Iterator<Element> it = element.elementIterator(); it.hasNext();) {
            Element propertyElement = it.next();
            String propertyName = propertyElement.attributeValue("name");
            if (propertyName == null) {
                throw new IllegalBeanException("name attribute is required");
            }
            Class propertyType = beanClass.getDeclaredField(propertyName).getType();
            String propertyValue = propertyElement.attributeValue("value");
            String propertyRef = propertyElement.attributeValue("ref");
            String setterMethodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
            if (propertyValue != null) {
                try {
                    if (int.class.equals(propertyType)) {
                        beanClass.getMethod(setterMethodName, int.class).invoke(beanInstance, Integer.parseInt(propertyValue));
                    } else if (String.class.equals(propertyType)) {
                        beanClass.getMethod(setterMethodName, String.class).invoke(beanInstance, propertyValue);
                    } else if (boolean.class.equals(propertyType)) {
                        beanClass.getMethod(setterMethodName, boolean.class).invoke(beanInstance, Boolean.parseBoolean(propertyValue));
                    } else if (float.class.equals(propertyType)) {
                        beanClass.getMethod(setterMethodName, float.class).invoke(beanInstance, Float.parseFloat(propertyValue));
                    } else if (double.class.equals(propertyType)) {
                        beanClass.getMethod(setterMethodName, double.class).invoke(beanInstance, Double.parseDouble(propertyValue));
                    } else if (long.class.equals(propertyType)) {
                        beanClass.getMethod(setterMethodName, long.class).invoke(beanInstance, Long.parseLong(propertyValue));
                    } else if (short.class.equals(propertyType)) {
                        beanClass.getMethod(setterMethodName, short.class).invoke(beanInstance, Short.parseShort(propertyValue));
                    } else if (byte.class.equals(propertyType)) {
                        beanClass.getMethod(setterMethodName, byte.class).invoke(beanInstance, Byte.parseByte(propertyValue));
                    } else if (char.class.equals(propertyType)) {
                        beanClass.getMethod(setterMethodName, char.class).invoke(beanInstance, propertyValue.charAt(0));
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalBeanException("value attribute is not a valid number");
                }
            } else if (propertyRef != null) {
                Object bean = getBean(propertyRef);
                try {
                    Method exactMatchMethod = beanClass.getMethod(setterMethodName);
                    exactMatchMethod.invoke(beanInstance, bean);
                } catch (NoSuchMethodException e) {
                    Method[] methods = beanClass.getMethods();
                    for (Method method : methods) {
                        if (method.getName().equals(setterMethodName)) {
                            method.invoke(beanInstance, bean);
                            break;
                        }
                    }
                }
            } else {
                throw new IllegalBeanException("value or ref attribute is required");
            }
        }
        if (element.attributeValue("init-method") != null) {
            String initMethodName = element.attributeValue("init-method");
            beanClass.getDeclaredMethod(initMethodName).invoke(beanInstance);
        }
        return beanInstance;
    }

    public void init(String xmlName) throws DocumentException {
        SAXReader reader = new SAXReader();
        InputStream inFile = this.getClass().getClassLoader().getResourceAsStream(xmlName);
        Document document = reader.read(inFile);
        Element root = document.getRootElement();
        for (Iterator<Element> it = root.elementIterator("bean"); it.hasNext();) {
            Element bean = it.next();
            beanConfigs.put(bean.attributeValue("id"), bean);
        }
    }
}
