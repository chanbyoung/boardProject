package firstProject.board.repository.post;

import firstProject.board.domain.post.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Long countCommentByPostId(long postId);

    @Query("select c from Comment c join fetch c.member m where m.id = :id order by c.commentNum desc")
    Page<Comment> findCommentByMemberId(@Param("id") Long id, Pageable pageable);

    @Query("select c from Comment c join fetch c.post p join fetch c.member m where p.id = :id order by c.commentNum desc")
    Page<Comment> findCommentByPostId(@Param("id") Long id , Pageable pageable);

}
