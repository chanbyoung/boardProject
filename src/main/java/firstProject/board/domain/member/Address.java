package firstProject.board.domain.member;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter @Setter
@ToString
public class Address {
    @NotEmpty
    private String postCode;
    @NotEmpty
    private String address;

    private String detailAddress;
    private String extraAddress;
    private String fullAddress;

    public Address() {
    }

    public Address(String postCode, String address, String detailAddress, String extraAddress) {
        this.postCode = postCode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.extraAddress = extraAddress;
        this.fullAddress = getFullAddress();
    }

    public String getFullAddress() {
        return "(" + postCode +") " + address +" "+ detailAddress+" "+extraAddress;
    }

}
