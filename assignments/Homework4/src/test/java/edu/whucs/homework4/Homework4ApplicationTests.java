package edu.whucs.homework4;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Homework4ApplicationTests {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private GoodController controller;

    @Test
    public void contextLoads() {
        assertNotNull(controller);
    }

    @Test
    public void testGetGoods() {
        String url = "http://localhost:" + port + "/api/goods";
        Good[] goods = restTemplate.getForObject(url, Good[].class);
        assertEquals(3, goods.length);
        List<Good> expectedGoods = (new GoodService()).getGoods();
        for (int i = 0; i < expectedGoods.size(); i++) {
            assertEquals(expectedGoods.get(i).getId(), goods[i].getId());
            assertEquals(expectedGoods.get(i).getName(), goods[i].getName());
            assertEquals(expectedGoods.get(i).getDescription(), goods[i].getDescription());
            assertEquals(expectedGoods.get(i).getPrice(), goods[i].getPrice());
        }
    }

    @Test
    public void testGetGood() {
        String url = "http://localhost:" + port + "/api/goods/3";
        Good good = restTemplate.getForObject(url, Good.class);
        assertEquals(3, good.getId());
        assertEquals("orange", good.getName());
        assertEquals("橙子", good.getDescription());
        assertEquals("3", good.getPrice());
    }

    @Test
    public void testGetGoodNotExist() {
        String url = "http://localhost:" + port + "/api/goods/4";
        Good good = restTemplate.getForObject(url, Good.class);
        assertNull(good);
    }

    @Test
    public void testGetGoodByName() {
        String url = "http://localhost:" + port + "/api/goods/?name=orange";
        Good good = restTemplate.getForObject(url, Good.class);
        assertEquals(3, good.getId());
        assertEquals("orange", good.getName());
        assertEquals("橙子", good.getDescription());
        assertEquals("3", good.getPrice());
    }

    @Test
    public void testGetGoodByNameNotExist() {
        String url = "http://localhost:" + port + "/api/goods/?name=pear";
        Good good = restTemplate.getForObject(url, Good.class);
        assertNull(good);
    }

    @Test
    public void testAddGood() {
        String url = "http://localhost:" + port + "/api/goods";
        Good good = new Good(4, "banana", "香蕉", "2");
        restTemplate.postForObject(url, good, Good.class);
        Good[] goods = restTemplate.getForObject(url, Good[].class);
        assertEquals(4, goods.length);
        assertEquals(4, goods[3].getId());
        assertEquals("banana", goods[3].getName());
        assertEquals("香蕉", goods[3].getDescription());
        assertEquals("2", goods[3].getPrice());
    }

    @Test
    public void testDeleteGood() {
        String url = "http://localhost:" + port + "/api/goods/1";
        restTemplate.delete(url);
        url = "http://localhost:" + port + "/api/goods";
        Good[] goods = restTemplate.getForObject(url, Good[].class);
        for (Good good : goods) {
            assertNotEquals(1, good.getId());
        }
    }

    @Test
    public void testDeleteGoodNotExist() {
        String url = "http://localhost:" + port + "/api/goods";
        Good[] goods = restTemplate.getForObject(url, Good[].class);
        int length1 = goods.length;
        restTemplate.delete(url + "/4");
        goods = restTemplate.getForObject(url, Good[].class);
        int length2 = goods.length;
        assertEquals(length1, length2);
    }

    @Test
    public void testDeleteGoodByName() {
        String url = "http://localhost:" + port + "/api/goods?name=banana";
        restTemplate.delete(url);
        Good[] goods = restTemplate.getForObject(url, Good[].class);
        for (Good good : goods) {
            assertNotEquals("banana", good.getName());
        }
    }

    @Test
    public void testDeleteGoodByNameNotExist() {
        String url = "http://localhost:" + port + "/api/goods?name=pear";
        restTemplate.delete(url);
        Good[] goods = restTemplate.getForObject(url, Good[].class);
        assertEquals(3, goods.length);
    }

    @Test
    public void testUpdateGood() {
        String url = "http://localhost:" + port + "/api/goods/1";
        Good good = new Good(1, "banana", "香蕉", "2");
        restTemplate.put(url, good);
        Good g = restTemplate.getForObject(url, Good.class);
        assertEquals("banana", g.getName());
        assertEquals("香蕉", g.getDescription());
        assertEquals("2", g.getPrice());
    }

    @Test
    public void testUpdateGoodNotExist() {
        String url = url = "http://localhost:" + port + "/api/goods";
        Good[] goods = restTemplate.getForObject(url, Good[].class);
        int length1 = goods.length;
        Good good = new Good(4, "banana", "香蕉", "2");
        restTemplate.put(url + "/4", good);
        goods = restTemplate.getForObject(url, Good[].class);
        int length2 = goods.length;
        assertEquals(length1, length2);
    }

}
