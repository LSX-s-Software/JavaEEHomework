package edu.whu.homework8.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    String name;

    private String password;

    /**
     * 用户的角色列表
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<Role> roles = new java.util.ArrayList<>();


}
