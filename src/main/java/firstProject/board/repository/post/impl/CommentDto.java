package firstProject.board.repository.post.impl;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentDto {
    @NotBlank
    private String content;
}
