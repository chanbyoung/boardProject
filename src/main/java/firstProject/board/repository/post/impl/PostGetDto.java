package firstProject.board.repository.post.impl;

import firstProject.board.domain.post.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostGetDto {
    private Long id;
    private Long postNum;
    private String memberName;

    private String postName;
    private String content;

    public PostGetDto(Post p) {
        this.id = p.getId();
        this.postNum = p.getPostNum();
        this.memberName = p.getMember().getName();
        this.postName = p.getPostName();
        this.content = p.getContent();
    }

    public PostGetDto() {
    }
}
