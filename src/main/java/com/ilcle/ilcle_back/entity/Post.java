package com.ilcle.ilcle_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	private boolean likeCheck;

	@Column
	private boolean likeReadCheck;

	public Post(Long postId) {
		this.id = postId;
	}

	public void updateLikeCheck(boolean likeCheck) {
		this.likeCheck = likeCheck;
	}
	public void updateLikeReadCheck ( boolean likeReadCheck){
			this.likeReadCheck = likeReadCheck;
	}
}
