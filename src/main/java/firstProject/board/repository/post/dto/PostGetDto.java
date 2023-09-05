package firstProject.board.repository.post.dto;

import firstProject.board.domain.post.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostGetDto {
    private Long id;
    private Long postNum;
    private String memberName;
    private String loginId;
    private String postName;
    private String content;

    public PostGetDto(Post p) {
        this.id = p.getId();
        this.postNum = p.getPostNum();
        this.memberName = p.getMember().getName();
        this.loginId = p.getMember().getLoginId();
        this.postName = p.getPostName();
        this.content = p.getContent();
    }

    public PostGetDto(Long id, Long postNum, String memberName, String postName, String content) {
        this.id = id;
        this.postNum = postNum;
        this.memberName = memberName;
        this.postName = postName;
        this.content = content;
    }

    public PostGetDto() {
    }
}
