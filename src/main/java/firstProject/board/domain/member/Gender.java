package firstProject.board.domain.member;

import lombok.Getter;

public enum Gender {
    MALE("남자"),
    FEMALE("여자");

    @Getter
    private final String description;

    Gender(String description) {
        this.description = description;
    }
}
