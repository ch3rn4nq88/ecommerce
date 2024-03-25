package mx.com.ecommerce.ecommerce.controllers;

import jakarta.validation.Valid;
import mx.com.ecommerce.ecommerce.dto.BookDto;
import mx.com.ecommerce.ecommerce.dto.ResponseDto;
import mx.com.ecommerce.ecommerce.exceptions.ErrorResponse;
import mx.com.ecommerce.ecommerce.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @GetMapping("/recommendations")
    public ResponseDto getRecommendedBooks(){
        return ResponseDto.forSuccess(booksService.getRecommendedBooks());

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto createBook(@Valid @RequestBody BookDto book){
        return ResponseDto.forSuccess(booksService.getBookById(booksService.create(book)).get(0));

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(((DefaultMessageSourceResolvable) Objects.requireNonNull(error.getArguments())[0]).getDefaultMessage()+
                    " " +error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse("Validation Failed", details);
        return ResponseEntity.badRequest().body(error);
    }


}
