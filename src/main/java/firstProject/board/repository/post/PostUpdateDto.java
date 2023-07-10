package firstProject.board.repository.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateDto {
    @NotBlank
    private String postName;
    @NotBlank
    private String content;

    public PostUpdateDto(String postName, String content) {
        this.postName = postName;
        this.content = content;
    }
}
