package mx.com.ecommerce.ecommerce.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import mx.com.ecommerce.ecommerce.dto.BookDto;
import mx.com.ecommerce.ecommerce.integration.GoogleBooksResponse;
import mx.com.ecommerce.ecommerce.model.Book;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService{

    private final RestClient restClient;
    private final Map<String, Book> booksCache = new HashMap<>();

    @Override
    public List<Book> getMainBookList(){
        return new ArrayList<>(booksCache.values());
    }

    @Override
    public List<Book> getBookById(String id) {
        return List.of(booksCache.get(id));
    }

    @Override
    public List<Book> getRecommendedBooks() {
        var response=
                restClient.get()
                        .uri("https://www.googleapis.com/books/v1/volumes?q=potter&maxResults=40")
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .body(GoogleBooksResponse.class);

        return Objects.requireNonNull(response).getItems().stream().limit(10).map(item -> {
            var book= bookMapper(item);
            booksCache.put(book.getId(), book);
            return book;
        }).collect(Collectors.toList());

    }

    @Override
    public String create(BookDto book) {
        var newBook = Book.builder()
                .id(UUID.randomUUID().toString())
                .categories(book.getCategories())
                .authors(book.getAuthors())
                .title(book.getTitle())
                .publishedDate(book.getPublishedDate())
                .industryIdentifiers(book.getIndustryIdentifiers())
                .imageLinks(book.getImageLinks())
                .language(book.getLanguage())
                .description(book.getDescription())
                .pageCount(book.getPageCount())
                .publisher(book.getPublisher())
                .maturityRating(book.getMaturityRating()).build();
        booksCache.put(newBook.getId(), newBook);
        return newBook.getId();
    }


    @PostConstruct
    private void getBookList(){
        var response=
        restClient.get()
                .uri("https://www.googleapis.com/books/v1/volumes?q=psicologia&maxResults=40")
                .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                                .body(GoogleBooksResponse.class);
        Objects.requireNonNull(response).getItems().stream().limit(30).forEach(item -> {
            var book= bookMapper(item);
            booksCache.put(book.getId(), book);
        });
    }

    private Book bookMapper(GoogleBooksResponse.Item item) {
        var volumeInfo= item.getVolumeInfo();
        return Book.builder()
                .id(item.getId())
                .categories(volumeInfo.getCategories())
                .authors(volumeInfo.getAuthors())
                .title(volumeInfo.getTitle())
                .publishedDate(volumeInfo.getPublishedDate())
                .language(volumeInfo.getLanguage())
                .description(volumeInfo.getDescription())
                .pageCount(volumeInfo.getPageCount())
                .publisher(volumeInfo.getPublisher())
                .maturityRating(volumeInfo.getMaturityRating())
               .imageLinks(
                       Optional.ofNullable(volumeInfo.getImageLinks()).map(imageLinks ->
                               Book.ImageLinks.builder()
                               .smallThumbnail(Optional.ofNullable(volumeInfo.getImageLinks())
                                       .map(GoogleBooksResponse.ImageLinks::getSmallThumbnail).orElse(null))
                               .thumbnail(Optional.ofNullable(volumeInfo.getImageLinks())
                                       .map(GoogleBooksResponse.ImageLinks::getThumbnail).orElse(null))
                               .build()).orElseGet(()->Book.ImageLinks.builder().build()))
                .industryIdentifiers(item.getVolumeInfo().getIndustryIdentifiers()
                        .stream()
                        .map(industryIdentifiers ->
                                Book.IndustryIdentifiers.builder()
                                        .identifier(industryIdentifiers.getIdentifier())
                                        .type(industryIdentifiers.getType())
                                        .build()
                        ).toList())
                .build();
    }
}
