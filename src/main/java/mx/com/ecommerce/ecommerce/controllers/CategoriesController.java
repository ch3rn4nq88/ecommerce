package mx.com.ecommerce.ecommerce.controllers;

import mx.com.ecommerce.ecommerce.dto.ResponseDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController {

    public ResponseDto<List<String>> getCategories(){

        var categories = List.of("Literary Criticism","Fiction", "Art", "Language Arts & Disciplines", "Juvenile Fiction");

        return ResponseDto.forSuccess(categories);
    }

}
