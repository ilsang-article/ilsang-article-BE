package com.ilcle.ilcle_back.service;

import com.ilcle.ilcle_back.entity.Member;
import com.ilcle.ilcle_back.entity.PostLike;
import com.ilcle.ilcle_back.repository.PostLikeRepository;
import com.ilcle.ilcle_back.utils.ValidateCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MypostService {
    private final ValidateCheck validateCheck;

    private final PostLikeRepository postLikeRepository;

    //찜한글 클릭시 자동 읽음 체크
    @Transactional
    public String saveLikeReadCheck(String username,Long postId) {
        //사용자가 있는지 확인
        Member member = validateCheck.getMember(username);
        //해당 찜한글 조회
        PostLike postLike = postLikeRepository.findByMemberAndPostId(member,postId);
        postLike.updateLikeReadCheck(true);
        return "찜한글 읽음 확인";
    }

    //찜한글 읽음 표시 수동 삭제
    @Transactional
    public String deleteLikeReadCheck(String username,Long postId) {
        //사용자가 있는지 확인
        Member member = validateCheck.getMember(username);
        //해당 찜한글 조회
        PostLike postLike = postLikeRepository.findByMemberAndPostId(member,postId);
        postLike.updateLikeReadCheck(false);
        return "찜한글 읽음 취소";
    }
}
