package jpabook.jpashop.service.member;

import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원가입")
    @Test
    void signUp() {
        // Given
        Member member = new Member();
        member.setName("KOBOOLEAN");


        // When
        memberService.join(member);

        // Then
        assertThat(member).isEqualTo(memberRepository.findById(member.getId()));
    }


    @DisplayName("중복 회원 예외처리")
    @Test
    void validateDuplicateMember() {
        // Given
        Member member1 = new Member();
        member1.setName("KOBOOLEAN");

        Member member2 = new Member();
        member2.setName("KOBOOLEAN");

        memberService.join(member1);

        // Then
        assertThatThrownBy(() -> {
            memberService.join(member2);
        }).isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 회원입니다.");
    }

}
