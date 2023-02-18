//package com.ilcle.ilcle_back.repository;
//
//import com.ilcle.ilcle_back.dto.response.PostResponseDto;
//import com.querydsl.core.types.Projections;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//
//import static com.ilcle.ilcle_back.entity.QPost.post;
//
//@RequiredArgsConstructor
//public class PostRepositoryImpl implements PostRepositoryCustom {
//
//	private final JPAQueryFactory jpaQueryFactory;
//
//	// 전체글 조회(최신순)
//	@Override
//	public Page<PostResponseDto> getAll(Pageable pageable) {
//		List<PostResponseDto> result = jpaQueryFactory.from(post)
//				.select(Projections.constructor(PostResponseDto.class,
//						post.id,
//						post.title,
//						post.contents,
//						post.url,
//						post.imageUrl,
//						post.writeDate,
//						post.writer
//				))
//				.limit(pageable.getPageSize())
//				.offset(pageable.getOffset())
//				.fetch();
//
//		return new PageImpl<>(result, pageable, result.size());
//	}
//}
