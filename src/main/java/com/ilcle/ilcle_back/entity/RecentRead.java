package com.ilcle.ilcle_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentRead extends BaseTimeEntity {

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
	private LocalDateTime readCheckTime;

	public RecentRead(Member member, Post post) {
		this.member = member;
		this.post = post;
		this.readCheckTime = LocalDateTime.now();
	}

	public void updateReadCheckTime() {
		this.readCheckTime = LocalDateTime.now();
	}
}

