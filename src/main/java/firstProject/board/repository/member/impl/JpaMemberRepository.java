package firstProject.board.repository.member.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import firstProject.board.domain.member.Member;
import firstProject.board.repository.member.MemberRepository;
import firstProject.board.repository.member.SpringDataJpaMemberRepository;
import firstProject.board.repository.member.dto.MemberUpdateDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static firstProject.board.domain.member.QMember.member;

@Repository
@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository {
    private final EntityManager em;
    private final SpringDataJpaMemberRepository repository;
    private final JPAQueryFactory query;

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
        return em.createQuery("" +
                        "select m" +
                        " from Member m" +
                        " left join m.posts p" +
                        " left join m.comments c", Member.class)
                .getResultList();
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return Optional.ofNullable(repository.findByLoginId(loginId));
    }

    @Override
    public List<Member> findByName(String username) {
        List<Member> members = query.select(member)
                .from(member)
                .where(member.name.contains(username))
                .fetch();
        return members;
    }

    @Override
    public void update(Long id, MemberUpdateDto memberUpdateDto) {
        Member member = em.find(Member.class, id);
        member.update(memberUpdateDto);
    }

    @Override
    public void delete(Long id) {
        repository.delete(repository.findById(id).get());
    }
}
