package yumi.mayara.dto.response;

import lombok.Builder;

@Builder
public record BookResponse(Long id,
                           String title,
                           String ISBN,
                           String author,
                           Integer totalPages,
                           Integer pagesRead) {
}
