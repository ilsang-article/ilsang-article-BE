package com.ilcle.ilcle_back.controller;

import com.ilcle.ilcle_back.dto.ResponseDto;
import com.ilcle.ilcle_back.service.MypostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
