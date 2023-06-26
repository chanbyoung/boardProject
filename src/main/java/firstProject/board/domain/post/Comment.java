package firstProject.board.domain.post;

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

    private String name; //댓글 작성자

    @NotBlank
    private String content; // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(String name, String content, Post post) {
        this.name = name;
        this.content = content;
        this.post = post;
    }

    public Comment() {
    }
}
