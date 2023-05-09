package com.ilcle.ilcle_back.utils;

import com.ilcle.ilcle_back.dto.request.MemberRequestDto;
import com.ilcle.ilcle_back.entity.Member;
import com.ilcle.ilcle_back.entity.Post;
import com.ilcle.ilcle_back.entity.PostLike;
import com.ilcle.ilcle_back.entity.PostRead;
import com.ilcle.ilcle_back.exception.ErrorCode;
import com.ilcle.ilcle_back.exception.GlobalException;
import com.ilcle.ilcle_back.repository.MemberRepository;
import com.ilcle.ilcle_back.repository.PostLikeRepository;
import com.ilcle.ilcle_back.repository.PostReadRepository;
import com.ilcle.ilcle_back.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidateCheck {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostReadRepository postReadRepository;

    // controller에서 userdetails 안쓰는 방법
//    public Member getMember() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        return memberRepository.findByUsername(username)
//                .orElseThrow(
//                        () -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)
//                );
//    }

    // 멤버 체크
    public Member getMember(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(
                        () -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)
                );
    }

    //아이디 중복 체크
    public void usernameDuplicateCheck(MemberRequestDto memberReqDto) {
        if (memberRepository.findByUsername(memberReqDto.getUsername()).isPresent()) {
            throw new GlobalException(ErrorCode.DUPLICATE_MEMBER_ID);
            // ex) return ResponseDto.fail()
        }
    }

    // 게시글 조회
    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new GlobalException(ErrorCode.POST_NOT_FOUND));
    }

    // 찜한글 여부 확인 (없으면 예외처리)
    public void isLike(Long postId, String username) {
        postLikeRepository.findByPostIdAndMemberUsername(postId, username).orElseThrow(
                () -> new GlobalException(ErrorCode.POST_LIKE_NOT_FOUND));
    }

    // 찜한글 조회 (없으면 null)
    public PostLike getPostLike(Long postId, String username) {
        return postLikeRepository.findByPostIdAndMemberUsername(postId, username).orElse(null);
    }

    // 읽은글 조회 (없으면 null)
    public PostRead getPostRead(Long postId, Long memberId) {
        return postReadRepository.findByPostIdAndMemberId(postId, memberId).orElse(null);
    }
}
