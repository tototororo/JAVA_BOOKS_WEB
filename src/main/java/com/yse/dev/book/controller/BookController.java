package com.yse.dev.book.controller;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yse.dev.book.dto.BookCreateDTO;
import com.yse.dev.book.dto.BookEditDTO;
import com.yse.dev.book.dto.BookEditResponseDTO;
import com.yse.dev.book.dto.BookListResponseDTO;
import com.yse.dev.book.dto.BookReadResponseDTO;
import com.yse.dev.book.service.BookService;

@Controller   //반환 값을 '뷰'로 전달
public class BookController {
	//필드 의존성 주입 @Autowired어노테이션에 의해 스프링이 알아서 인스턴스 생성
	@Autowired  
	private BookService bookService;
	
	///////////////////////////////////////////////
	//책 생성 기능 페이지
	@GetMapping("/book/create")
	public String create() {
		return "book/create";
	}
	//생성 요청시 디비에 bookCreateDTO통해 데이터 입력 후 상세화면 창으로 이동
	@PostMapping("/book/create")
	public String insert(BookCreateDTO bookCreateDTO) {
		Integer bookId = this.bookService.insert(bookCreateDTO);
		return String.format("redirect:/book/read/%s",bookId);
	}
	
	/////////////////////////////////////////////////
	//책 읽기 기능 페이지
	//인자에 해당하는 GET요청받으면 실행 책 읽기 기능 페이지로 이동
	@GetMapping("/book/read/{bookId}")
	public ModelAndView read(@PathVariable Integer bookId) {
		//데이터와 화면을 담을 수 있는 객체
		ModelAndView mav = new ModelAndView();
		
		// try 문 사용 예외처리 방식
		try {
			BookReadResponseDTO bookReadResponseDTO = this.bookService.read(bookId);
			//뷰에 전달할 데이터 설정 (뷰에서 사용할 이름,뷰에서 사용할 값)
			mav.addObject("bookReadResponseDTO", bookReadResponseDTO);
			//뷰 경로 지정
			mav.setViewName("book/read");
		}
		//책정보를 찾을 수 없다면 오류 응답을 보여주고 모록 페이지로 이동
		catch(NoSuchElementException ex) {
			//오류상태 422 설정
			mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
			mav.addObject("message", "책 정보가 없습니다.");
			mav.addObject("location", "/book");
			mav.setViewName("common/error/422");
		}
		//설정한 객체 리턴
		return mav;
	}
	
///////////////////////////////////////////////////////////////////
	//책 수정 기능 페이지
	@GetMapping("/book/edit/{bookId}")
	public ModelAndView edit(@PathVariable Integer bookId) throws NoSuchElementException {
		ModelAndView mav = new ModelAndView();
		BookEditResponseDTO bookEditResponseDTO = this.bookService.edit(bookId);
		mav.addObject("bookEditResponseDTO", bookEditResponseDTO);
		mav.setViewName("book/edit");
		return mav;
	}
	//책 정보 수정
	@PostMapping("/book/edit/{bookId}")
	//유효성 검사 위해 @Validated 사용
	public ModelAndView update(@Validated BookEditDTO bookEditDTO,	Errors errors) {
		//오류가 있다면
		if (errors.hasErrors()) {
			String errorMessage = errors.getFieldErrors()
				//스트림객체로 변경
				.stream()
				//람다식 사용 각 요소 변형
				.map(x -> x.getField() + " : " + x.getDefaultMessage())
				//문자열로 변경
				.collect(Collectors.joining("\n"));
			return this.error422(errorMessage, String.format("/book/edit/%s", bookEditDTO.getBookId()));
		}
		this.bookService.update(bookEditDTO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(String.format("redirect:/book/read/%s", bookEditDTO.getBookId()));
		return mav;
	}
	////////////////////////////////////////////////////////////
	//첵 삭제 기능
	@PostMapping("/book/delete")
	//매개변수가 하나뿐 일 땐 DTO를 안 만들어도 됨. 
	public String delete(Integer bookId) throws NoSuchElementException{
		this.bookService.delete(bookId);
		return "redirect:/book/list";
	}
	////////////////////////////////////////////////////////
	//책 목록 페이지
	//두 개의 포인트에 맵핑
	@GetMapping(value= {"/book/list", "/book"})
	public ModelAndView  bookList(String title, Integer page, ModelAndView mav){
		BookListResponseDTO books = this.bookService.bookList(title, page);
		mav.addObject("books", books);
		mav.setViewName("/book/list");
	    return mav;
	}

		
	////////////////////////////////////////////////////////	
	//에외 처리 함수화 방식
	@ExceptionHandler(NoSuchElementException.class)
	public ModelAndView noSuchElementExceptionHandler(NoSuchElementException ex) {
		return this.error422("책 정보가 없습니다.", "/book/list");
	}

	private ModelAndView error422(String message, String location) {
		ModelAndView mav = new ModelAndView();
		mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
		mav.addObject("message", message);
		mav.addObject("location", location);
		mav.setViewName("common/error/422");
		return mav;
	}
		
		
	
}
