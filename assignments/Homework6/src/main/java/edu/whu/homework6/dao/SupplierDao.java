package edu.whu.homework6.dao;

import edu.whu.homework6.entity.Supplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linsixing
 * @since 2022-10-27
 */
@Mapper
public interface SupplierDao extends BaseMapper<Supplier> {
    @Select("SELECT * FROM supplier WHERE id IN (SELECT supplier_id FROM product_supplier WHERE product_id = #{id})")
    List<Supplier> getSuppliersByProductId(long id);
}
