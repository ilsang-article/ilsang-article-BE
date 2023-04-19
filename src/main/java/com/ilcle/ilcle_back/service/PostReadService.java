package com.ilcle.ilcle_back.service;

import com.ilcle.ilcle_back.dto.response.PostResponseDto;
import com.ilcle.ilcle_back.entity.Member;
import com.ilcle.ilcle_back.entity.Post;
import com.ilcle.ilcle_back.entity.PostRead;
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
public class PostReadService {
    private final ValidateCheck validateCheck;
    private final PostReadRepository postReadRepository;
    private final PostRepository postRepository;

    //글 클릭시 읽은 글로 저장
    @Transactional
    public String savePostRead(String username, Long postId) {
        //사용자가 있는지 확인
        Member member = validateCheck.getMember(username);
        //글이 있는지 확인
        Post post = validateCheck.getPost(postId);
        //읽은 적 있는 글인지 확인
        PostRead postRead = validateCheck.getPostRead(postId, member.getId());

        //읽은 적 있는 글이면 읽은 날짜만 업데이트
        if (postRead != null) {
            postRead.updateReadCheckTime();
            //처음 읽는 글이면 읽은 글에 추가
        } else {
            PostRead readCheck = new PostRead(member, post);
            postReadRepository.save(readCheck);
        }
        return "읽음 확인";
    }

    //최근 읽은 글 조회
    @Transactional
    public Page<PostResponseDto> getRecentRead(String username, Pageable pageable) {
        //사용자가 있는지 확인
        Long memberId = validateCheck.getMember(username).getId();

        Page<Post> recentReadList = postRepository.getRecentReadPosts(memberId, pageable);
        List<PostResponseDto> recentReadResponseDtoList = new ArrayList<>();
        for (Post post : recentReadList) {
            boolean likeCheck = validateCheck.getPostLike(post.getId(), username) != null;

            PostResponseDto recentReadResponseDto =
                    PostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .contents(post.getContents())
                            .url(post.getUrl())
                            .imageUrl(post.getImageUrl())
                            .writeDate(post.getWriteDate())
                            .writer(post.getWriter())
                            .likeCheck(likeCheck)
                            .build();
            recentReadResponseDtoList.add(recentReadResponseDto);
        }
        return new PageImpl<>(recentReadResponseDtoList, pageable, recentReadList.getTotalElements());
    }
}
