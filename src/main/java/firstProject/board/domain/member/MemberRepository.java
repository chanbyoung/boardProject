package firstProject.board.domain.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository{
    void save(Member member);

    Member findById(Long id);

    List<Member> findAll();

    Optional<Member> findByLoginId(String loginId);

    void delete(Long id);
}
