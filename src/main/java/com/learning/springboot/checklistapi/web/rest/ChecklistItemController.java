package com.learning.springboot.checklistapi.web.rest;

import com.learning.springboot.checklistapi.entity.dto.ChecklistItemDTO;
import com.learning.springboot.checklistapi.service.ChecklistItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController("/api/v1/checklist-items")
public class ChecklistItemController {

    private ChecklistItemService checklistItemService;

    public ChecklistItemController(ChecklistItemService checklistItemService) {
        this.checklistItemService = checklistItemService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ChecklistItemDTO>> getAllChecklistItems() {

        List<ChecklistItemDTO> response = StreamSupport.stream(this.checklistItemService.findAllChecklistItems().spliterator(), false)
                .map(checklistItem -> ChecklistItemDTO.toDTO(checklistItem))
                .collect((Collectors.toList()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
