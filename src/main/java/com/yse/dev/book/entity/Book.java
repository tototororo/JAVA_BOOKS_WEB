package com.yse.dev.book.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookId;
	
	@Column(length = 200)
	private String title;
	
	private Integer price;
	
	@CreationTimestamp
	private LocalDateTime insertDateTime;
	
	//참조하기 위해 BookLog객체 받을 자효형 생성
	//관계 정의 mappedBy="book"은 주인 클래스 BookLog의 PK변수
	@OneToMany(mappedBy="book", fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
	@Builder.Default //기본겂 null설정 방지
	private List<BookLog> bookLogList = new ArrayList();

}
