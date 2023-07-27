package firstProject.board.repository.post.impl;

import firstProject.board.domain.member.Member;
import firstProject.board.domain.post.Post;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostGetDto {
    private Long id;

    private Long postNum;

    private String postName;

    private int commentSize;

    private Member member;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime localDateTime;

    private Long readCount;

    public PostGetDto() {
    }

    public PostGetDto(Post post) {
        this.id = post.getId();
        this.postNum = post.getPostNum();
        this.postName = post.getPostName();
        this.commentSize = post.getComments().size();
        this.member = post.getMember();
        this.localDateTime = post.getLocalDateTime();
        this.readCount = post.getReadCount();
    }
}
