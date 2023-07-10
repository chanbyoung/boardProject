package firstProject.board.repository.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostAddDto {
    @NotBlank
    private String postName;
    @NotBlank
    private String content;

}
