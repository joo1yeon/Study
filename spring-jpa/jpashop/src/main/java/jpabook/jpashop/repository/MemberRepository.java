package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // Entity Manager
//    @PersistenceContext // 표준 어노테이션
//    private EntityManager em;

    private final EntityManager em;

    // 멤버 한 명 저장
    public void save(Member member) {
        em.persist(member);
    }

    // 멤버 한 명 찾기
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // 멤버 모두 찾기
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 이름으로 멤버 찾기
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
