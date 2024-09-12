package jpabook.jpashop.domain.member;

import jakarta.persistence.*;
import jpabook.jpashop.domain.address.Address;
import jpabook.jpashop.domain.order.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // member와 연관관계를 가지므로 Member에서는 읽기전용으로 만든다.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
