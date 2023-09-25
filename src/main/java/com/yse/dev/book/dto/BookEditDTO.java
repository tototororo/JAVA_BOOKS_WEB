package com.yse.dev.book.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.yse.dev.book.entity.Book;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class BookEditDTO {
	@NonNull
	@Positive     //양수 이상
	private Integer bookId;
	@NonNull
	@NotBlank	//null 불가
	private String title;
	@NonNull
	@Min(1000)	//최소값
	private Integer price;

	//책 엔티티 채우기
	public Book fill(Book book) {
		book.setTitle(this.title);
		book.setPrice(this.price);
		return book;
	}
}