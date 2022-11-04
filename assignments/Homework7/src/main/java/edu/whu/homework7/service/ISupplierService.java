package edu.whu.homework7.service;

import edu.whu.homework7.entity.Supplier;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linsixing
 * @since 2022-10-27
 */
public interface ISupplierService extends IService<Supplier> {
    List<Supplier> getSuppliersByProductId(long id);
}
