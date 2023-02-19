package com.ilcle.ilcle_back.service;

import com.ilcle.ilcle_back.dto.ResponseDto;
import com.ilcle.ilcle_back.dto.response.RecentReadResponseDto;
import com.ilcle.ilcle_back.entity.Member;
import com.ilcle.ilcle_back.entity.RecentRead;
import com.ilcle.ilcle_back.repository.RecentReadRepository;
import com.ilcle.ilcle_back.utils.ValidateCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecentReadService {
    private final ValidateCheck validateCheck;

    private final RecentReadRepository recentReadRepository;

//    public ResponseEntity<ResponseDto<RecentReadResponseDto>> getRecentRead(Long memberId) {
//
//        //사용자가 있는지 확인
//        Member member = validateCheck.getMember(memberId);
//
//        RecentRead recentRead = recentReadRepository.findRecentReadById(memberId)
//
//
//
//
//
//    }
}
