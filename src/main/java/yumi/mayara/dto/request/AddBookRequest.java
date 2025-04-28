package yumi.mayara.dto.request;

import lombok.Builder;
import yumi.mayara.entity.Book;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
public record AddBookRequest(
                             @Min(3)
                             @Max(50)
                             @NotBlank
                             String title,
                             String ISBN,

                             @Min(3)
                             @NotBlank
                             String author,

                             @NotBlank
                             @Min(1)
                             Integer totalPages,


                             Integer pagesRead) {

    public Book toBook() {
        return Book.builder()
                .title(title)
                .ISBN(ISBN)
                .author(author)
                .totalPages(totalPages)
                .pagesRead(pagesRead)
                .build();
    }
}
