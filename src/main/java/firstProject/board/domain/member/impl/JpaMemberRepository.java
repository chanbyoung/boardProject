package firstProject.board.domain.member.impl;

import firstProject.board.domain.member.Member;
import firstProject.board.domain.member.MemberRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository {
    private final EntityManager em;
    private final SpringDataJpaMemberRepository repository;

    @Override
    public void save(Member member) {
        em.persist(member);
    }

    @Override
    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return Optional.ofNullable(repository.findByLoginId(loginId));
    }

    @Override
    public void delete(Long id) {
        repository.delete(repository.findById(id).get());
    }
}
