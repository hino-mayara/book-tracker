package yumi.mayara.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yumi.mayara.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {


}
