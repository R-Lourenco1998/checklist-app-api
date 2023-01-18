package com.learning.springboot.checklistapi.web.rest;

import com.learning.springboot.checklistapi.entity.Category;
import com.learning.springboot.checklistapi.entity.dto.CategoryDTO;
import com.learning.springboot.checklistapi.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController()
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryDTO>> getAllCategories() throws ValidationException {

        List<CategoryDTO> response = StreamSupport.stream(this.categoryService.findAllCategories().spliterator(), false)
                .map(CategoryDTO::toDTO) // .map(checklistItem -> ChecklistItemDTO.toDTO(checklistItem))
                .collect((Collectors.toList()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addNewCategory(@RequestBody CategoryDTO categoryDTO) throws ValidationException {

        Category newCategory = this.categoryService.addNewCategory(categoryDTO.getName());
        return new ResponseEntity<>(newCategory.getGuid(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{guid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCategory(@RequestBody CategoryDTO categoryDTO) throws ValidationException {

        if(!StringUtils.hasText(categoryDTO.getGuid())) {
            throw new ValidationException("Category guid cannot be null");
        }

        Category updatedCategory = this.categoryService.updateCategory(categoryDTO.getGuid(), categoryDTO.getName());
        return new ResponseEntity<>(updatedCategory.getGuid(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCategory(@RequestBody CategoryDTO categoryDTO) throws ValidationException {

        this.categoryService.deleteCategory(categoryDTO.getGuid());
        return new ResponseEntity<>(categoryDTO.getGuid(), HttpStatus.OK);
    }

}