package com.yse.dev.book.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookLog {
	//주 키 설정
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookLogId;
	// One = 부모 Many = 자식 , 자식테이블이므로 @ManyToOne
	@ManyToOne(fetch = FetchType.LAZY)
	//외래키 설정
	@JoinColumn(name = "book_id")
	private Book book;
	@Column(columnDefinition = "TEXT")
	private String comment;
	private Integer page;
	@CreationTimestamp
	private LocalDateTime insertDateTime;
}