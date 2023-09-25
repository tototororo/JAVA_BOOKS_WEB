package com.yse.dev.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yse.dev.book.dto.BookLogCreateDTO;
import com.yse.dev.book.dto.BookLogCreateResponseDTO;
import com.yse.dev.book.service.BookLogService;

//@RequestBody : 요청 객체 (JSON, Form..) => 컨버터 => 자바 객체
//@ResponseBody : 자바 객체 => 컨버터 => 응답 객체(JSON)
@RestController //요청을 받는 클래스라는 것을 나타내는 @Controller + @ResponseBody  뷰를 거치지 않고 자바객체를 JSON형식으로 바꿔주는 컨버터
@RequestMapping("/book-log")
public class BookLogController {
	private BookLogService bookLogService;

	@Autowired //세터 인젝션, 메소드로 의존성 주입
	public void setBookLogService(BookLogService bookLogService) {
		this.bookLogService = bookLogService;
	}

	@PostMapping("/create")
	//입력값을 자바 객체로 변형하여 인자 전달
	public ResponseEntity<BookLogCreateResponseDTO> insert(@RequestBody BookLogCreateDTO bookLogCreateDTO) {
		BookLogCreateResponseDTO bookLogCreateResponseDTO = this.bookLogService.insert(bookLogCreateDTO);
		return ResponseEntity.ok(bookLogCreateResponseDTO);
	}
	
	
}