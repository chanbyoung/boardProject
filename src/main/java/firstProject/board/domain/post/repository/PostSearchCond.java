package firstProject.board.domain.post.repository;

import lombok.Data;

@Data
public class PostSearchCond {
    private String type;
    private String searchContent;

    public PostSearchCond() {
    }

    public PostSearchCond(String type, String searchContent) {
        this.type = type;
        this.searchContent = searchContent;
    }
}
