package firstProject.board.domain.post.repository.impl;

import firstProject.board.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaPostRepository extends JpaRepository<Post,Long> {
    void deleteById(Long id);
}
