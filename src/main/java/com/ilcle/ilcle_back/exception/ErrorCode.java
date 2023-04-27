package com.ilcle.ilcle_back.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

	/* BAD_REQUEST 400 error*/
	BAD_PASSWORD(HttpStatus.BAD_REQUEST.value(), "Password incorrect", "비밀번호를 확인하세요"),

	/*UNAUTHORIZED 401 error*/

	/*FORBIDDEN 403 error*/

	/*Not Found 404 error*/
	POST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Post not Found", "글이 존재 하지 않습니다."),
	POST_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "PostLike not Found", "찜한글이 존재 하지 않습니다."),
	POST_READ_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "PostRead not Found", "읽은 글이 아닙니다."),

	/* CONFLICT 409 error*/
	BAD_PASSWORD_CONFIRM(HttpStatus.CONFLICT.value(), "Password and PasswordConfirm don't match", "비밀번호와 비밀번호 확인이 다릅니다."),
	DUPLICATE_MEMBER_ID(HttpStatus.CONFLICT.value(), "Member is duplicated", "중복된 사용자 ID가 존재합니다."),
	DUPLICATE_MEMBER_NICKNAME(HttpStatus.CONFLICT.value(), "Nickname is duplicated", "중복된 닉네임 ID가 존재합니다."),
	/*500 server error*/
	MEMBER_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Member not found", "사용자를 찾을 수 없습니다."),
	// 로그인 필요
	NEED_TO_LOGIN(HttpStatus.UNAUTHORIZED.value(), "T01", "토큰이 없습니다."),
	WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED.value(), "T02", "잘못된 타입의 토큰입니다."),
	EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "T03", "만료된 토큰입니다."),
	UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "T04", "지원되지 않는 토큰입니다."),
	WRONG_TOKEN(HttpStatus.UNAUTHORIZED.value(), "T05", "시그니처가 일치하지 않는 토큰입니다."),

	UNDEFINED_ERROR(HttpStatus.CONFLICT.value(), "E00", "알 수 없는 에러 발생!!");

	private final Integer httpStatus;
	private final String message;
	private final String detail;
}