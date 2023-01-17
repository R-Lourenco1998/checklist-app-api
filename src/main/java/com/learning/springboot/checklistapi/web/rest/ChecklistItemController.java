package com.learning.springboot.checklistapi.web.rest;

import com.learning.springboot.checklistapi.entity.ChecklistItem;
import com.learning.springboot.checklistapi.entity.dto.ChecklistItemDTO;
import com.learning.springboot.checklistapi.service.ChecklistItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;
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
                .map(ChecklistItemDTO::toDTO) // .map(checklistItem -> ChecklistItemDTO.toDTO(checklistItem))
                .collect((Collectors.toList()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createChecklistItem(@RequestBody ChecklistItemDTO checklistItemDTO) throws ValidationException {
        if(checklistItemDTO.getCategoryGuid() == null){
            throw new ValidationException("Category guid cannot be null");
        }
        ChecklistItem newChecklistItem = this.checklistItemService.addNewChecklistItem(
                checklistItemDTO.getDescription(),
                checklistItemDTO.getIsCompleted(),
                checklistItemDTO.getDeadLine(),
                checklistItemDTO.getCategoryGuid()
        );
        return new ResponseEntity<>(newChecklistItem.getGuid(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateChecklistItem(@PathVariable String guid, @RequestBody ChecklistItemDTO checklistItemDTO) throws ValidationException {
        if(!StringUtils.hasLength(checklistItemDTO.getGuid())){
            throw new ValidationException("Category guid cannot be null");
        }
        this.checklistItemService.updateCheckListItem(
                guid,
                checklistItemDTO.getDescription(),
                checklistItemDTO.getIsCompleted(),
                checklistItemDTO.getDeadLine(),
                checklistItemDTO.getCategory() != null ? checklistItemDTO.getCategoryGuid() : null
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteChecklistItem(@PathVariable String guid) {
        this.checklistItemService.deleteChecklistItem(guid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
