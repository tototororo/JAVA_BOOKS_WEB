package com.yse.dev.book.service;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.yse.dev.book.dto.BookCreateDTO;
import com.yse.dev.book.dto.BookEditDTO;
import com.yse.dev.book.dto.BookEditResponseDTO;
import com.yse.dev.book.dto.BookListResponseDTO;
import com.yse.dev.book.dto.BookReadResponseDTO;
import com.yse.dev.book.entity.Book;
import com.yse.dev.book.entity.BookRepository;

@Service
public class BookService {
	private BookRepository bookRepository;
	
	//생성자 의존성 주입
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	//c(생성)r(읽기)u(갱신)d(삭제) 기능 구현, 생성외에는 조회->처리 작업을 거치기 때문에 객체를 생성한다. 
	//데이터 생성
	public Integer insert(BookCreateDTO bookCreateDTO) {
		//빌더 패턴 사용
		Book book = Book.builder() //BookBuilder 객체 생성
				.title(bookCreateDTO.getTitle()) //title 데이터 세팅
				.price(bookCreateDTO.getPrice()) //price 데이터 세팅
				.build(); //Book 객체 생성
		//데이터베이스에 insert
		this.bookRepository.save(book);
		return book.getBookId();
	}
	//데이터 읽기
	public BookReadResponseDTO read(Integer bookId) throws NoSuchElementException {
		//데이터 베이스에 저장된 책 정보 가져오기
		Book book = this.bookRepository.findById(bookId).orElseThrow();
		// 정적 메소드 이용방식
		return BookReadResponseDTO.BookFactory(book);
	}
	//데이터 수정 화면을 위해 값 읽어오기
	public BookEditResponseDTO edit(Integer bookId) throws NoSuchElementException {
		Book book = this.bookRepository.findById(bookId).orElseThrow();
		return BookEditResponseDTO.BookFactory(book);
	}
	//데이터 수정
	public void update(BookEditDTO bookEditDTO) throws NoSuchElementException {
		//데이터 베이스에 저장된 책 정보 가져오기
		Book book = this.bookRepository.findById(bookEditDTO.getBookId()).orElseThrow();
		//책 정보 변경
		book = bookEditDTO.fill(book);
		//데이터 베이스 연동 후 수정 JPA.save()는 pk 속성값 유무로 입력과 수정을 구분.
		this.bookRepository.save(book);
	}
	//데이터 삭제
	public void delete(Integer bookId) throws NoSuchElementException {
		Book book = this.bookRepository.findById(bookId).orElseThrow();
		this.bookRepository.delete(book);
	}
	//데이터 목록 읽어오기
	public BookListResponseDTO bookList(String title, Integer page) {
		final int pageSize = 3;
		//페이지 컨트롤
		if (page == null) {
			page = 0;
		} // page 객체는 1페이지가 0부터 시작이니 맞춰주기
		else {
			page -= 1;
		}
		// page 객체 생성
		Pageable pageable = PageRequest.of(page, pageSize, Direction.DESC, "insertDateTime");
		Page<Book> books;
		//검색 분기
		if (title == null) {		
			books = this.bookRepository.findAll(pageable);
		} else {	
			books = this.bookRepository.findByTitleContains(title, pageable);
		}
		 // DTO 생성 및 설정
		BookListResponseDTO bookListResponseDTO = new BookListResponseDTO(books.getNumber(),books.getTotalPages(),books.getContent());
		
		return  bookListResponseDTO;
	}

}
