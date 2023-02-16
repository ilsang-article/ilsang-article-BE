package com.ilcle.ilcle_back.controller;

import com.ilcle.ilcle_back.dto.ResponseDto;
import com.ilcle.ilcle_back.dto.request.LoginRequestDto;
import com.ilcle.ilcle_back.dto.request.MemberRequestDto;
import com.ilcle.ilcle_back.dto.request.UsernameRequestDto;
import com.ilcle.ilcle_back.dto.response.LoginResponseDto;
import com.ilcle.ilcle_back.dto.response.MemberResponseDto;
import com.ilcle.ilcle_back.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@Tag(name = "멤버 컨트롤러")
@RequiredArgsConstructor
@RestController
public class MemberController {

	private final MemberService memberService;

	//회원가입
	@PostMapping("/signup")
	public ResponseEntity<ResponseDto<MemberResponseDto>> registerMember(@RequestBody @Valid MemberRequestDto memberRequestDto){
		return memberService.signup(memberRequestDto);
	}

	//아이디 중복 확인
	@PostMapping("/username")
	@Operation(summary = "아이디 중복 확인", description = "아이디 중복 확인 API")
	public ResponseEntity<ResponseDto<String>> usernameDuplicateCheck(@RequestBody @Valid UsernameRequestDto usernameRequestDto){
		return memberService.usernameDuplicateCheck(usernameRequestDto.getUsername());
	}

	//로그인
	@PostMapping("/login")
	public ResponseEntity<ResponseDto<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse){
		return memberService.login(loginRequestDto, httpServletResponse);
	}

	//로그아웃
	@PostMapping("/api/logout")
	public HttpHeaders setHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
		return headers;
	}

}
