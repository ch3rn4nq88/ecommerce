package mx.com.ecommerce.ecommerce.services;

import mx.com.ecommerce.ecommerce.model.Book;

import java.util.List;

public interface BooksService {
    List<Book> getMainBookList();
    List<Book> getBookById(String id);
}
