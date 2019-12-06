package com.food.sas.data.entity;

import com.food.sas.enums.StatusEnum;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Created by ygdxd_admin at 2019-01-06 8:05 PM
 */
@Table(name = "t_organization")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@NamedEntityGraphs({
        @NamedEntityGraph(name = Organization.ALL,
                attributeNodes = {
                        @NamedAttributeNode(value = "users")
                }
        )
})
public class Organization implements Serializable {

    public static final String ALL = "all";

    private static final long serialVersionUID = -2067391799785799688L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String credential;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private String errorMsg;

    @CreatedDate
    private LocalDateTime createDate;

    @CreatedBy
    private Long creator;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private Set<User> users = new LinkedHashSet<>();

}
