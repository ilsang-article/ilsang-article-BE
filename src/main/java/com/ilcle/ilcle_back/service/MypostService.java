package com.ilcle.ilcle_back.service;

import com.ilcle.ilcle_back.dto.response.MyPostResponseDto;
import com.ilcle.ilcle_back.entity.Member;
import com.ilcle.ilcle_back.entity.Post;
import com.ilcle.ilcle_back.entity.PostLike;
import com.ilcle.ilcle_back.entity.PostRead;
import com.ilcle.ilcle_back.repository.PostLikeRepository;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MypostService {
    private final ValidateCheck validateCheck;

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final PostReadRepository postReadRepository;


    //찜한글 읽음 표시 수동 삭제
    @Transactional
    public String deletePostRead(String username,Long postId) {
        //사용자가 있는지 확인
        Member member = validateCheck.getMember(username);
        //해당 찜한글 조회
        Optional<PostLike> postLike = postLikeRepository.findByPostIdAndMemberUsername(postId,username);
        if(postLike.isPresent()) {
            postReadRepository.deleteByMemberIdAndPostId(member.getId(), postId);
        }
        return "찜한글 읽음 취소";
    }

    // 찜한글 조회(기본: 최신순, 필터링: 읽은순/안 읽은순)
    public Page<MyPostResponseDto> filter(String username, Pageable pageable, Boolean read) {

        // 사용자가 있는지 확인
        Member member = validateCheck.getMember(username);
        // 찜한글 목록
        Page<Post> FilteredMyPostList = postRepository.findFilterByMember(member, pageable, read);
        List<MyPostResponseDto> myPostsResponseDtoList = new ArrayList<>();

        for(Post post : FilteredMyPostList) {
            boolean readCheck = false;
            Optional<PostRead> postRead = postReadRepository.findByMemberUsernameAndPostId(username,post.getId());
            if (postRead.isPresent()) readCheck = true;
            MyPostResponseDto myPostResponseDto =
                    MyPostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .contents(post.getContents())
                            .url(post.getUrl())
                            .imageUrl(post.getImageUrl())
                            .writeDate(post.getWriteDate())
                            .likeCheck(post.isLikeCheck())
                            .readCheck(readCheck)
                            .writer(post.getWriter())
                            .build();
            myPostsResponseDtoList.add(myPostResponseDto);
        }
        Page<MyPostResponseDto> myPostResponseDtoLists =
                new PageImpl<>(myPostsResponseDtoList, pageable, FilteredMyPostList.getTotalElements());

        return myPostResponseDtoLists;
    }
}
