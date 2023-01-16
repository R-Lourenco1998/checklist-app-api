package com.learning.springboot.checklistapi.service;

import com.learning.springboot.checklistapi.entity.Category;
import com.learning.springboot.checklistapi.entity.ChecklistItem;
import com.learning.springboot.checklistapi.exception.ResourceNotFoundException;
import com.learning.springboot.checklistapi.repository.CategoryRepository;
import com.learning.springboot.checklistapi.repository.ChecklistItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
public class ChecklistItemService {

    private ChecklistItemRepository checklistItemRepository;

    private CategoryRepository categoryRepository;

    private void validateChecklistItemData(String description, Boolean isCompleted, LocalDate deadLine, String categoryGuid) {
        if (!StringUtils.hasText(description)) {
            throw new IllegalArgumentException("Checklist item must have a description");
        }
        if (!StringUtils.hasText(categoryGuid)) {
            throw new IllegalArgumentException("Checklist item category guid must be provided");
        }
        if (isCompleted == null) {
            throw new IllegalArgumentException("Checklist item must have a flag indicating if it is completed or not");
        }
        if (deadLine == null) {
            throw new IllegalArgumentException("Checklist item must have a deadline");
        }
    }

    public ChecklistItem updateCheckListItem(String guid, String description, Boolean isCompleted, LocalDate deadline, String categoryGuid) {
        if (!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Guid cannot be null or empty");
        }

        ChecklistItem retriviedItem = this.checklistItemRepository.findByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Checklist item not found"));

        if (!StringUtils.hasText(description)) {
            retriviedItem.setDescription(description);
        }

        if (isCompleted != null) {
            retriviedItem.setIdCompleted(isCompleted);
        }

        if (deadline != null) {
            retriviedItem.setDeadLine(deadline);
        }

        if (!StringUtils.hasText(categoryGuid)) {
            Category retriviedCategory = this.categoryRepository.findByGuid(categoryGuid).orElseThrow(
                    () -> new ResourceNotFoundException("Category not found"));
            retriviedItem.setCategory(retriviedCategory);
        }

        log.debug("Updating checklist item {}", retriviedItem);

        return this.checklistItemRepository.save(retriviedItem);
    }

    public ChecklistItem addNewChecklistItem(String description, Boolean isCompleted, LocalDate deadline, String categoryGuid) {
//        if (!StringUtils.hasText(description) || isCompleted == null || deadLine == null || !StringUtils.hasText(categoryGuid)) {
//            throw new IllegalArgumentException("Invalid checklist item description, isCompleted, deadLine or categoryGuid");
//        }
        this.validateChecklistItemData(description, isCompleted, deadline, categoryGuid);

        Category retriviedCategory = this.categoryRepository.findByGuid(categoryGuid).orElseThrow(
                () -> new ResourceNotFoundException("Category not found"));

        ChecklistItem checklistItem = new ChecklistItem();
        checklistItem.setGuid(UUID.randomUUID().toString());
        checklistItem.setDescription(description);
        checklistItem.setDeadLine(deadline);
        checklistItem.setPostDate(LocalDate.now());
        checklistItem.setCategory(retriviedCategory);

        log.debug("Saving checklist item {}", checklistItem);

        return this.checklistItemRepository.save(checklistItem);
    }

    public Iterable<ChecklistItem> getAllChecklistItems() {
        return this.checklistItemRepository.findAll();
    }

    public ChecklistItem findChecklistitemByGuid(String guid) {

        if (!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Guid cannot be null or empty");
        }
        return this.checklistItemRepository.findByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Checklist item not found"));
    }

    public void deleteChecklistItem(String guid) {
        if (!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Guid cannot be null or empty");
        }

        ChecklistItem retriviedItem = this.checklistItemRepository.findByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Checklist item not found"));


        log.debug("Deleting checklist item {}", retriviedItem);

        this.checklistItemRepository.delete(retriviedItem);
    }
}
