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

    @NotBlank(message = "Checklist item name cannot be empty")
    private String description;

    @NotNull(message = "isCompleted cannot be null")
    private Boolean isCompleted;

    @NotBlank(message = "Checklist name cannot be empty")
    private String name;

    private LocalDate deadLine;

    private LocalDate postDate;

    private String categoryGuid;

    private Category category;
    public static ChecklistItemDTO toDTO(ChecklistItem checklistItem) {

        return ChecklistItemDTO.builder()
                .guid(checklistItem.getGuid())
                .description(checklistItem.getDescription())
                .deadLine(checklistItem.getDeadLine())
                .postDate(checklistItem.getPostDate())
                .isCompleted(checklistItem.getIsCompleted())
                .categoryGuid(checklistItem.getCategory().getGuid())
                .build();
    }
}
