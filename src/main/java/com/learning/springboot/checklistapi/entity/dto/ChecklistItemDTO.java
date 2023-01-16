package com.learning.springboot.checklistapi.entity.dto;

import com.learning.springboot.checklistapi.entity.ChecklistItem;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class ChecklistItemDTO {

    private String guid;

    private String description;

    private Boolean isCompleted;

    private String name;

    private LocalDate deadLine;

    private LocalDate postDate;

    private String categoryGuid;

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
