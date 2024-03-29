package com.ilcle.ilcle_back.controller;

import com.ilcle.ilcle_back.dto.ResponseDto;
import com.ilcle.ilcle_back.dto.response.PostResponseDto;
import com.ilcle.ilcle_back.service.PostReadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



@Tag(name = "최근 읽은 글 컨트롤러")
@RequiredArgsConstructor
@RestController
public class RecentReadController {

    private final PostReadService postReadService;

    //글 클릭시 읽은 글로 저장
    @PostMapping("/posts/{postId}/read")
    public ResponseDto<String> saveReadCheck(@AuthenticationPrincipal UserDetails userDetails,
                                       @PathVariable Long postId) {
        return ResponseDto.success(postReadService.savePostRead(userDetails.getUsername(), postId));
    }

    //최근 읽은 글 조회
    @GetMapping("/posts/recent")
    public ResponseDto<Page<PostResponseDto>> getRecentRead(@AuthenticationPrincipal UserDetails userDetails,
                                                            @PageableDefault(page = 10, sort = "readCheckTime", direction = Sort.Direction.DESC)  Pageable pageable) {
        Page<PostResponseDto> postReadList = postReadService.getRecentRead(userDetails.getUsername(),pageable);
        return ResponseDto.success(postReadList);
    }
}


