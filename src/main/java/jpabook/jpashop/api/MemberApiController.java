package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.service.member.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;


    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMember(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id
                                             , @RequestBody UpdateMemberRequest request){
        memberService.update(id, request.getName());

        Member member = memberService.findMember(id);

        return new UpdateMemberResponse(member.getId(), member.getName());
    }

    @GetMapping("api/v2/members/")
    public Result selectMemberV2(){
        List<Member> findMembers = memberService.findMembers();

        List<MemberDto> collection = findMembers.stream().map(member -> new MemberDto(member.getName()))
                .toList();

        return new Result(collection);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }

    @Data
    static class UpdateMemberRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }

}
