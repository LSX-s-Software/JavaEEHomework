package edu.whucs.homework4;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GoodService {
    private List<Good> goods = Collections.synchronizedList(new ArrayList<>());

    public GoodService() {
        goods.add(new Good(1, "apple", "苹果", "1"));
        goods.add(new Good(2, "banana", "香蕉", "2"));
        goods.add(new Good(3, "orange", "橙子", "3"));
    }

    public List<Good> getGoods() {
        return goods;
    }

    public Good getGood(int id) {
        for (Good good : goods) {
            if (good.getId() == id) {
                return good;
            }
        }
        return null;
    }

    public Good getGood(String name) {
        for (Good good : goods) {
            if (good.getName().equals(name)) {
                return good;
            }
        }
        return null;
    }

    public void addGood(Good good) {
        goods.add(good);
    }

    public void deleteGood(int id) {
        for (Good good : goods) {
            if (good.getId() == id) {
                goods.remove(good);
                return;
            }
        }
    }

    public void deleteGood(String name) {
        goods.removeIf(good -> good.getName().equals(name));
    }

    public void updateGood(int id, Good good) {
        for (Good g : goods) {
            if (g.getId() == id) {
                g.setName(good.getName());
                g.setDescription(good.getDescription());
                g.setPrice(good.getPrice());
                return;
            }
        }
    }
}
