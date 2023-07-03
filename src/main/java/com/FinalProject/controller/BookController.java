package com.FinalProject.controller;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.BookRequest;
import com.FinalProject.service.impl.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookServiceImpl bookService;

    @PostMapping("/{isbn}/update")
    public String update(@PathVariable("isbn") String isbn,
                         @ModelAttribute("bookRequest") BookRequest bookRequest,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "book-update";
        }
        bookService.update(isbn, bookRequest);
        return "book-list";
    }

    @GetMapping("/{isbn}/update")
    public String updatePage(@PathVariable("isbn") String isbn, Model model) {
        model.addAttribute("bookRequest", new BookRequest());
        return "book-update";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") BookRequest bookRequest, Model model) {
        if (bookRequest == null) {
            return "book-create";
        }
        bookService.create(bookRequest);
        model.addAttribute("book", bookService.findAll());
        return "redirect:/book";
    }


    @GetMapping("/add")
    public String bookForm(Model model) {
        model.addAttribute("bookRequest", new BookRequest());
        return "book-create";
    }


    @GetMapping
    public String findAll(Model model) {
        List<BookDto> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);
        return "book-list";
    }

    @GetMapping("/{isbn}/remove")
    public String delete(@PathVariable String isbn, Model model) {
        bookService.delete(isbn);
        model.addAttribute("category", bookService.findAll());
        return "redirect:/book";
    }


    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> getImage(Model model, @PathVariable String imageName) {
        var resource = bookService.load(imageName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + imageName + "\"")
                .contentType(MediaType.IMAGE_PNG).body(resource);

    }

    @GetMapping("/search/")
    public String findByIsbn(@RequestParam String isbn, Model model) {
        model.addAttribute("books", bookService.findByIsbn(isbn));
        return "book-list";
    }

    @GetMapping("/search/c")
    public String findByCategory(@RequestParam String category, Model model) {
        model.addAttribute("books", bookService.findByCategory(category));
        return "book-list";
    }

    @GetMapping("/search/a")
    public String findByAuthor(@RequestParam String author, Model model) {
        model.addAttribute("books", bookService.findByAuthor(author));
        return "book-list";
    }


}
