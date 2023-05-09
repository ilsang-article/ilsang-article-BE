package com.ilcle.ilcle_back.service;

import com.ilcle.ilcle_back.dto.response.MyPostResponseDto;
import com.ilcle.ilcle_back.entity.Post;
import com.ilcle.ilcle_back.entity.PostRead;
import com.ilcle.ilcle_back.exception.ErrorCode;
import com.ilcle.ilcle_back.exception.GlobalException;
import com.ilcle.ilcle_back.repository.PostReadRepository;
import com.ilcle.ilcle_back.repository.PostRepository;
import com.ilcle.ilcle_back.utils.ValidateCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MypostService {
    private final ValidateCheck validateCheck;
    private final PostRepository postRepository;
    private final PostReadRepository postReadRepository;


    //찜한글 읽음 표시 수동 삭제
    @Transactional
    public String deletePostRead(String username, Long postId) {
        Long memberId = validateCheck.getMember(username).getId();
        //찜한글 존재여부 확인
        validateCheck.isLike(postId, username);
        PostRead postRead = validateCheck.getPostRead(postId, memberId);

        // 읽은 글이 맞으면 읽은 글에서 삭제 처리
        if (postRead != null) {
            postReadRepository.delete(postRead);
        // 읽은 글에서 없으면 예외처리
        } else throw new GlobalException(ErrorCode.POST_READ_NOT_FOUND);

        return "찜한글 읽음 취소";
    }

    // 찜한글 조회(기본: 최신순, 필터링: 읽은순/안 읽은순)
    public Page<MyPostResponseDto> filter(String username, Pageable pageable, Boolean read) {

        // 사용자가 있는지 확인
        Long memberId = validateCheck.getMember(username).getId();
        // 찜한글 목록
        Page<Post> FilteredMyPostList = postRepository.findFilterByMember(memberId, pageable, read);
        List<MyPostResponseDto> myPostsResponseDtoList = new ArrayList<>();

        for (Post post : FilteredMyPostList) {

            // 읽었는지 체크
            boolean readCheck = validateCheck.getPostRead(post.getId(), memberId) != null;
            // 찜했는지 체크
            boolean likeCheck = validateCheck.getPostLike(post.getId(), username) != null;

            MyPostResponseDto myPostResponseDto =
                    MyPostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .contents(post.getContents())
                            .url(post.getUrl())
                            .imageUrl(post.getImageUrl())
                            .writeDate(post.getWriteDate())
                            .likeCheck(likeCheck)
                            .readCheck(readCheck)
                            .writer(post.getWriter())
                            .build();
            myPostsResponseDtoList.add(myPostResponseDto);
        }

        return new PageImpl<>(myPostsResponseDtoList, pageable, FilteredMyPostList.getTotalElements());
    }
}
