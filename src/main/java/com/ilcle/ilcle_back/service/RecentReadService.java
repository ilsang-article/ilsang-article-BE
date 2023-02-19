package com.ilcle.ilcle_back.service;

import com.ilcle.ilcle_back.entity.Member;
import com.ilcle.ilcle_back.entity.Post;
import com.ilcle.ilcle_back.entity.RecentRead;
import com.ilcle.ilcle_back.exception.ErrorCode;
import com.ilcle.ilcle_back.exception.GlobalException;
import com.ilcle.ilcle_back.repository.PostRepository;
import com.ilcle.ilcle_back.repository.RecentReadRepository;
import com.ilcle.ilcle_back.utils.ValidateCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RecentReadService {
    private final ValidateCheck validateCheck;

    private final RecentReadRepository recentReadRepository;

    private final PostRepository postRepository;

    @Transactional
    public String saveReadCheck(String username,Long postId) {
        //사용자가 있는지 확인
        Member member = validateCheck.getMember(username);
        //글이 있는지 확인
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new GlobalException(ErrorCode.POST_NOT_FOUND)
        );
        //읽은 적 있는 글인지 확인
        Optional<RecentRead> recentRead = recentReadRepository.findByMemberUsernameAndPostId(username, postId);

        //읽은 적 있는 글이면 읽은 날짜만 업데이트
        if(recentRead.isPresent()) {
            RecentRead updateDate = recentRead.get();
            updateDate.updateReadCheckTime();
        //처음 읽는 글이면 읽은 글에 추가
        }else{
            RecentRead readCheck = new RecentRead(member,post);
            recentReadRepository.save(readCheck);
        }
        return "읽음 확인";
    }
}
