package com.FinalProject.controller;

import com.FinalProject.dto.AuthorsDto;
import com.FinalProject.dto.BookDto;
import com.FinalProject.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/author/new")
    public String createAuthorForm(Model model) {
        AuthorsDto author = new AuthorsDto();
        model.addAttribute("author", author);
        return "author-createStudent";
    }

    @PostMapping("/author/new")
    public String saveAuthor(@ModelAttribute("author") AuthorsDto author,
                             BindingResult result,
                             @Valid Model model) {
        if (result.hasErrors()) {
            model.addAttribute("author", author);
            return "author-createStudent";
        }
        authorService.createAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/authors")
    public String listAuthor(Model model) {
        List<AuthorsDto> author = authorService.getAuthors();
        model.addAttribute("author", author);
        return "author-list";
    }

    @GetMapping("/{authorId}/delete")
    public String deleteAuthor(@PathVariable("authorId") Long authorId) {
        authorService.deleteAuthor(authorId);
        return "redirect:/authors";
    }

    @GetMapping("/author/search")
    public String searchAuthor(@RequestParam(value = "query") String query,
                               Model model) {
        List<AuthorsDto> author = authorService.searchBook(query);
        model.addAttribute("author", author);
        return "author-list";
    }

    @GetMapping("/author/{authorId}/edit")
    public String editAuthorForm(@PathVariable("authorId") Long authorId, Model model) {
        AuthorsDto author = authorService.viewAuthor(authorId);
        model.addAttribute("author", author);
        return "author-updateStudent";
    }

    @PostMapping("/author/{authorId}/edit")
    public String updateAuthor(@PathVariable("authorId") Long authorId,
                               @Valid @ModelAttribute("author") AuthorsDto author,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("author", author);
            return "author-updateStudent";
        }
        authorService.updateAuthor(authorId, author);
        return "redirect:/authors";
    }

    @GetMapping("/authorBooks/{authorId}")
    public ResponseEntity<List<BookDto>> authorBooks(@PathVariable("authorId") Long authorId) {
        return ResponseEntity.ok(authorService.showAuthorBooks(authorId));
    }


}
