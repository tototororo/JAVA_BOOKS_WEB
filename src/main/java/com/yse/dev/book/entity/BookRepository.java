package com.yse.dev.book.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
	//제목 검색 기능 추가
	// findBy{멤버변수명} (멤버변수타입 매개변수)
	//Contains 붙으면 포함처리 예: Like '%스프링%' 
    Page<Book> findByTitleContains(String title, Pageable pageable);
}
