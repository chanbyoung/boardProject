package firstProject.board.domain.member.impl;

import firstProject.board.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long> {
    Member findByLoginId(String loginId);
}
