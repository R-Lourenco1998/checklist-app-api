package com.learning.springboot.checklistapi.entity.dto;

import com.learning.springboot.checklistapi.entity.Category;
import com.learning.springboot.checklistapi.entity.ChecklistItem;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Getter
public class ChecklistItemDTO {

    private String guid;

    @NotBlank(message = "Checklist item description cannot be either null or empty")
    private String description;

    @NotNull(message = "Is completed is mandatory")
    private Boolean isCompleted;

    @NotNull(message = "Deadline is mandatory")
    private LocalDate deadline;

    private LocalDate postedDate;

    private CategoryDTO category;
    public static ChecklistItemDTO toDTO(ChecklistItem checklistItem) {

        return ChecklistItemDTO.builder()
                .guid(checklistItem.getGuid())
                .description(checklistItem.getDescription())
                .deadline(checklistItem.getDeadLine())
                .postedDate(checklistItem.getPostDate())
                .isCompleted(checklistItem.getIsCompleted())
                .category(checklistItem.getCategory() != null ?
                        CategoryDTO.builder()
                                .guid(checklistItem.getCategory().getGuid())
                                .name(checklistItem.getCategory().getName())
                                .build() :
                        null)
                .build();
    }
}
