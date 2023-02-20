package com.ilcle.ilcle_back.controller;

import com.ilcle.ilcle_back.dto.ResponseDto;
import com.ilcle.ilcle_back.dto.response.RecentReadResponseDto;
import com.ilcle.ilcle_back.entity.Post;
import com.ilcle.ilcle_back.service.RecentReadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "최근 읽은 글 컨트롤러")
@RequiredArgsConstructor
@RestController
public class RecentReadController {

    private final RecentReadService recentReadService;

    //글 클릭시 읽은 글로 저장
    @PostMapping("/post/{postId}/read")
    public ResponseDto<String> saveReadCheck(@AuthenticationPrincipal UserDetails userDetails,
                                       @PathVariable Long postId) {
        return ResponseDto.success(recentReadService.saveReadCheck(userDetails.getUsername(), postId));
    }

    //최근 읽은 글 조회
    @GetMapping("/posts/recent")
    public ResponseDto<List<RecentReadResponseDto>> getRecentRead(@AuthenticationPrincipal UserDetails userDetails) {
        List<RecentReadResponseDto> recentReadList = recentReadService.getRecentRead(userDetails.getUsername());
        return ResponseDto.success(recentReadList);
    }
}


