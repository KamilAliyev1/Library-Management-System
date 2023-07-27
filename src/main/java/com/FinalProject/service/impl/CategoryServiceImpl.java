package com.FinalProject.service.impl;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.CategoryDto;
import com.FinalProject.exception.BookNotFoundException;
import com.FinalProject.exception.CategoryAlreadyExistsException;
import com.FinalProject.exception.CategoryNotFoundException;
import com.FinalProject.mapper.CategoryMapper;
import com.FinalProject.model.Book;
import com.FinalProject.model.Category;
import com.FinalProject.repository.BookRepository;
import com.FinalProject.repository.CategoryRepository;
import com.FinalProject.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.FinalProject.service.impl.BookServiceImpl.entityListToResponseList;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final BookServiceImpl bookService;
    private final BookRepository bookRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, BookServiceImpl bookService, BookRepository bookRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<CategoryDto> findAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findCategoryById(Long id) {
        Category optionalCategory = categoryRepository.findById(id).get();
        CategoryDto categoryDto = categoryMapper.categoryToCategoryDto(optionalCategory);
//        List<BookDto> bookRequest = bookService.findByCategory(categoryDto.getName());
        if (optionalCategory == null) {
            throw new CategoryNotFoundException("No  category present with id=" + id);
        } else {
            return categoryDto;
        }
    }


    @Override
    public void createCategory(CategoryDto category) {
        Category category1 = categoryMapper.categoryDtoToCategory(category);
        if (category1 != null) {
            categoryRepository.save(new Category(category1.getId(), category1.getName(), category1.getBook()));
        } else {
            throw new CategoryAlreadyExistsException("Category already exists!");

        }
    }


    @Override
    public void updateCategory(Long id, CategoryDto categoryDto) {
        if (categoryRepository.findById(id).isPresent()) {
            Category existingCategory = categoryRepository.findById(id).get();
            existingCategory.setName(categoryDto.getName());
            Category updatedCategory = categoryRepository.save(existingCategory);
        } else {
            System.out.println("Category is not present");
        }


    }


    @Override
    public void deleteCategory(Long id) {
        final Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Not found Category such id= " + id));
        categoryRepository.deleteById(category.getId());
    }

//    @Override
//    public List<BookDto> showBooksByCategoryName(String name) {
//        List<BookDto> bookDtoList=bookService.findAll();
//        List<BookDto> categoryBook=new ArrayList<>();
//        for(BookDto bookDto : bookDtoList){
//            if(bookDto.getCategory()==name)
//                categoryBook.add(bookDto);
//        }
//        return categoryBook;
//    }

    @Override
    public List<BookDto> showBooksByCategoryName(String category) {
        if (bookRepository.findByCategory(category).isEmpty())
            throw new BookNotFoundException("Book not found with category :  " + category);
       List<Book>  book=bookRepository.findByCategory(category);
        return entityListToResponseList(bookRepository.findByCategory(category));
    }
}

