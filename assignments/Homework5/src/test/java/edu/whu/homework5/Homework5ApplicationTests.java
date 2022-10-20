package edu.whu.homework5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Homework5Application.class)
@Transactional
class Homework5ApplicationTests {

    @Autowired
    private GoodService goodService;
    @Autowired
    private SupplierService supplierService;

    @Test
    void contextLoads() {
        assertNotNull(goodService);
        assertNotNull(supplierService);
    }

    @BeforeEach
    void setUp() {
        Supplier[] suppliers = {
                new Supplier("supplier0", "address0"),
                new Supplier("supplier1", "address1"),
                new Supplier("supplier2", "address2"),
        };
        for (Supplier supplier : suppliers) {
            supplierService.addSupplier(supplier);
        }
        Good[] goods = {
                new Good("good0", "description0", 1.0, List.of(suppliers[0], suppliers[1])),
                new Good("good1", "description1", 2.0, List.of(suppliers[1], suppliers[2])),
                new Good("good2", "description2", 3.0, List.of(suppliers[2], suppliers[0])),
        };
        for (Good good : goods) {
            goodService.addGood(good);
        }
    }

    @Test
    public void testGetGoods() {
        List<Good> goods = goodService.getGoods();
        assertEquals(3, goods.size());
        for (int i = 0; i < 3; i++) {
            assertEquals("good" + i, goods.get(i).getName());
            assertEquals("description" + i, goods.get(i).getDescription());
            assertEquals(i + 1.0, goods.get(i).getPrice());
            assertEquals(2, goods.get(i).getSuppliers().size());
            for (int j = 0; j < 2; j++) {
                assertEquals("supplier" + (i + j) % 3, goods.get(i).getSuppliers().get(j).getName());
                assertEquals("address" + (i + j) % 3, goods.get(i).getSuppliers().get(j).getAddress());
            }
        }
    }

    @Test
    public void testGetGood() {
        List<Good> goods = goodService.getGoods();
        Good good = goodService.getGood(goods.get(0).getId());
        assertEquals("good0", good.getName());
        assertEquals("description0", good.getDescription());
        assertEquals(1.0, good.getPrice());
        assertEquals(2, good.getSuppliers().size());
        for (int i = 0; i < 2; i++) {
            assertEquals("supplier" + i, good.getSuppliers().get(i).getName());
            assertEquals("address" + i, good.getSuppliers().get(i).getAddress());
        }
    }

    @Test
    public void testGetGoodNotExist() {
        Good good = goodService.getGood(4);
        assertNull(good);
    }

    @Test
    public void testGetGoodByName() {
        List<Good> goods = goodService.getGood("good2");
        assertEquals(1, goods.size());
    }

    @Test
    public void testGetGoodByNameNotExist() {
        List<Good> goods = goodService.getGood("good4");
        assertEquals(0, goods.size());
    }

    @Test
    public void testAddGood() {
        List<Supplier> suppliers = supplierService.getSuppliers();
        Good good = new Good("good4", "description4", 4.0, List.of(suppliers.get(0), suppliers.get(1)));
        goodService.addGood(good);
        assertEquals(4, goodService.getGoods().size());
        good = goodService.getGoods().get(3);
        assertEquals("good4", good.getName());
        assertEquals("description4", good.getDescription());
        assertEquals(4.0, good.getPrice());
        assertEquals(2, good.getSuppliers().size());
        for (int i = 0; i < 2; i++) {
            assertEquals("supplier" + i, good.getSuppliers().get(i).getName());
            assertEquals("address" + i, good.getSuppliers().get(i).getAddress());
        }
    }

    @Test
    public void testDeleteGood() {
        List<Good> goods = goodService.getGoods();
        int originalSize = goods.size();
        long targetId = goods.get(0).getId();
        goodService.deleteGood(targetId);
        assertEquals(originalSize - 1, goodService.getGoods().size());
        assertNull(goodService.getGood(targetId));
    }

    @Test
    public void testDeleteGoodNotExist() {
        assertThrows(org.springframework.dao.EmptyResultDataAccessException.class, () -> goodService.deleteGood(4));
    }

    @Test
    public void testDeleteGoodByName() {
        goodService.deleteGood("good2");
        assertEquals(2, goodService.getGoods().size());
        assertNull(goodService.getGood(2));
    }

    @Test
    public void testDeleteGoodByNameNotExist() {
        goodService.deleteGood("good4");
        assertEquals(3, goodService.getGoods().size());
    }

    @Test
    public void testUpdateGood() {
        List<Supplier> suppliers = supplierService.getSuppliers();
        List<Supplier> newSuppliers = new ArrayList<>();
        newSuppliers.add(suppliers.get(0));
        newSuppliers.add(suppliers.get(1));
        Good good = new Good("good4", "description4", 4.0, newSuppliers);
        long targetId = goodService.getGoods().get(0).getId();
        goodService.updateGood(targetId, good);
        assertEquals(3, goodService.getGoods().size());
        good = goodService.getGood(targetId);
        assertEquals("good4", good.getName());
        assertEquals("description4", good.getDescription());
        assertEquals(4.0, good.getPrice());
        assertEquals(2, good.getSuppliers().size());
        for (int i = 0; i < 2; i++) {
            assertEquals("supplier" + i, good.getSuppliers().get(i).getName());
            assertEquals("address" + i, good.getSuppliers().get(i).getAddress());
        }
    }

    @Test
    public void testUpdateGoodNotExist() {
        List<Supplier> suppliers = supplierService.getSuppliers();
        Good good = new Good("good4", "description4", 4.0, List.of(suppliers.get(0), suppliers.get(1)));
        goodService.updateGood(4, good);
        List<Good> goods = goodService.getGoods();
        assertEquals(3, goods.size());
        for (int i = 0; i < 3; i++) {
            assertEquals("good" + i, goods.get(i).getName());
            assertEquals("description" + i, goods.get(i).getDescription());
            assertEquals(i + 1.0, goods.get(i).getPrice());
            assertEquals(2, goods.get(i).getSuppliers().size());
            for (int j = 0; j < 2; j++) {
                assertEquals("supplier" + (i + j) % 3, goods.get(i).getSuppliers().get(j).getName());
                assertEquals("address" + (i + j) % 3, goods.get(i).getSuppliers().get(j).getAddress());
            }
        }
    }

}
