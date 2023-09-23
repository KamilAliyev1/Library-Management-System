package com.FinalProject.exception;

import com.FinalProject.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    private final StudentService studentService;
    private final OrderService orderService;

    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;


//    public GlobalExceptionHandler(AuthorService authorService, CategoryService categoryService) {
//        this.authorService = authorService;
//        this.categoryService = categoryService;
//    }

    @ExceptionHandler(AuthorsNotFoundException.class)
    public ResponseEntity<?> authorNotFound(AuthorsNotFoundException authorsNotFoundException) {
        List<String> detail = new ArrayList<>();
        detail.add(authorsNotFoundException.getMessage());

        ErrorResponse errorResponse = new ErrorResponse("Author not Found !", detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> categoryNotFound(CategoryNotFoundException categoryNotFoundException) {
        List<String> detail = new ArrayList<>();
        detail.add(categoryNotFoundException.getMessage());

        ErrorResponse errorResponse = new ErrorResponse("Category not Found !", detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<?> categoryAlreadyExists(CategoryAlreadyExistsException categoryAlreadyExistsException) {
        List<String> detail = new ArrayList<>();
        detail.add(categoryAlreadyExistsException.getMessage());

        ErrorResponse errorResponse = new ErrorResponse("Category Already Exists !", detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            OrderNotFoundException.class
    })
    public ResponseEntity<?> userExceptions(Exception userException) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userException.getMessage());
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> validationException(BindException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getFieldError().getDefaultMessage());
    }

//    @ExceptionHandler(BookAlreadyFoundException.class)
//    public String bookAlreadyFoundException(BookAlreadyFoundException bookAlreadyFoundException, Model model) {
//        model.addAttribute("exception", bookAlreadyFoundException.getMessage());
//        model.addAttribute("bookRequest", new BookRequest());
//        model.addAttribute("categories", categoryService.findAllCategories());
//        model.addAttribute("authors", authorService.getAuthors());
//        return "books/book-create";
//    }

    @ExceptionHandler({
            NotChangeableException.class,
            HaveAlreadyBookException.class,
            OrderMustUpdateException.class,
            StockNotEnoughException.class,
            NotDeletableException.class
    }
    )
    public String orderUpdateExceptions(Exception userException, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        request.getSession().setAttribute("exception",userException.getMessage());
        return "redirect:"+ referer;
    }







}