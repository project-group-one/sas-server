package com.food.sas.data.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zj on 2018/12/22
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(name = "INDEX_PATH", columnList = "path")})
public class FileInfo implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String path;

    private String name;
}
