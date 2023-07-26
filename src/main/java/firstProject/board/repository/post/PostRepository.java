package firstProject.board.repository.post;

import firstProject.board.domain.post.Post;
import firstProject.board.repository.post.impl.PostSearchCond;
import firstProject.board.repository.post.impl.PostUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepository {
    Long save(Post post);

    Post findById(Long id);

    Page<Post> findAll(PostSearchCond cond, Pageable pageable);

    void update(Long id, PostUpdateDto updateParam);

    void delete(Long id);
}
