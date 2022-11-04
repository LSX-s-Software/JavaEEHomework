package edu.whu.homework7.service;

import edu.whu.homework7.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linsixing
 * @since 2022-10-27
 */
public interface IProductService extends IService<Product> {
    Product getProductWithSupplier(Product product);
    @Transactional
    Product updateProductWithSupplier(Product product);
}
