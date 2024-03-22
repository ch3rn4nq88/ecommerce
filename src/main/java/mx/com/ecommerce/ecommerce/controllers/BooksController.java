package mx.com.ecommerce.ecommerce.controllers;

import mx.com.ecommerce.ecommerce.dto.ResponseDto;
import mx.com.ecommerce.ecommerce.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BooksService booksService;

    @GetMapping
    public ResponseDto getBooks(){
        return ResponseDto.forSuccess(booksService.getMainBookList());

    }

    @GetMapping("/{id}")
    public ResponseDto getBookById(@PathVariable(name = "id")String id){
        return ResponseDto.forSuccess(booksService.getBookById(id));

    }


}
