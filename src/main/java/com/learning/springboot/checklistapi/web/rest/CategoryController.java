package com.learning.springboot.checklistapi.web.rest;

import com.learning.springboot.checklistapi.entity.Category;
import com.learning.springboot.checklistapi.entity.dto.CategoryDTO;
import com.learning.springboot.checklistapi.entity.dto.NewResourceDTO;
import com.learning.springboot.checklistapi.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController()
@RequestMapping("/api/v1/categories")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){

        List<CategoryDTO> response = StreamSupport.stream(this.categoryService.findAllCategories().spliterator(), false)
                .map(CategoryDTO::toDTO) // .map(checklistItem -> ChecklistItemDTO.toDTO(checklistItem))
                .collect((Collectors.toList()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewResourceDTO> addNewCategory(@RequestBody CategoryDTO categoryDTO){

        Category newCategory = this.categoryService.addNewCategory(categoryDTO.getName());
        return new ResponseEntity<>(new NewResourceDTO(newCategory.getGuid()), HttpStatus.CREATED);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCategory(@RequestBody CategoryDTO categoryDTO) {

        if(!StringUtils.hasText(categoryDTO.getGuid())){
            throw new javax.validation.ValidationException("Category cannot be null or empty");
        }

        this.categoryService.updateCategory(categoryDTO.getGuid(), categoryDTO.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping(value = "{guid}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String guid){
        this.categoryService.deleteCategory(guid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}