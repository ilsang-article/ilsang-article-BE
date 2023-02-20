package com.ilcle.ilcle_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.ui.context.Theme;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostLike {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@Column
	private boolean likeReadCheck;

	public PostLike(Member member, Post post) {
		this.member = member;
		this.post = post;
	}

	public void updateLikeReadCheck(boolean likeReadCheck) {
		this.likeReadCheck = likeReadCheck;
	}
}
