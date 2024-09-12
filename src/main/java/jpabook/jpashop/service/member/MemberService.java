package jpabook.jpashop.service.member;

import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     * @param member
     * @return
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);

        return member.getId();
    }

    /**
     * 이미 존재하는 회원인지 확인한다.
     * @param member
     */
    private void validateDuplicateMember(Member member) {
        int count = memberRepository.findByNameCount(member.getName());

        if(count > 0){
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 멤버 목록을 조회한다.
     * @return
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 파라미터 Id를 가진 회원을 조회한다.
     * @param id
     * @return
     */
    public Member findMember(Long id) {
        return memberRepository.findById(id);
    }
}
