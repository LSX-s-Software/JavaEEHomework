package edu.whu.homework5;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Supplier {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    private String address;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "suppliers")
    private List<Good> goods;

    public Supplier(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Supplier() {
    }
}
