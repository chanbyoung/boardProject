package firstProject.board.repository.post;

import firstProject.board.domain.post.Post;
import firstProject.board.repository.post.dto.PostsGetDto;
import firstProject.board.repository.post.dto.PostSearchCond;
import firstProject.board.repository.post.dto.PostUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepository {
    Long save(Post post);

    Post findById(Long id);

    Page<PostsGetDto> findAll(PostSearchCond cond, Pageable pageable);

    void update(Long id, PostUpdateDto updateParam);

    void delete(Long id);
}
