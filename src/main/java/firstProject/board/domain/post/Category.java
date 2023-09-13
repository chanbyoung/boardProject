package firstProject.board.domain.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    GENERAL("자유글"), NOTICE("공지글");
    private final String value;
}
