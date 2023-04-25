package com.FinalProject.controller;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.BookRequest;
import com.FinalProject.service.impl.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookServiceImpl bookService;

    @GetMapping("/add")
    public String bookForm(BookRequest bookRequest) {
        return "book-create";
    }

    @PostMapping
    public String createBook(@ModelAttribute("bookRequest") BookRequest bookRequest, Model model) {
        if (bookRequest == null) {
            return "book-create";
        }
        bookService.create(bookRequest);
        model.addAttribute("bookRequest", bookService.findAll());
        return "redirect:/book";
    }

    @GetMapping
    public String findAll(Model model) {
        final List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "book-list";
    }

    @GetMapping("/remove/{isbn}")
    public String delete(@PathVariable String isbn, Model model) {
        bookService.delete(isbn);
        model.addAttribute("category", bookService.findAll());
        return "redirect:/book";
    }


    @GetMapping("/category")
    public ResponseEntity<List<BookDto>> findByCategory(@RequestParam String category) {
        return ResponseEntity.ok(bookService.findByCategory(category));
    }

    @PostMapping("/{isbn}/update")
    public String update(@PathVariable String isbn, @ModelAttribute("bookRequest") BookRequest bookRequest, Model model) {
        if (bookRequest == null) {
            return "book-update";
        }
        bookService.update(isbn, bookRequest);
        model.addAttribute("bookRequest", bookService.findAll());
        return "redirect:/book";
    }

    @GetMapping("/{isbn}/update")
    public String updatePage(@PathVariable String isbn, Model model) {
        model.addAttribute("bookRequest", bookService.findByIsbn(isbn));
        return "book-update";
    }

    @GetMapping("/search/{isbn}")
    public String findByIsbn(@PathVariable("isbn") String isbn, Model model) {
        final BookDto bookDto = bookService.findByIsbn(isbn);
        model.addAttribute("bookRequest", bookDto);
        return "book-list";
    }
//    @GetMapping("/{isbn}")
//    public ResponseEntity<BookDto> findByIsbn(@PathVariable String isbn) {
//        return new ResponseEntity<>(bookService.findByIsbn(isbn), HttpStatus.OK);
//    }


}
