package firstProject.board.repository.post.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository {
    private final EntityManager em;

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
