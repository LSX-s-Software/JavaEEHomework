package edu.whu.homework7;

import edu.whu.homework7.aspect.LogAspect;
import edu.whu.homework7.aspect.StopwatchAspect;
import edu.whu.homework7.controller.ProductController;
import edu.whu.homework7.controller.SupplierController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Homework7ApplicationTests {

    @Autowired
    private ProductController productController;
    @Autowired
    private SupplierController supplierController;
    @Autowired
    private StopwatchAspect stopwatchAspect;
    @Autowired
    private LogAspect logAspect;

    @Test
    void contextLoads() {
        assertNotNull(productController);
        assertNotNull(supplierController);
        assertNotNull(stopwatchAspect);
        assertNotNull(logAspect);
    }

}
