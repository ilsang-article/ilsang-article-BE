package com.ilcle.ilcle_back.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public class GlobalException extends  RuntimeException implements Supplier<ErrorCode> {

	private final ErrorCode errorCode;

	@Override
	public ErrorCode get() {

		return errorCode;
	}
}