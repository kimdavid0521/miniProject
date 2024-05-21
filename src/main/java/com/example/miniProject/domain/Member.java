package com.example.miniProject.domain;

import com.example.miniProject.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity //엔티티 어노테이션을 통해 해당 클래스가 jpa의 엔티티임을 명시
@Getter // getter 만들어주는 어노테이션
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor //위 3개의 어노테이션은 빌더패턴을 사용하기 위해 쓰는것(생성자보다 편리하게 사용 가능)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

}
