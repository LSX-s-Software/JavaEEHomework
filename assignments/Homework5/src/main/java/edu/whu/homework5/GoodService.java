package edu.whu.homework5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GoodService {
    @Autowired
    private GoodJPARepository goodJPARepository;

    public List<Good> getGoods() {
        return goodJPARepository.findAll();
    }

    public Good getGood(long id) {
        return goodJPARepository.findById(id).orElse(null);
    }

    public List<Good> getGood(String name) {
        return goodJPARepository.findByName(name);
    }

    public Good addGood(Good good) {
        return goodJPARepository.save(good);
    }

    @Modifying
    public void deleteGood(long id) {
        goodJPARepository.deleteById(id);
    }

    public void deleteGood(String name) {
        goodJPARepository.deleteByName(name);
    }

    @Modifying
    public void updateGood(long id, Good good) {
        Good oldGood = goodJPARepository.findById(id).orElse(null);
        if (oldGood != null) {
            oldGood.setName(good.getName());
            oldGood.setDescription(good.getDescription());
            oldGood.setPrice(good.getPrice());
            oldGood.setSuppliers(good.getSuppliers());
            goodJPARepository.saveAndFlush(oldGood);
        }
    }
}
