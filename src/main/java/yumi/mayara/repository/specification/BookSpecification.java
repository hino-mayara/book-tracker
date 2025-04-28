package yumi.mayara.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import yumi.mayara.entity.Book;

import java.util.Objects;

public interface BookSpecification {

    static Specification<Book> hasTitle(String title) {
        return (root, query, builder) -> {
            if (Objects.isNull(title) || title.isBlank()) {
                return null;
            }
            return builder.like(root.get("title"), "%" + title + "%");
        };
    }

    static Specification<Book> hasISBN(String isbn) {
        return (root, query, builder) -> {
            if (Objects.isNull(isbn) || isbn.isBlank()) {
                return null;
            }
            return builder.like(root.get("ISBN"), "%" + isbn + "%");
        };
    }

    static Specification<Book> hasAuthor(String author) {
        return (root, query, builder) -> {
            if (Objects.isNull(author) || author.isBlank()) {
                return null;
            }
            return builder.like(root.get("author"), "%" + author + "%");
        };
    }
}
