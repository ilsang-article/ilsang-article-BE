package com.ilcle.ilcle_back.repository;

import com.ilcle.ilcle_back.entity.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static com.ilcle.ilcle_back.entity.QPost.post;
import static com.ilcle.ilcle_back.entity.QPostLike.postLike;
import static com.ilcle.ilcle_back.entity.QPostRead.postRead;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // 전체글 조회(최신순), 검색
    @Override
    public Page<Post> getAllPosts(Pageable pageable, String search) {
        List<Post> result = jpaQueryFactory
                .selectFrom(post)
                .where(eqSearch(search))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(post.writeDate.desc())
                .fetch();

        long totalSize = jpaQueryFactory
                .selectFrom(post)
                .where(eqSearch(search))
                .fetch().size();

        return new PageImpl<>(result, pageable, totalSize);
    }

    // 찜한글 조회(기본: 최신순, 필터링: 읽음/안 읽음)
    public Page<Post> findFilterByMember(Long memberId, Pageable pageable, Boolean read) {

        List<Post> result = jpaQueryFactory
                // 가져올것 : 게시글
                .selectFrom(post)
                // 현재 사용자가 찜한 글 중 postId 기준으로 post - postLike join
                .leftJoin(postLike)
                .on(postLike.member.id.eq(memberId).and(postLike.post.id.eq(post.id)))
                // 현재 사용자가 읽은 글 중 postId 기준으로 post - postRead join
                // 조인 결과 postRead에는 없고 post에만 있으면 현재 사용자가 읽지 않은 글
                .leftJoin(postRead)
                .on(postRead.member.id.eq(memberId).and(postRead.post.id.eq(post.id)))
                .where(
                        // 조건1 : 현재 로그인한 사용자가 찜한 글
                        postLike.member.id.eq(memberId),
                        // 조건2: 현재 로그인한 사용자가 읽은 글/안 읽은 글(param으로 들어오는 read값에 따라)
                        isRead(read,memberId)
                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(post.writeDate.desc())
                .fetch();

        long totalSize = jpaQueryFactory
                .selectFrom(post)
                .leftJoin(postLike)
                .on(postLike.member.id.eq(memberId).and(postLike.post.id.eq(post.id)))
                .leftJoin(postRead)
                .on(postRead.member.id.eq(memberId).and(postRead.post.id.eq(post.id)))
                .where(
                        postLike.member.id.eq(memberId),
                        isRead(read,memberId)
                )
                .fetch().size();

        return new PageImpl<>(result, pageable, totalSize);
    }

    private BooleanExpression eqSearch(String search) {
        return search != null ? post.title.contains(search).or(post.contents.contains(search)) : null;
    }

    private BooleanExpression isRead(Boolean read, Long memberId) {
        // param 입력 없으면 null 반환하여 읽은 글 필터 조건 무시
        if (read == null) return null;
        // Read가 true면 읽은글 필터링 : 조인테이블에서 읽은 글 테이블 중 현재 로그인한 사용자의 memberId와 일치하는 값 가져오기
        else if(read)
            return postRead.member.id.eq(memberId);
        // read가 false 면 안읽은글 필터링 : 조인테이블에서 읽은 글 테이블에서 postId가 null인 값 가져오기
        // post에는 있지만 postRead에는 없는 것 -> 읽지 않은 글
        else
            return postRead.post.id.isNull();
    }


    // 최근 읽은 글 조회
    @Override
    public Page<Post> getRecentReadPosts(Long memberId, Pageable pageable) {
        List<Post> result = jpaQueryFactory
                // postRead에서 post가져오기 (join하는것보다 빠름)
                .select(post)
                .from(postRead)
                // 조건1 : 읽은 글에서 memberId가 현재 로그인한 사용자의 memberId와 일치
                .where(postRead.member.id.eq(memberId),
                        // 조건2 : 읽은 글에서 글을 읽은 시간이 현재 시간에서 3일전 이후
                        postRead.readCheckTime.gt(LocalDateTime.now().minusHours(72)))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(postRead.readCheckTime.desc())
                .fetch();

        long totalSize = jpaQueryFactory
                .select(post)
                .from(postRead)
                .where(postRead.member.id.eq(memberId),
                        postRead.readCheckTime.gt(LocalDateTime.now().minusHours(72)))
                .fetch().size();

        return new PageImpl<>(result, pageable, totalSize);
    }
}
