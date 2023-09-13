package firstProject.board.repository.post.dto;

import firstProject.board.domain.post.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private Category category;

    public PostAddDto() {
    }
    //테스트용
    public PostAddDto(Long id, String postName, String content, Category category) {
        this.id = id;
        this.postName = postName;
        this.content = content;
        this.category = category;
    }

    public PostAddDto updatePostID(Long id) {
        this.id = id;
        return this;
    }

}
