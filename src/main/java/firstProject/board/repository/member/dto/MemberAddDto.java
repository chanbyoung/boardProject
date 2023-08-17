package firstProject.board.repository.member.dto;

import firstProject.board.domain.member.Address;
import firstProject.board.domain.member.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberAddDto {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name; //사용자 이름
    @NotEmpty
    private String email;
    @NotEmpty
    private String birth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Valid
    private Address address;

    public MemberAddDto() {
    }

    public MemberAddDto(String loginId, String password, String name,String email, String birth, Gender gender, Address address) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.gender = gender;
        this.address = address;
    }
}
