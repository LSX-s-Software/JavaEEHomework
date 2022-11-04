package edu.whu.homework7.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.whu.homework7.entity.Product;
import edu.whu.homework7.exception.APIException;
import edu.whu.homework7.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linsixing
 * @since 2022-10-27
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("")
    public List<Product> getAllProducts() {
        List<Product> results = productService.list();
        for (int i = 0; i < results.size(); i++) {
            results.set(i, productService.getProductWithSupplier(results.get(i)));
        }
        return results;
    }

    @GetMapping("{id}")
    public Product getProductById(@PathVariable Long id) throws APIException {
        Product product = productService.getById(id);
        if (product == null) {
            throw new APIException("product not found");
        }
        return productService.getProductWithSupplier(product);
    }

    @GetMapping("/")
    public List<Product> getProductByName(String name) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        List<Product> productList = productService.list(queryWrapper);
        productList.replaceAll(product -> productService.getProductWithSupplier(product));
        return productList;
    }

    @PostMapping("")
    public Product addProduct(@RequestBody Product product) {
        productService.save(product);
        return product;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) throws APIException {
        if (id <= 0) {
            throw new APIException("Parameter 'id' must be positive");
        }
        if (!productService.removeById(id)) {
            throw new APIException("删除失败");
        }
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteProduct(String name) throws APIException {
        if (name.length() == 0) {
            throw APIException.badRequest("Parameter 'name' cannot be empty");
        }
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        productService.remove(queryWrapper);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody Product product) throws APIException {
        if (id <= 0) {
            throw new APIException("Parameter 'id' must be positive");
        }
        if (product == null) {
            throw new APIException("product is null");
        }
        if (product.getId() == null) {
            product.setId(id);
        } else if (id != product.getId()) {
            product.setId(id);
        }
        try {
            productService.updateProductWithSupplier(product);
        } catch (Exception e) {
            throw new APIException(e.getLocalizedMessage());
        }
        return product;
    }
}

