package mx.com.ecommerce.ecommerce.controllers;

import mx.com.ecommerce.ecommerce.dto.ResponseDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/industryIdentifiers")
public class IndustryIdentifiersController {

    public ResponseDto<List<String>> getIndustryIdentifiers(){

        var identifiers = List.of("ISBN_13","ISBN_10");
        return ResponseDto.forSuccess(identifiers);
    }
}
