package firstProject.board.web.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageInfo {
    private int nowPage;
    private int startPage;
    private int endPage;

    public PageInfo(int nowPage,int totalPages) {
        this.nowPage = nowPage+1;
        this.startPage = Math.max(nowPage - 4, 1);
        this.endPage = Math.min(nowPage + 9, totalPages);
    }
}
