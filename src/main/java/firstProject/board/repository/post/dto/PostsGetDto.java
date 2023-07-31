package firstProject.board.repository.post.dto;

import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Post;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostsGetDto {
    private Long id;

    private Long postNum;

    private String postName;

    private int commentSize;

    private Member member;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime localDateTime;

    private Long readCount;

    public PostsGetDto() {
    }

    public PostsGetDto(Post post) {
        this.id = post.getId();
        this.postNum = post.getPostNum();
        this.postName = post.getPostName();
        this.commentSize = post.getComments().size();
        this.member = post.getMember();
        this.localDateTime = post.getLocalDateTime();
        this.readCount = post.getReadCount();
    }
}
