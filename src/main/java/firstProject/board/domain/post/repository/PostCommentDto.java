package firstProject.board.domain.post.repository;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostCommentDto {
    @NotBlank
    private String content;
}
