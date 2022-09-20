package org.example.test;

import static org.junit.jupiter.api.Assertions.*;

import org.example.Demo;
import org.example.Main;
import org.junit.jupiter.api.Test;

class MainTest extends Main {

    @Test
    void testNormal() {
        assertEquals(0, tryReflection("/myapp.properties"));
    }

    @Test
    void testNoFile() {
        assertEquals(2, tryReflection("/foo"));
    }

    @Test
    void testNoBootstrapClass() {
        assertEquals(3, tryReflection("/myapp.noBootstrap.properties"));
    }

    @Test
    void testNoInitMethod() {
        assertEquals(1, tryReflection("/myapp.invalidBootstrap.properties"));
    }

    @Test
    void testNoClass() {
        assertEquals(4, tryReflection("/myapp.invalidClass.properties"));
    }

    @Test
    void testWrongArgument() {
        assertEquals(5, tryReflection("/myapp.wrongArgument.properties"));
    }
}