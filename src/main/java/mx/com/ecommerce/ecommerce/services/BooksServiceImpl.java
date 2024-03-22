package mx.com.ecommerce.ecommerce.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import mx.com.ecommerce.ecommerce.integration.GoogleBooksResponse;
import mx.com.ecommerce.ecommerce.model.Book;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService{

    private final RestClient restClient;
    private Map<String, Book> booksCache = new HashMap<>();
    @Override
    public List<Book> getMainBookList(){
        return getBookList();
    }

    @Override
    public List<Book> getBookById(String id) {
        return List.of(booksCache.get(id));
    }

    @PostConstruct
    private List<Book> getBookList(){

        var response=
        restClient.get()
                .uri("https://www.googleapis.com/books/v1/volumes?q=psicologia&maxResults=40")
                .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                                .body(GoogleBooksResponse.class);

        return Objects.requireNonNull(response).getItems().stream().limit(30).map(item -> {
            var volumeInfo= item.getVolumeInfo();
            var book= Book.builder()
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
                    .imageLinks(Book.ImageLinks.builder()
                            .smallThumbnail(volumeInfo.getImageLinks()!=null?
                                    volumeInfo.getImageLinks().getSmallThumbnail():null)
                            .thumbnail(volumeInfo.getImageLinks()!=null?
                                    volumeInfo.getImageLinks().getThumbnail():null)
                            .build())
                    .industryIdentifiers(item.getVolumeInfo().getIndustryIdentifiers()
                            .stream()
                            .map(industryIdentifiers ->
                                    Book.IndustryIdentifiers.builder()
                                            .identifier(industryIdentifiers.getIdentifier())
                                            .type(industryIdentifiers.getType())
                                            .build()
                            ).toList())
                    .build();
            booksCache.put(book.getId(), book);
            return book;
        }).collect(Collectors.toList());

    }

}
