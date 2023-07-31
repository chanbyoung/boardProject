package firstProject.board.repository.post.dto;

import firstProject.board.domain.post.Comment;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentDto {
    @NotBlank
    private String content;

    public CommentDto(Comment comment) {
        this.content = comment.getContent();
    }

    public CommentDto() {
    }
}
