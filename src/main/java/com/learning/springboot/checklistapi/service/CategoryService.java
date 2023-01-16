package com.learning.springboot.checklistapi.service;

import com.learning.springboot.checklistapi.entity.Category;
import com.learning.springboot.checklistapi.entity.ChecklistItem;
import com.learning.springboot.checklistapi.exception.CategoryServiceException;
import com.learning.springboot.checklistapi.exception.ResourceNotFoundException;
import com.learning.springboot.checklistapi.repository.CategoryRepository;
import com.learning.springboot.checklistapi.repository.ChecklistItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class CategoryService {

    @Autowired
    private ChecklistItemRepository checklistItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryService(ChecklistItemRepository checklistItemRepository, CategoryRepository categoryRepository) {
        this.checklistItemRepository = checklistItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public Category addNewCategory(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        Category category = new Category();
        category.setGuid(UUID.randomUUID().toString());
        category.setName(name);

        log.debug("Saving category {}", category);
        return categoryRepository.save(category);
    }

    public Category updateCategory(String guid, String name) {
        if (guid == null || !StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Invalid category guid or name");
        }

        Category category = this.categoryRepository.findByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Category not found"));
        category.setName(name);
        log.debug("Updating category {}", category);
        return this.categoryRepository.save(category);
    };

    public void deleteCategory(String guid) {
        if (!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Invalid category guid");
        }

        List<ChecklistItem> checklistItems = this.checklistItemRepository.findByCategoryGuid(guid);
        if (!CollectionUtils.isEmpty(checklistItems)) {
            throw new CategoryServiceException("Cannot delete category with checklist items");
        }
        Category category = this.categoryRepository.findByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Category not found"));
        log.debug("Deleting category {}", category);
        this.categoryRepository.delete(category);
    }

    public Iterable<Category> findAllCategories() {
        return this.categoryRepository.findAll();
    }

    public Category findCategoriesByGuid(String guid) {
        if (!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Invalid category guid");
        }
        return this.categoryRepository.findByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Category not found"));
    }

}
