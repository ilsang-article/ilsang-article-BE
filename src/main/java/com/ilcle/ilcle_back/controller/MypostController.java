package com.ilcle.ilcle_back.controller;

import com.ilcle.ilcle_back.dto.ResponseDto;
import com.ilcle.ilcle_back.dto.response.MyPostResponseDto;
import com.ilcle.ilcle_back.service.MypostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@Tag(name = "찜한글 컨트롤러")
@RequiredArgsConstructor
@RestController
public class MypostController {
    private final MypostService mypostService;

    //읽은 글에서 삭제
    @DeleteMapping("/myposts/{postId}/unread")
    public ResponseDto<?> deletePostRead(@AuthenticationPrincipal UserDetails userDetails,
                                         @PathVariable Long postId) {
        return ResponseDto.success(mypostService.deletePostRead(userDetails.getUsername(),postId));
    }

    // 찜한글 필터링(읽은글/안 읽은글)
    @GetMapping("/myposts")
    public ResponseDto<Page<MyPostResponseDto>> myPostFilter(@AuthenticationPrincipal UserDetails userDetails,
                                                             @PageableDefault Pageable pageable,
                                                             @RequestParam(value = "read", required = false) Boolean read) {
        Page<MyPostResponseDto> filteredMyPostList = mypostService.filter(userDetails.getUsername(), pageable, read);
        return ResponseDto.success(filteredMyPostList);
    }
}
