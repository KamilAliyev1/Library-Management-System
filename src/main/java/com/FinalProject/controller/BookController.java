package com.FinalProject.controller;

import com.FinalProject.dto.BookDto;
import com.FinalProject.request.BookRequest;
import com.FinalProject.service.AuthorService;
import com.FinalProject.service.BookService;
import com.FinalProject.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.FinalProject.exception.ExceptionHandler.setExceptionMessage;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    @PostMapping("/{isbn}/update")
    public String update(@PathVariable("isbn") String isbn,
                         @ModelAttribute("bookRequest") BookRequest bookRequest) {
        bookService.update(isbn, bookRequest);
        return "redirect:/book";
    }

    @GetMapping("/{isbn}/update")
    public String updatePage(
            @PathVariable("isbn") String isbn,
            @ModelAttribute("bookRequest") BookRequest bookRequest,
            Model model, HttpServletRequest request) {
        setExceptionMessage(model, request);
        BookDto book = bookService.findByIsbn(isbn);
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("authors", authorService.getAuthors());
        return "books/book-update";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") BookRequest bookRequest, Model model) {

        if (bookRequest == null) {
            return "books/book-create";
        }
        bookService.create(bookRequest);
        model.addAttribute("book", bookService.findAll());
        return "redirect:/book";

    }

    @GetMapping("/add")
    public String bookForm(Model model, HttpServletRequest request) {
        setExceptionMessage(model, request);
        model.addAttribute("bookRequest", new BookRequest());
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("authors", authorService.getAuthors());
        return "books/book-create";
    }

    @GetMapping
    public String findAll(Model model, HttpServletRequest request) {

        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("authors", authorService.getAuthors());
        model.addAttribute("categories", categoryService.findAllCategories());
        setExceptionMessage(model, request);


        return "books/book-list";
    }

    @GetMapping("/{isbn}/remove")
    public String delete(@PathVariable String isbn, Model model, HttpServletRequest request) {
        setExceptionMessage(model, request);
        bookService.delete(isbn);
        model.addAttribute("category", bookService.findAll());
        return "redirect:/book";
    }

    @GetMapping("/search")
    public String searchBooks(
            @RequestParam(name = "isbn", required = false) String isbn,
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "authorId", required = false) Long authorId,
            Model model) {
        List<BookDto> books = bookService.searchBooks(isbn, categoryId, authorId);
        model.addAttribute("books", books);
        model.addAttribute("authors", authorService.getAuthors());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "books/book-list";
    }


}
