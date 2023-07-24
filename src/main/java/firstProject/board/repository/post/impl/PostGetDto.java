package firstProject.board.repository.post.impl;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PostGetDto {

    private Long postNum;

    private String postName;

    private int commentSize;

    private String memberName;

    private LocalDateTime localDateTime;

    private Long readCount;

}
