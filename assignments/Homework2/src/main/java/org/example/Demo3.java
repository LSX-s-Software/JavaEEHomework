package org.example;

import org.example.annotation.InitMethod;

public class Demo3 {
    @InitMethod
    public int init(int arg1) {
        System.out.println(arg1);
        return 0;
    }
}
