package firstProject.board.repository.post.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostSearchCond {
    private String type;
    private String searchContent;
    private String category;

}
