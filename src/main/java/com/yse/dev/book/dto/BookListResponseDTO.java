package com.yse.dev.book.dto;

import java.util.List;

import com.yse.dev.book.entity.Book;

import lombok.Getter;
import lombok.Setter;

//기능확장 시 추가로 생성자를 생성시킬 수 있으므로 @AllArgsConstructor 사용 안함
@Getter
@Setter
public class BookListResponseDTO {
	private int currentPage;  // 현재 페이지 번호
    private int totalPages;   // 총 페이지 수
    private List<Book> items; // 현재 페이지에 보여줄 항목들

    
	public BookListResponseDTO(int currentPage, int totalPages, List<Book> items) {
		this.currentPage = currentPage;
		this.totalPages = totalPages;
		this.items = items;
	}
	
}
