package com.ilcle.ilcle_back.service;

import com.ilcle.ilcle_back.dto.ResponseDto;
import com.ilcle.ilcle_back.dto.request.LoginRequestDto;
import com.ilcle.ilcle_back.dto.request.MemberRequestDto;
import com.ilcle.ilcle_back.dto.response.LoginResponseDto;
import com.ilcle.ilcle_back.dto.response.MemberResponseDto;
import com.ilcle.ilcle_back.entity.Member;
import com.ilcle.ilcle_back.entity.RefreshToken;
import com.ilcle.ilcle_back.exception.ErrorCode;
import com.ilcle.ilcle_back.exception.GlobalException;
import com.ilcle.ilcle_back.repository.MemberRepository;
import com.ilcle.ilcle_back.repository.RefreshTokenRepository;
import com.ilcle.ilcle_back.security.jwt.JwtUtil;
import com.ilcle.ilcle_back.security.jwt.TokenDto;
import com.ilcle.ilcle_back.utils.ValidateCheck;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ValidateCheck validateCheck;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    //회원가입
    @Transactional
    public ResponseEntity<ResponseDto<MemberResponseDto>> signup(MemberRequestDto memberReqDto) {

        // username 중복 검사
        validateCheck.usernameDuplicateCheck(memberReqDto);

        // 비빌번호 확인 & 비빌번호 불일치
        if(!memberReqDto.getPassword().equals(memberReqDto.getPasswordConfirm())){
            throw new GlobalException(ErrorCode.BAD_PASSWORD_CONFIRM);
        }
        Member member = Member.builder()
                .username(memberReqDto.getUsername())
                .password(passwordEncoder.encode(memberReqDto.getPassword()))
                .build();
        memberRepository.save(member);
        return ResponseEntity.ok().body(ResponseDto.success(
                MemberResponseDto.builder()
                        .username(member.getUsername())
                        .createdAt(member.getCreatedAt())
                        .build()
        ));
    }

    //로그인
    @Transactional
    public ResponseEntity<ResponseDto<LoginResponseDto>> login(LoginRequestDto loginReqDto, HttpServletResponse response) {

        Member member = validateCheck.isPresentMember(loginReqDto.getUsername());

        //사용자가 있는지 확인
        if(null == member){
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        }
        //비밀번호가 맞는지 확인
        if(!member.validatePassword(passwordEncoder, loginReqDto.getPassword())){
            throw new GlobalException(ErrorCode.BAD_PASSWORD);
        }

        TokenDto tokenDto = jwtUtil.createAllToken(loginReqDto.getUsername());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberUsername(loginReqDto.getUsername());

        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginReqDto.getUsername());
            refreshTokenRepository.save(newToken);
        }
        setHeader(response, tokenDto);

        return ResponseEntity.ok().body(ResponseDto.success(
                LoginResponseDto.builder()
                        .username(member.getUsername())
                        .createdAt(member.getCreatedAt())
                        .build()
        ));
    }
    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

    //로그아웃
    public String logout(Member member) {
        refreshTokenRepository.deleteByMemberUsername(member.getUsername());
        return "로그아웃 완료";
    }


    //아이디 중복 확인
    @Transactional(readOnly = true) //읽기 전용 쿼리의 성능 최적화
    public ResponseEntity<ResponseDto<String>> usernameDuplicateCheck(String username) {
        if(memberRepository.existsByUsername(username)){
            throw new GlobalException(ErrorCode.DUPLICATE_MEMBER_ID);
        }else{
            return ResponseEntity.ok().body(ResponseDto.success("사용 가능한 아이디 입니다."));
        }
    }
}
