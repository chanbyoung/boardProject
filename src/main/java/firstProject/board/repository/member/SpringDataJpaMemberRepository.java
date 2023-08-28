package firstProject.board.repository.member;

import firstProject.board.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long> {
    Member findByLoginId(String loginId);
    @Query("select m from Member m where m.name like %:name%")
    Page<Member> findMemberByMemberName(@Param("name") String memberName, Pageable pageable);

    @Query("select m.loginId from Member m where m.name=:name and m.email=:email")
    String findLoginIdByMemberInfo(@Param("name") String name,@Param("email") String email);

    @Query("select m.password from Member m where m.name=:name and m.email=:email and m.loginId =:loginId ")
    String findPasswordByMemberInfo(@Param("name") String name,@Param("loginId") String loginId, @Param("email") String email);
    @Query("select m from Member m where m.email = :email")
    Optional<Member> findEmailExist(@Param("email") String email);
}
