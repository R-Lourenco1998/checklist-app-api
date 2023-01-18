package com.learning.springboot.checklistapi.db;

import com.learning.springboot.checklistapi.entity.Category;
import com.learning.springboot.checklistapi.repository.CategoryRepository;
import com.learning.springboot.checklistapi.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Profile("data-load")
@Slf4j
@Component
public class DbLoader implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void run(String... args) throws Exception {
        log.info("Populating database with sample data");
        List<String> categories = Arrays.asList("Personal", "Work", "Shopping", "Travel");

        for(String categoryName : categories) {

            Optional<Category> category =
                    this.categoryRepository.findByName(categoryName);

            if (!category.isPresent()) {
                log.debug("Category {} not found. Creating new category", categoryName);
                this.categoryService.addNewCategory(categoryName);
            }
        }
    }

}
