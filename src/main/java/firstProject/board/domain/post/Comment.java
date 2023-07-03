package firstProject.board.domain.post;

import firstProject.board.domain.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "post")
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "comment_id")
    private Long id;

    @NotBlank
    private String content; // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Comment(Member member, String content, Post post) {
        this.member = member;
        this.content = content;
        this.post = post;
    }

    public Comment() {
    }
}
