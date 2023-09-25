package com.yse.dev.book.dto;

import java.util.List;

import com.yse.dev.book.entity.Book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookListResponseDTO {
	private int currentPage;  // 현재 페이지 번호
    private int totalPages;   // 총 페이지 수
    private List<Book> items; // 현재 페이지에 보여줄 항목들

	
	
}