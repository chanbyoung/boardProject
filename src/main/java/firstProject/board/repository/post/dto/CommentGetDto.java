package firstProject.board.repository.post.dto;

import firstProject.board.domain.post.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentGetDto {
    private Long id;
    private Long commentNum;
    private String content;
    private String memberName;

    public CommentGetDto(Comment comment) {
        this.id = comment.getId();
        this.commentNum = comment.getCommentNum();
        this.content = comment.getContent();
        this.memberName = comment.getMember().getName();
    }
}
