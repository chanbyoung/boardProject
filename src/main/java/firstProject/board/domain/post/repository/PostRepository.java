package firstProject.board.domain.post.repository;

import firstProject.board.domain.post.Post;

import java.util.List;

public interface PostRepository {
    Post save(Post post);

    Post findById(Long id);

    List<Post> findAll(PostSearchCond cond);

    void update(Long id, PostUpdateDto updateParam);

    void updateReadCount(Long id);

    void delete(Long id);
}
