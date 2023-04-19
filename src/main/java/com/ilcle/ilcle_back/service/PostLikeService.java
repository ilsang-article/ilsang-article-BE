package com.ilcle.ilcle_back.service;

import com.ilcle.ilcle_back.dto.ResponseDto;
import com.ilcle.ilcle_back.dto.response.PostLikeResponseDto;
import com.ilcle.ilcle_back.entity.Member;
import com.ilcle.ilcle_back.entity.Post;
import com.ilcle.ilcle_back.entity.PostLike;
import com.ilcle.ilcle_back.repository.PostLikeRepository;
import com.ilcle.ilcle_back.utils.ValidateCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final ValidateCheck validateCheck;

    // 게시글 찜하기
    @Transactional
    public ResponseDto<PostLikeResponseDto> postLikeUp(Long postId, String username) {
        Member member = validateCheck.getMember(username);
        Post post = validateCheck.getPost(postId);
        PostLike postLike = validateCheck.getPostLike(postId, username);

        // 이미 찜한 글인 경우 삭제
        if (postLike != null) {
            postLikeRepository.delete(postLike);
            // 찜했던 글이 아닌 경우 추가
        } else {
            PostLike createPostLike = new PostLike(member, post);
            postLikeRepository.save(createPostLike);
        }

        return ResponseDto.success(
                PostLikeResponseDto.builder()
                        .likeCheck(postLike == null)
                        .build()
        );
    }
}
