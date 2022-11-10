package edu.whu.homework8.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Role {

    @Id
    String name;

    String remark;

    /**
     * 权限的列表。
     * 这里每个权限使用一个字符串表示。一般可以把系统的功能进行分解，每个子功能作为一个权限。
     * 如果需要动态管理权限，可以在数据库中建一个功能表（权限表），和角色进行多对多关联
     */
    @Convert(converter = StringListConverter.class)
    List<String> authorities = new ArrayList<>();

}
