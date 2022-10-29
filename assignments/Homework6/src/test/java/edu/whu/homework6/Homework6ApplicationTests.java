package edu.whu.homework6;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.homework6.dao.SupplierDao;
import edu.whu.homework6.entity.*;
import edu.whu.homework6.service.IProductService;
import edu.whu.homework6.service.ISupplierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class Homework6ApplicationTests {

    @Autowired
    private IProductService productService;
    @Autowired
    private ISupplierService supplierService;

    @Test
    void contextLoads() {
        assertNotNull(productService);
        assertNotNull(supplierService);
    }

    @BeforeEach
    void setUp() {
        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(new Supplier(1, "supplier1", "address1"));
        suppliers.add(new Supplier(2, "supplier2", "address2"));
        suppliers.add(new Supplier(3, "supplier3", "address3"));
        supplierService.saveBatch(suppliers);
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "product1", "description1", 1.0, List.of(suppliers.get(0), suppliers.get(1))));
        products.add(new Product(2, "product2", "description2", 2.0, List.of(suppliers.get(1), suppliers.get(2))));
        products.add(new Product(3, "product3", "description3", 3.0, List.of(suppliers.get(2), suppliers.get(0))));
        productService.saveBatch(products);
        for (var product : products) {
            productService.updateProductWithSupplier(product);
        }
    }

    @Test
    void testGetProductWithSupplier() {
        var productList = productService.list();
        var product = productService.getProductWithSupplier(productList.get(0));
        assertNotNull(product);
        assertEquals("product1", product.getName());
        assertEquals("description1", product.getDescription());
        assertEquals(1.0, product.getPrice());
        assertEquals(2, product.getSuppliers().size());
        assertEquals("supplier1", product.getSuppliers().get(0).getName());
        assertEquals("address1", product.getSuppliers().get(0).getAddress());
        assertEquals("supplier2", product.getSuppliers().get(1).getName());
        assertEquals("address2", product.getSuppliers().get(1).getAddress());
    }

    @Test
    void testGetProductByName() {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "product1");
        var product = productService.list(queryWrapper).get(0);
        assertNotNull(product);
        product = productService.getProductWithSupplier(product);
        assertEquals("product1", product.getName());
        assertEquals("description1", product.getDescription());
        assertEquals(1.0, product.getPrice());
        assertEquals(2, product.getSuppliers().size());
        assertEquals("supplier1", product.getSuppliers().get(0).getName());
        assertEquals("address1", product.getSuppliers().get(0).getAddress());
        assertEquals("supplier2", product.getSuppliers().get(1).getName());
        assertEquals("address2", product.getSuppliers().get(1).getAddress());
    }

    @Test
    void testGetProductByDescription() {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("description", "description");
        var productList = productService.list(queryWrapper);
        assertEquals(3, productList.size());
        var product = productList.get(0);
        assertNotNull(product);
        product = productService.getProductWithSupplier(product);
        assertEquals("product1", product.getName());
        assertEquals("description1", product.getDescription());
        assertEquals(1.0, product.getPrice());
        assertEquals(2, product.getSuppliers().size());
        assertEquals("supplier1", product.getSuppliers().get(0).getName());
        assertEquals("address1", product.getSuppliers().get(0).getAddress());
        assertEquals("supplier2", product.getSuppliers().get(1).getName());
        assertEquals("address2", product.getSuppliers().get(1).getAddress());
    }

    @Test
    void testGetProductByPrice() {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("price", 2.0);
        var product = productService.list(queryWrapper).get(0);
        assertNotNull(product);
        product = productService.getProductWithSupplier(product);
        assertEquals("product2", product.getName());
        assertEquals("description2", product.getDescription());
        assertEquals(2.0, product.getPrice());
        assertEquals(2, product.getSuppliers().size());
        assertEquals("supplier2", product.getSuppliers().get(0).getName());
        assertEquals("address2", product.getSuppliers().get(0).getAddress());
        assertEquals("supplier3", product.getSuppliers().get(1).getName());
        assertEquals("address3", product.getSuppliers().get(1).getAddress());
    }

    @Test
    void testUpdateProductWithSupplier() {
        var productList = productService.list();
        var product = productService.getProductWithSupplier(productList.get(0));
        product.setName("product1_new");
        product.setDescription("description1_new");
        product.setPrice(1.1);
        product.setSuppliers(List.of(product.getSuppliers().get(0)));
        productService.updateProductWithSupplier(product);
        var productNew = productService.getProductWithSupplier(product);
        assertNotNull(productNew);
        assertEquals("product1_new", productNew.getName());
        assertEquals("description1_new", productNew.getDescription());
        assertEquals(1.1, productNew.getPrice());
        assertEquals(1, productNew.getSuppliers().size());
        assertEquals("supplier1", productNew.getSuppliers().get(0).getName());
        assertEquals("address1", productNew.getSuppliers().get(0).getAddress());
    }

    @Test
    void testDeleteProductWithSupplier() {
        var productList = productService.list();
        var product = productService.getProductWithSupplier(productList.get(0));
        productService.removeById(product.getId());
        var productNew = productService.getById(product.getId());
        assertNull(productNew);
        var supplierList = supplierService.getSuppliersByProductId(product.getId());
        assertEquals(0, supplierList.size());
    }

    @Test
    void testPagination() {
        var page = new Page<Product>(1, 2);
        var pageProduct = productService.page(page);
        assertEquals(2, pageProduct.getRecords().size());
        assertEquals(3, pageProduct.getTotal());
        assertEquals(2, pageProduct.getSize());
        assertEquals(1, pageProduct.getCurrent());
        assertEquals(2, pageProduct.getPages());
        pageProduct = productService.page(page, new QueryWrapper<Product>().ge("price", 2.0));
        assertEquals(2, pageProduct.getRecords().size());
        assertEquals(2, pageProduct.getTotal());
        assertEquals(2, pageProduct.getSize());
        assertEquals(1, pageProduct.getCurrent());
        assertEquals(1, pageProduct.getPages());
    }

}
