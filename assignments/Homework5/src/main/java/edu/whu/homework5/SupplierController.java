package edu.whu.homework5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping("")
    public List<Supplier> getSuppliers() {
        return supplierService.getSuppliers();
    }

    @GetMapping("/")
    public ResponseEntity<List<Supplier>> getSuppliers(String name) {
        List<Supplier> supplier = supplierService.getSupplier(name);
        return supplier == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(supplier);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable long id) {
        Supplier supplier = supplierService.getSupplier(id);
        return supplier == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(supplier);
    }

    @PostMapping("")
    public Supplier addSupplier(@RequestBody Supplier supplier) {
        return supplierService.addSupplier(supplier);
    }

    @DeleteMapping("/{id}")
    public void deleteSupplier(@PathVariable long id) {
        supplierService.deleteSupplier(id);
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteSupplier(String name) {
        supplierService.deleteSupplier(name);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Supplier updateSupplier(@PathVariable long id, @RequestBody Supplier supplier) {
        supplierService.updateSupplier(id, supplier);
        return supplier;
    }
}
