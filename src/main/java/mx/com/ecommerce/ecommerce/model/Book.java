package mx.com.ecommerce.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
public class Book {
    private String title;
    private String publisher;
    private String maturityRating;
    private String language;
    private String description;
    private String id;
    private Integer pageCount;
    private List<String> categories;
    private List<String> authors;
    private ImageLinks imageLinks;
    private List<IndustryIdentifiers> industryIdentifiers;
    private String publishedDate;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageLinks{
        private String smallThumbnail;
        private String thumbnail;
    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IndustryIdentifiers{
        private String type;
        private String identifier;
    }
}
