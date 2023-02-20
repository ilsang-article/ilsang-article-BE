package com.ilcle.ilcle_back.controller;

import com.ilcle.ilcle_back.dto.ResponseDto;
import com.ilcle.ilcle_back.dto.response.MyPostResponseDto;
import com.ilcle.ilcle_back.dto.response.PostResponseDto;
import com.ilcle.ilcle_back.service.MypostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "찜한글 컨트롤러")
@RequiredArgsConstructor
@RestController
public class MypostController {
    private final MypostService mypostService;

    //찜한글 클릭시 자동 읽음 체크
    @PutMapping("/myposts/{postId}/read")
    public ResponseDto<?> saveLikeReadCheck(@AuthenticationPrincipal UserDetails userDetails,
                                                                          @PathVariable Long postId) {
        return ResponseDto.success(mypostService.saveLikeReadCheck(userDetails.getUsername(), postId));
    }

    //찜한글 읽음 표시 수동 삭제
    @PutMapping("/myposts/{postId}/unread")
    public ResponseDto<?> deleteLikeReadCheck(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable Long postId) {
        return ResponseDto.success(mypostService.deleteLikeReadCheck(userDetails.getUsername(), postId));
    }

    // 찜한글 목록(최신순)
    @GetMapping("/myposts")
    public ResponseDto<Page<MyPostResponseDto>> getAllMyPosts(@AuthenticationPrincipal UserDetails userDetails,
                                                              @PageableDefault(page = 10, sort = "writeDate", direction = Sort.Direction.DESC) Pageable pageable,
                                                              HttpServletRequest request) {
        Page<MyPostResponseDto> myPostResponseDtoList = mypostService.getAllMyPosts(userDetails.getUsername(), pageable, request);
        return ResponseDto.success(myPostResponseDtoList);
    }
}
