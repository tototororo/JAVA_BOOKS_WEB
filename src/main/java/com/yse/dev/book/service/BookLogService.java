package com.yse.dev.book.service;

import org.springframework.stereotype.Service;

import com.yse.dev.book.dto.BookLogCreateDTO;
import com.yse.dev.book.dto.BookLogCreateResponseDTO;
import com.yse.dev.book.entity.Book;
import com.yse.dev.book.entity.BookLog;
import com.yse.dev.book.entity.BookLogRepository;
import com.yse.dev.book.entity.BookRepository;

@Service
public class BookLogService {
	private BookRepository bookRepository;
	private BookLogRepository bookLogRepository;

	public BookLogService(BookRepository bookRepository, BookLogRepository bookLogRepository) {
		this.bookRepository = bookRepository;
		this.bookLogRepository = bookLogRepository;
	}

	public BookLogCreateResponseDTO insert(BookLogCreateDTO bookLogCreateDTO) {
		// 책 정보 가져오기 없는 데이터면 오류반환
		Book book = this.bookRepository.findById(bookLogCreateDTO.getBookId()).orElseThrow();
		// 객체 형식으로 표현하는 데이터 베이스 테이블이기 때문에 외래키 대신 객체로 BookLog에 주입
		BookLog bookLog = BookLog.builder().book(book)
											.comment(bookLogCreateDTO.getComment())
											.page(bookLogCreateDTO.getPage())
											.build();
		bookLog = this.bookLogRepository.save(bookLog);
		return BookLogCreateResponseDTO.BookLogFactory(bookLog);
	}
	

}