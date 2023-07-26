package firstProject.board.repository.post;

import firstProject.board.domain.post.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaPostRepository extends JpaRepository<Post,Long> {
    void deleteById(Long id);

    Long countPostBy();

    @Override
    @EntityGraph(attributePaths = {"member", "comments"})
    List<Post> findAll();
}

