package org.example;

import org.example.annotation.InitMethod;

public class Demo {

    @InitMethod
    public int init() {
        System.out.println("init() called");
        return 0;
    }

    public Demo() {
        System.out.println("Demo() called");
    }
}

