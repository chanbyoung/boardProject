package firstProject.board.domain.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Comment {
    private Long id;
    private String name; //댓글 작성자
    @NotBlank
    private String content; // 댓글 내용

    public Comment( String name, String content) {
        this.name = name;
        this.content = content;
    }

    public Comment() {
    }
}
