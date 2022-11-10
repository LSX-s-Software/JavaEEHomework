package edu.whu.homework8.dao;

import edu.whu.homework8.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linsixing
 * @since 2022-10-27
 */
@Mapper
public interface ProductDao extends BaseMapper<Product> {
    @Insert("INSERT INTO product_supplier (product_id, supplier_id) VALUES (#{productId}, #{supplierId})")
    void insertProductSupplierRelation(long productId, long supplierId);

    @Delete("DELETE FROM product_supplier WHERE product_id = #{productId}")
    void deleteProductSupplierRelation(long productId);
}
