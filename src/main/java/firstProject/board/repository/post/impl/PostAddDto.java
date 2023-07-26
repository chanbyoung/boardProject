package firstProject.board.repository.post.impl;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostAddDto {

    private Long id;
    @NotBlank
    private String postName;
    @NotBlank
    private String content;

    public PostAddDto() {
    }

    public PostAddDto updatePostID(Long id) {
        this.id = id;
        return this;
    }
}
