package com.ilcle.ilcle_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String title;

	@Column
	private String contents;

	@Column
	private String url;

	@Column
	private String imageUrl;

	@Column
	private String writeDate;

	@Column
	private String writer;

	@Column
	private boolean postLikeCheck;

	public Post(Long postId) {
		this.id = postId;
	}

	public void updatePostLikeCheck(boolean postLikeCheck) {
		this.postLikeCheck = postLikeCheck;
	}
}
