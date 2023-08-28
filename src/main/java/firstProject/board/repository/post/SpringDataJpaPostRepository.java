package firstProject.board.repository.post;

import firstProject.board.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataJpaPostRepository extends JpaRepository<Post,Long> {
    void deleteById(Long id);
    @Query("select count(p) from Post p")
    Long countPostBy();

    @Override
    @EntityGraph(attributePaths = {"member", "comments"})
    List<Post> findAll();
    @Query("select p from Post p join fetch p.member m where m.id = :id order by p.postNum desc")
    Page<Post> findPostByMemberId(@Param("id") Long id, Pageable pageable);
}

