package edu.whu.homework5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class GoodController {
    @Autowired
    private GoodService goodService;

    @GetMapping("")
    public List<Good> getGoods() {
        return goodService.getGoods();
    }

    @GetMapping("/")
    public ResponseEntity<List<Good>> getGoods(String name) {
        List<Good> good = goodService.getGood(name);
        return good == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(good);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Good> getGood(@PathVariable long id) {
        Good good = goodService.getGood(id);
        return good == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(good);
    }

    @PostMapping("")
    public Good addGood(@RequestBody Good good) {
        return goodService.addGood(good);
    }

    @DeleteMapping("/{id}")
    public void deleteGood(@PathVariable long id) {
        goodService.deleteGood(id);
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteGood(String name) {
        goodService.deleteGood(name);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Good updateGood(@PathVariable long id, @RequestBody Good good) {
        goodService.updateGood(id, good);
        return good;
    }
}
