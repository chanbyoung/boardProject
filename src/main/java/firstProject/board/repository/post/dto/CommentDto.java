package firstProject.board.repository.post.dto;

import firstProject.board.domain.post.Comment;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentDto {

    private Long postId;

    private String postName;

    @NotBlank
    private String content;

    public CommentDto(Long postId, String postName, String content) {
        this.postId = postId;
        this.postName = postName;
        this.content = content;
    }

    public CommentDto(Comment comment) {
        this.postId = comment.getPost().getId();
        this.postName = comment.getPost().getPostName();
        this.content = comment.getContent();

    }

    public CommentDto() {
    }
}
