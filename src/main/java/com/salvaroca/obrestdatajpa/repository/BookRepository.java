package com.salvaroca.obrestdatajpa.repository;

import com.salvaroca.obrestdatajpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
