package edu.whu.homework7.service.impl;

import edu.whu.homework7.dao.SupplierDao;
import edu.whu.homework7.entity.Product;
import edu.whu.homework7.dao.ProductDao;
import edu.whu.homework7.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.whu.homework7.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linsixing
 * @since 2022-10-27
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements IProductService {

    @Autowired
    private SupplierDao supplierDao;

    @Override
    public Product getProductWithSupplier(Product product) {
        if (product == null) {
            return null;
        }
        product.setSuppliers(supplierDao.getSuppliersByProductId(product.getId()));
        return product;
    }

    @Override
    @Transactional
    public Product updateProductWithSupplier(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product is null");
        }
        baseMapper.deleteProductSupplierRelation(product.getId());
        for (var supplier : product.getSuppliers()) {
            baseMapper.insertProductSupplierRelation(product.getId(), supplier.getId());
        }
        return getProductWithSupplier(product);
    }
}
