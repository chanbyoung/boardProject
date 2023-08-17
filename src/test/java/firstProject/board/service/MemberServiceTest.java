package firstProject.board.service;

import firstProject.board.domain.member.Address;
import firstProject.board.domain.member.Gender;
import firstProject.board.domain.member.Member;
import firstProject.board.repository.member.MemberRepository;
import firstProject.board.repository.member.dto.MemberAddDto;
import firstProject.board.repository.member.dto.MemberGetDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @InjectMocks MemberService memberService;
    @Spy MemberRepository memberRepository;

    @Test
    void getMember() {
        //given
        Long memberId= 1L;
        Member mockMember = new Member(memberId,"123", "123","123@123.com", "pcb", "20000728", Gender.MALE, new Address());
        when(memberRepository.findById(memberId)).thenReturn(mockMember);

        //when
        MemberGetDto member = memberService.getMember(memberId);

        //then
        assertThat(member.getId()).isEqualTo(mockMember.getId());
        assertThat(member.getName()).isEqualTo(mockMember.getName());
    }

    @Test
    void getMembers() {
        //given
        List<Member> mockMemberList = new ArrayList<>();
        Long memberId = 1L;
        Member mockMember = new Member(memberId,"123", "123","123@123.com", "pcb", "20000728", Gender.MALE, new Address());
        Long memberId2 = 2L;
        Member mockMember2 = new Member(memberId2,"123", "123","123@123.com", "pcb", "20000728", Gender.MALE, new Address());
        mockMemberList.add(mockMember);
        mockMemberList.add(mockMember2);
        when(memberRepository.findAll()).thenReturn(mockMemberList);

        //when
        List<MemberGetDto> members = memberService.getMembers();

        //then
        assertThat(mockMemberList.size()).isEqualTo(members.size());
    }

    @Test
    void getMemberName() {
    }

    @Test
    void equalMember() {
        //given
        Long memberId = 1L;
        Member mockMember = new Member(memberId,"123", "123","123@123.com", "pcb", "20000728", Gender.MALE, new Address());
        when(memberRepository.findById(memberId)).thenReturn(mockMember);

        //when
        boolean flag = memberService.equalMember(mockMember, memberId);

        //then
        assertThat(flag).isTrue();
    }

    @Test
    void saveMember() {
        //given
        MemberAddDto member = new MemberAddDto("123", "123", "pcb","abc123@abc.com" ,"20000728", Gender.MALE, new Address());

        //when
        Member saveMember = memberService.saveMember(member);

        //then
        assertThat(member.getName()).isEqualTo(saveMember.getName());

    }

    @Test
    void deleteMember() {
        //given
        Long memberId = 1L;
        Member mockMember = new Member(memberId,"123", "123","123@123.com", "pcb", "20000728", Gender.MALE, new Address());
        memberRepository.save(mockMember);

        //when
        memberService.deleteMember(memberId);

        //then
        assertThat(memberRepository.findById(memberId)).isNull();
    }

    @Test
    void editMember() {
    }

}