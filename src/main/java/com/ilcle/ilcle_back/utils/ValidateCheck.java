package com.ilcle.ilcle_back.utils;

import com.ilcle.ilcle_back.dto.request.MemberRequestDto;
import com.ilcle.ilcle_back.entity.Member;
import com.ilcle.ilcle_back.exception.ErrorCode;
import com.ilcle.ilcle_back.exception.GlobalException;
import com.ilcle.ilcle_back.repository.MemberRepository;
import com.ilcle.ilcle_back.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidateCheck {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public Member getMember(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)
                );
    }

    public Member getMember(String username){
        return memberRepository.findByUsername(username)
                .orElseThrow(
                        () -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)
                );
    }

    public Member isPresentMember(String username){
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.orElse(null);
    }

    //아이디 중복 체크
    public void usernameDuplicateCheck(MemberRequestDto memberReqDto) {
        if(memberRepository.findByUsername(memberReqDto.getUsername()).isPresent()){
            throw new GlobalException(ErrorCode.DUPLICATE_MEMBER_ID);
            // ex) return ResponseDto.fail()
        }
    }


}
