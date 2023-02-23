package com.ilcle.ilcle_back.service;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.ilcle.ilcle_back.dto.ResponseDto;
import com.ilcle.ilcle_back.dto.response.MyPostResponseDto;
import com.ilcle.ilcle_back.dto.response.PostResponseDto;
import com.ilcle.ilcle_back.entity.Member;
import com.ilcle.ilcle_back.entity.Post;
import com.ilcle.ilcle_back.entity.PostLike;
import com.ilcle.ilcle_back.exception.ErrorCode;
import com.ilcle.ilcle_back.exception.GlobalException;
import com.ilcle.ilcle_back.repository.PostLikeRepository;
import com.ilcle.ilcle_back.repository.PostRepository;
import com.ilcle.ilcle_back.utils.ValidateCheck;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.context.Theme;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ilcle.ilcle_back.exception.ErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MypostService {
    private final ValidateCheck validateCheck;

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    //찜한글 클릭시 자동 읽음 체크
    @Transactional
    public String saveLikeReadCheck(String username,Long postId) {
        //사용자가 있는지 확인
        Member member = validateCheck.getMember(username);
        //해당 찜한글 조회
        PostLike postLike = postLikeRepository.findByMemberAndPostId(member,postId);
        postLike.getPost().updateLikeReadCheck(true);
        return "찜한글 읽음 확인";
    }

    //찜한글 읽음 표시 수동 삭제
    @Transactional
    public String deleteLikeReadCheck(String username,Long postId) {
        //사용자가 있는지 확인
        Member member = validateCheck.getMember(username);
        //해당 찜한글 조회
        PostLike postLike = postLikeRepository.findByMemberAndPostId(member,postId);
        postLike.getPost().updateLikeReadCheck(false);
        return "찜한글 읽음 취소";
    }

    // 찜한글 조회(기본: 최신순, 필터링: 읽은순/안 읽은순)
    public Page<MyPostResponseDto> filter(String username, Pageable pageable, Boolean read) {

        // 사용자가 있는지 확인
        Member member = validateCheck.getMember(username);
        // 찜한글 목록
        Page<Post> FilteredMyPostList = postLikeRepository.findFilterByMember(member, pageable, read);
        List<MyPostResponseDto> myPostsResponseDtoList = new ArrayList<>();

        for(Post post : FilteredMyPostList) {

            MyPostResponseDto myPostResponseDto =
                    MyPostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .contents(post.getContents())
                            .url(post.getUrl())
                            .imageUrl(post.getImageUrl())
                            .writeDate(post.getWriteDate())
                            .likeCheck(post.isLikeCheck())
                            .likeReadCheck(post.isLikeReadCheck())
                            .writer(post.getWriter())
                            .build();
            myPostsResponseDtoList.add(myPostResponseDto);
        }
        Page<MyPostResponseDto> myPostResponseDtoLists =
                new PageImpl<>(myPostsResponseDtoList, pageable, FilteredMyPostList.getTotalElements());

        return myPostResponseDtoLists;
    }
}
