package firstProject.board.domain.post;

import firstProject.board.domain.BaseEntity;
import firstProject.board.domain.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString(exclude = {"post", "member"})
public class Comment extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "comment_id")
    private Long id;

    private Long commentNum;

    @NotBlank
    private String content; // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Comment(Member member, String content, Post post, Long commentNum) {
        this.member = member;
        this.content = content;
        this.post = post;
        this.commentNum = commentNum+1;
    }

    public Comment() {
    }

    public void updateCommentContent(String content) {
        this.content = content;
    }

    public void updateCommentNum(Long cnt) {
        this.commentNum = cnt;
    }
}
