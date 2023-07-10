package firstProject.board.repository.post;

import firstProject.board.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaPostRepository extends JpaRepository<Post,Long> {
    void deleteById(Long id);
}
