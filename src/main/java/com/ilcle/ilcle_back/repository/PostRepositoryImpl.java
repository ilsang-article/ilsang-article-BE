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
//				.orderBy(post.writeDate.desc())
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

	private BooleanExpression eqSearch(String search) {
		return search != null ? post.title.contains(search).or(post.contents.contains(search)) : null;
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
				if (order.getProperty().equals("read")) {
					return new OrderSpecifier<>(Order.DESC, post.likeReadCheck);
				} else {
					return new OrderSpecifier<>(Order.ASC, post.likeReadCheck);
				}
			}
		}
		return new OrderSpecifier<>(Order.DESC, post.writeDate);
	}
}