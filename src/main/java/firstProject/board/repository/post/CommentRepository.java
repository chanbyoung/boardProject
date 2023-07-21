package firstProject.board.repository.post;

import firstProject.board.domain.post.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Long countCommentByPostId(long postId);

}
