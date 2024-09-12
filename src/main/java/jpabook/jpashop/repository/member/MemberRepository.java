package jpabook.jpashop.repository.member;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // Spring boot 하위에 있기에 자동으로 빈으로 연결시켜주며, JPA와 연결시켜준다.
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public int findByNameCount(String name){
        return findByName(name).size();
    }
}
