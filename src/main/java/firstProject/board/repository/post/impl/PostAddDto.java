package firstProject.board.repository.post.impl;

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
