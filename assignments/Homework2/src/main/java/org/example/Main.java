package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import org.example.annotation.InitMethod;

public class Main {
    public static void main(String[] args) {}

    public static int tryReflection(String propertiesFileName) {
        try (InputStream input = Main.class.getResourceAsStream(propertiesFileName)) {
            Properties props = new Properties();
            props.load(input);
            Class targetClass = Class.forName(props.getProperty("bootstrapClass"));
            Method methods[] = targetClass.getMethods();
            Object obj = targetClass.getConstructor().newInstance();
            for (Method method : methods) {
                if (method.isAnnotationPresent(InitMethod.class)) {
                    Method initMethod = targetClass.getMethod(method.getName());
                    initMethod.invoke(obj);
                    return 0;
                }
            }
            System.out.println("No method annotated with \"@InitMethod\" has been found");
            return 1;
        } catch (FileNotFoundException e) {
            System.out.println("properties file not found");
            return 2;
        } catch (NullPointerException e) {
            System.out.println("field \"bootstrapClass\" not found in properties file");
            return 3;
        } catch (ClassNotFoundException e) {
            System.out.println("bootstrapClass not found");
            return 4;
        } catch (NoSuchMethodException e) {
            System.out.println("init method not conform to requirements");
            return 5;
        } catch (Exception e) {
            e.printStackTrace();
            return 6;
        }
    }
}