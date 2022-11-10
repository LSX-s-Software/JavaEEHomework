package edu.whu.homework8.service.impl;

import edu.whu.homework8.entity.Supplier;
import edu.whu.homework8.dao.SupplierDao;
import edu.whu.homework8.service.ISupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linsixing
 * @since 2022-10-27
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierDao, Supplier> implements ISupplierService {

    @Override
    public List<Supplier> getSuppliersByProductId(long id) {
        return baseMapper.getSuppliersByProductId(id);
    }
}
