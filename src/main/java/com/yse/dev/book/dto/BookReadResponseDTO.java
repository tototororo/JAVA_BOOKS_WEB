package com.yse.dev.book.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.yse.dev.book.entity.Book;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BookReadResponseDTO {
	private Integer bookId;
	private String title;
	private Integer price;
	private LocalDateTime insertDateTime;
	
	//책 정보 응답에 책 기록을 포함
	//책 정보:책 기록 = 1:N 관계이므로 리스트
	private List<BookLogReadResponseDTO> bookLogs;
	
	//book 엔티티에서 값을 받아 응답객체 필드에 채운다
	//여러 엔티티에서 값을 받아와서 응답객체에 조합해도 문제 없는 방식
	public BookReadResponseDTO fromBook(Book book) {
		this.bookId = book.getBookId();
		this.title = book.getTitle();
		this.price = book.getPrice();
		this.insertDateTime = book.getInsertDateTime();
		
		this.bookLogs = book.getBookLogList()  // book 엔티티의 bookLogList 항목을 가져와서
				.stream()	// Stream 객체로 바꾸고
				//개별 항목에 bookLog 타입 객체를 BookLogReadResponseDTO 타입으로 바꾸는 함수를 적용
				.map(bookLog -> BookLogReadResponseDTO.BookLogFactory(bookLog))
				.collect(Collectors.toList()); //List 타입으로 변경
		return this;
	}
	
	//정적 메소드로 객체를 생성하는 팩토리 패턴
	public static BookReadResponseDTO BookFactory(Book book) {
		BookReadResponseDTO bookReadResponseDTO = new BookReadResponseDTO();
		bookReadResponseDTO.fromBook(book);
		return bookReadResponseDTO;
	}
}
