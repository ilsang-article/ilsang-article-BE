package com.ilcle.ilcle_back.repository;

import com.ilcle.ilcle_back.entity.Member;
import com.ilcle.ilcle_back.entity.Post;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.ilcle.ilcle_back.entity.QPost.post;
import static com.ilcle.ilcle_back.entity.QPostLike.postLike;

@RequiredArgsConstructor
public class PostLikeRepositoryImpl {

	private final JPAQueryFactory jpaQueryFactory;

	// 찜한글 조회(기본: 최신순, 필터링: 읽은순/안 읽은순)
	public Page<Post> findFilterByMember(Member member, Pageable pageable, Boolean read) {

		List<Post> result = jpaQueryFactory
				.select(post)
				.from(postLike)
				.where(
						postLike.member.id.eq(member.getId()),
						eqRead(read)
				)
				.limit(pageable.getPageSize())
				.offset(pageable.getOffset())
				.orderBy(sort(pageable), post.writeDate.desc())
				.fetch();

		long totalSize = jpaQueryFactory
				.select(post)
				.from(postLike)
				.where(
						postLike.member.id.eq(member.getId()),
						eqRead(read))
				.fetch().size();

		return new PageImpl<>(result, pageable, totalSize);
	}

	private BooleanExpression eqRead(Boolean read) {
		if (read == null) {
			return null;
		}
		return post.likeReadCheck.eq(read);
    }

	//정렬하기
	private OrderSpecifier<?> sort(Pageable pageable) {
		if (!pageable.getSort().isEmpty()) {
			for (Sort.Order order : pageable.getSort()) {
				switch (order.getProperty()) {
					case "read":
						return new OrderSpecifier<>(Order.DESC, post.likeReadCheck);
					case "unread":
						return new OrderSpecifier<>(Order.ASC, post.likeReadCheck);
				}
			}
		}
		return new OrderSpecifier<>(Order.DESC, post.writeDate);
	}
}
