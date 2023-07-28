package firstProject.board.repository.post.impl;

import firstProject.board.domain.post.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository {
    private final EntityManager em;

    public List<Comment> findCommentByPostId(Long postId) {
        List<Comment> comments = em.createQuery("select c " +
                        " from Comment c" +
                        " join fetch c.member m" +
                        " where c.post.id = :postId", Comment.class)
                .setParameter("postId", postId)
                .getResultList();

        return comments;
    }
    public void updateCommentNum(Long postId,Long commentNum) {
        em.createQuery("update Comment c" +
                " set c.commentNum = c.commentNum-1" +
                " where c.post.id = :postId" +
                " and c.commentNum > :commentNum")
                .setParameter("postId", postId)
                .setParameter("commentNum", commentNum)
                .executeUpdate();
    }
}
