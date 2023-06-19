package firstProject.board.domain.post.repository;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostUpdateDto {
    @NotBlank
    private String postName;
    @NotBlank
    private String content;
}
