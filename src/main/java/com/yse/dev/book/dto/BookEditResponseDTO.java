package com.yse.dev.book.dto;

import java.time.LocalDateTime;

import com.yse.dev.book.entity.Book;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BookEditResponseDTO {
	private Integer bookId;
	private String title;
	private Integer price;
	private LocalDateTime insertDateTime;
	
	//book 엔티티에서 값을 받아 응답객체 필드에 채운다
	//여러 엔티티에서 값을 받아와서 응답객체에 조합해도 문제 없는 방식
	public BookEditResponseDTO fromBook(Book book) {
		this.bookId = book.getBookId();
		this.title = book.getTitle();
		this.price = book.getPrice();
		this.insertDateTime = book.getInsertDateTime();
		return this;
	}
	
	//정적 메소드로 객체를 생성하는 팩토리 패턴
	public static BookEditResponseDTO BookFactory(Book book) {
		BookEditResponseDTO bookEditResponseDTO = new BookEditResponseDTO();
		bookEditResponseDTO.fromBook(book);
		return bookEditResponseDTO;
	}
}
