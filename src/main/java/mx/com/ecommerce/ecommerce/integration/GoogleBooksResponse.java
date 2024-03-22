package mx.com.ecommerce.ecommerce.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoogleBooksResponse {

    private List<Item> items;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item{
        private String id;
        private VolumeInfo volumeInfo;
    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VolumeInfo{
        private String title;
        private List<String> authors;
        private Integer pageCount;
        private String publisher;
        private String maturityRating;
        private List<String> categories;
        private String language;
        private String description;
        private String publishedDate;
        private ImageLinks imageLinks;
        private List<IndustryIdentifiers> industryIdentifiers;
    }

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
