package firstProject.board.domain.post;

import java.util.List;
import java.util.Map;

public interface PostRepository {
    Post save(Post post);

    Map saveComment(Post post, Comment comment);

    Post findById(Long id);

    List<Post> findAll();

    void update(Long id, Post updateParam);

    void updateReadCount(Long id);
}
