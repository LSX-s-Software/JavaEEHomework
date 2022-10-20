package edu.whucs.homework4;

import lombok.Data;

@Data
public class Good {
    private Integer id;
    private String name;
    private String description;
    private String price;

    public Good(Integer id, String name, String description, String price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
