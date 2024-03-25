package mx.com.ecommerce.ecommerce.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import mx.com.ecommerce.ecommerce.model.Book;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class BookDto implements Serializable {

    @NotNull(message = "title should not be null")
    @NotBlank
    private String title;

    @NotNull(message = "published should not be null")
    @NotBlank
    private String publisher;

    @NotNull(message = "maturityRating should not be null")
    @NotBlank
    private String maturityRating;

    @NotNull(message = "language should not be null")
    @NotBlank
    private String language;

    @NotNull(message = "description should not be null")
    @NotBlank
    private String description;

    @NotNull(message = "Page count should not be null")
    private Integer pageCount;

    @NotNull(message = "Categories should not be null")
    @NotEmpty(message = "Categories should not be EMPTY")
    private List<String> categories;

    @NotNull
    @NotEmpty
    private List<String> authors;

    private Book.ImageLinks imageLinks;

    private List<Book.IndustryIdentifiers> industryIdentifiers;

    @NotNull(message = "Published date should not be null")
    @NotBlank(message = "Published dats should not be blank")
    private String publishedDate;
}
