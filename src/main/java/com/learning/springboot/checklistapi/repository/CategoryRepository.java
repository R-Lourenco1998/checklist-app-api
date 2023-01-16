package com.learning.springboot.checklistapi.repository;

import com.learning.springboot.checklistapi.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

//    Optional<Category> findByName(String name);

    Optional<Category> findByGuid(String guid);
}
