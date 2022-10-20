package edu.whu.homework5;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    private SupplierJPARepository supplierJPARepository;

    public SupplierService(SupplierJPARepository supplierJPARepository) {
        this.supplierJPARepository = supplierJPARepository;
    }

    public List<Supplier> getSuppliers() {
        return supplierJPARepository.findAll();
    }

    public Supplier getSupplier(long id) {
        return supplierJPARepository.findById(id).orElse(null);
    }

    public List<Supplier> getSupplier(String name) {
        return supplierJPARepository.findByName(name);
    }

    public Supplier addSupplier(Supplier supplier) {
        return supplierJPARepository.save(supplier);
    }

    @Modifying
    public void deleteSupplier(long id) {
        supplierJPARepository.deleteById(id);
    }

    public void deleteSupplier(String name) {
        supplierJPARepository.deleteByName(name);
    }

    @Modifying
    public Supplier updateSupplier(long id, Supplier supplier) {
        Supplier oldSupplier = supplierJPARepository.findById(id).orElse(null);
        if (oldSupplier == null) {
            return null;
        }
        oldSupplier.setName(supplier.getName());
        oldSupplier.setAddress(supplier.getAddress());
        return supplierJPARepository.save(oldSupplier);
    }
}
