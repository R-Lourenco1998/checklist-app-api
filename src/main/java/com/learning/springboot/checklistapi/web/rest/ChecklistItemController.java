package com.learning.springboot.checklistapi.web.rest;

import com.learning.springboot.checklistapi.entity.ChecklistItem;
import com.learning.springboot.checklistapi.entity.dto.ChecklistItemDTO;
import com.learning.springboot.checklistapi.entity.dto.NewResourceDTO;
import com.learning.springboot.checklistapi.service.ChecklistItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/checklist-items")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class ChecklistItemController {

    private ChecklistItemService checklistItemService;

    public ChecklistItemController(ChecklistItemService checklistItemService) {
        this.checklistItemService = checklistItemService;
    }

    @Operation(description = "Retrieves all checklist items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all checklist items")
    })
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ChecklistItemDTO>> getAllChecklistItems() {

        List<ChecklistItemDTO> resp = StreamSupport.stream(this.checklistItemService.findAllChecklistItems().spliterator(), false)
                .map(checklistItemEntity -> ChecklistItemDTO.toDTO(checklistItemEntity))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Operation(description = "Inserts a new checklist Items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewResourceDTO> createChecklistItem(@RequestBody ChecklistItemDTO checklistItemDTO) throws ValidationException {
        if(checklistItemDTO.getCategory() == null) {
            throw new javax.validation.ValidationException("Category cannot be null");
        }
        ChecklistItem newChecklistItem = this.checklistItemService.addNewChecklistItem(
                checklistItemDTO.getDescription(),
                checklistItemDTO.getIsCompleted(),
                checklistItemDTO.getDeadline(),
                checklistItemDTO.getCategory().getGuid());
        return new ResponseEntity<>(new NewResourceDTO(newChecklistItem.getGuid()) , HttpStatus.CREATED);
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
                checklistItemDTO.getDeadline(),
                checklistItemDTO.getCategory() != null ? checklistItemDTO.getCategory().getGuid() : null);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteChecklistItem(@PathVariable String guid) {
        this.checklistItemService.deleteChecklistItem(guid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
