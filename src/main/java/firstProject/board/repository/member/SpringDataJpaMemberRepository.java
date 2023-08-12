package firstProject.board.repository.member;

import firstProject.board.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long> {
    Member findByLoginId(String loginId);
    @Query("select m from Member m where m.name like %:name%")
    Page<Member> findMemberByMemberName(@Param("name") String memberName, Pageable pageable);



}
