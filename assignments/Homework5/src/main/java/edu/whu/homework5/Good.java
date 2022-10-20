package edu.whu.homework5;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Good {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private Double price;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Supplier> suppliers;

    public Good(String name, String description, Double price, List<Supplier> suppliers) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.suppliers = suppliers;
    }

    public Good() { }
}
