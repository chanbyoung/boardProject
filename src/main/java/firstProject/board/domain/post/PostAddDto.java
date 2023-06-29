package firstProject.board.domain.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostAddDto {
    @NotBlank
    private String postName;
    @NotBlank
    private String content;
}
