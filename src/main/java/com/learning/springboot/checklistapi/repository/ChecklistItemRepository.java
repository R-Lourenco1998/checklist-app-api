package com.learning.springboot.checklistapi.repository;
import com.learning.springboot.checklistapi.entity.ChecklistItem;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistItemRepository extends PagingAndSortingRepository<ChecklistItem, Long> {

//    Optional<ChecklistItem> findByName(String name);

    Optional<ChecklistItem> findByGuid(String guid);

//    List<ChecklistItem> findByDescriptionAndIsCompleted(String description, Boolean isCompleted);

    List<ChecklistItem>findByCategoryGuid(String guid);
}
