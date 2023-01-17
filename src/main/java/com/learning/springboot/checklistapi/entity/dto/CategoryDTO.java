package com.learning.springboot.checklistapi.entity.dto;
import com.learning.springboot.checklistapi.entity.Category;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class CategoryDTO {

    private String guid;

    @NotBlank(message = "Category name cannot be empty")
    private String name;
    public static CategoryDTO toDTO(Category category) {

        return CategoryDTO.builder()
                .guid(category.getGuid())
                .name(category.getName())
                .build();
    }
}
